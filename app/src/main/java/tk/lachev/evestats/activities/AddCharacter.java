package tk.lachev.evestats.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tlabs.eve.EveNetwork;
import com.tlabs.eve.api.AccessInfo;
import com.tlabs.eve.api.AccessInfoRequest;
import com.tlabs.eve.api.AccessInfoResponse;
import com.tlabs.eve.api.character.CharacterSheet;
import com.tlabs.eve.net.DefaultEveNetwork;

import java.util.List;

import tk.lachev.evestats.R;
import utils.Character;
import utils.DatabaseHandler;
import utils.PortraitDownload;

public class AddCharacter extends AppCompatActivity {

    Boolean veriCodeFull = false;
    Boolean keyIdFull = false;

    public EditText fieldApiCode;
    public EditText fieldKeyId;
    Button submitButton;
    public SharedPreferences characters;
    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        fieldApiCode = (EditText) findViewById(R.id.verification_code);
        fieldKeyId = (EditText) findViewById(R.id.key_id);
        submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setEnabled(false);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestApiKey().execute();
            }
        });
        fieldApiCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 64) {
                    veriCodeFull = true;
                    Log.d("Add character", "Text lenght is 64!");
                }
                if (s.length() < 64) {
                    veriCodeFull = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (veriCodeFull && keyIdFull) {
                    submitButton.setEnabled(true);
                }
            }
        });

        fieldKeyId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 7) {
                    keyIdFull = true;
                }
                if (s.length() < 7) {
                    keyIdFull = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (veriCodeFull && keyIdFull) {
                    submitButton.setEnabled(true);
                }
            }
        });
    }
    public class TestApiKey extends AsyncTask<EveNetwork, AccessInfoRequest, AccessInfoResponse> {


        String apiKey;
        String keyId;
        AccessInfoResponse response;
        String name = null;
        AccessInfo accessInfo;
        List<CharacterSheet> characterSheets;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            apiKey = fieldApiCode.getText().toString();
            keyId = fieldKeyId.getText().toString();
        }

        @Override
        protected AccessInfoResponse doInBackground(EveNetwork... params) {
            final EveNetwork eve = new DefaultEveNetwork();

            final AccessInfoRequest request = new AccessInfoRequest(keyId, apiKey);

            response = eve.execute(request);

            accessInfo = response.getAccessInfo();
            characterSheets = accessInfo.getCharacters();

            if (!characterSheets.isEmpty() && characterSheets.size() == 1) {
                name = characterSheets.get(0).getCharacterName();
            }

            PortraitDownload download = new PortraitDownload();

            download.downloadImage(getApplicationContext(), characterSheets);

            return response;
        }
        @Override
        protected void onPostExecute(AccessInfoResponse response) {

            Log.d("App", apiKey + " " + keyId);
            Log.d("App", response.hasError() + " " + response.getExpires() + " " + response.hasAuthenticationError() + " " + response.getErrorCode());
            progressDialog.hide();

            if (!response.hasError() && accessInfo.getExpires() == 0 && accessInfo.getAccessMask() == 1073741823 && characterSheets.size() == 1) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                Boolean exists = false;
                List<Character> list = db.getAllCharacters();
                Log.d("app", list.toString());
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size() - 1 || !exists; i++) {
                        Character c = list.get(i);
                        if (c.get_apiKey().equals(apiKey)) exists = true;
                    }
                }
                if (!exists || list.isEmpty() && name != null && characterSheets.size() == 1) {
                    for (CharacterSheet c : characterSheets) {
                        db.addCharacter(new Character(keyId, apiKey, c.getCharacterName(), String.valueOf(c.getCharacterID())));
                    }
                    //db.addCharacter(new Character(apiKey, keyId, name));
                    Toast.makeText(getApplicationContext(), "Character added.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Characters.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Character already exists.", Toast.LENGTH_LONG).show();
                    fieldApiCode.setText("");
                    fieldKeyId.setText("");
                }
            } else if (response.hasError()) {
                if (response.getErrorCode() == 400) {
                    Toast.makeText(getApplicationContext(), "Bad Request. Possibly wrong keyId/verification code?", Toast.LENGTH_LONG).show();
                }
                if (response.getErrorCode() == 408) {
                    Toast.makeText(getApplicationContext(), "Request timeout.", Toast.LENGTH_SHORT).show();
                }
            } else if (accessInfo.getExpires() != 0) {
                Toast.makeText(getApplicationContext(), "You must provide a non-expiring API key.", Toast.LENGTH_LONG).show();
            } else if (accessInfo.getAccessMask() != 1073741823) {
                Toast.makeText(getApplicationContext(), "You must provide a full-access API key.", Toast.LENGTH_LONG).show();
            } else if (name == null) {
                Toast.makeText(getApplicationContext(), "You must provide an API key for a single character.", Toast.LENGTH_LONG).show();
            } else if (characterSheets.size() > 1) {
                Toast.makeText(getApplicationContext(), "You must provide an API key for a single character.", Toast.LENGTH_LONG).show();
            }

        }
    }
}
