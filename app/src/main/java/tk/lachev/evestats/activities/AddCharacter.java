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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlabs.eve.EveNetwork;
import com.tlabs.eve.api.AccessInfo;
import com.tlabs.eve.api.AccessInfoRequest;
import com.tlabs.eve.api.AccessInfoResponse;
import com.tlabs.eve.api.EveAPI;
import com.tlabs.eve.api.NamesRequest;
import com.tlabs.eve.api.NamesResponse;
import com.tlabs.eve.api.character.CharacterSheet;
import com.tlabs.eve.net.DefaultEveNetwork;
import com.tlabs.eve.parser.BooleanDeserializer;

import java.util.List;

import tk.lachev.evestats.R;
import utils.Character;
import utils.DatabaseHandler;

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

            AccessInfo accessInfo = response.getAccessInfo();

            List<CharacterSheet> list = response.getCharacters();

            if (list.size() == 1) {
                name = list.get(0).getCharacterName();
            }

            return response;
        }
        @Override
        protected void onPostExecute(AccessInfoResponse response) {
            Log.d("App", apiKey + " " + keyId);
            Log.d("App", response.hasError() + " " + response.getExpires() + " " + response.hasAuthenticationError() + " " + response.getErrorCode());
            progressDialog.hide();
            if (!response.hasError() && response.getExpires() == 0 && response.getAccessMask() == 1073741823 && name != null) {


                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                Boolean exists = false;

                List<Character> list = db.getAllCharacters();

                Log.d("app", list.toString());
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size() || !exists; i++) {
                        Character c = list.get(i);
                        if (c.get_apiKey().equals(apiKey)) exists = true;
                    }
                }
                if (!exists || list.isEmpty()) {
                    db.addCharacter(new Character(apiKey, keyId, name));
                    Toast.makeText(getApplicationContext(), "Character added.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Characters.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Character already exists.", Toast.LENGTH_LONG).show();
                    fieldApiCode.setText("");
                    fieldKeyId.setText("");
                }

            } else if (response.getExpires() != 0) {
                Toast.makeText(getApplicationContext(), "You must provide a non-expiring API key.", Toast.LENGTH_LONG).show();
            } else if (response.getAccessMask() != 1073741823) {
                Toast.makeText(getApplicationContext(), "You must provide a full-access API key.", Toast.LENGTH_LONG).show();
            } else if (response.hasError()) {
                Toast.makeText(getApplicationContext(), "Invalid verification code/key ID.", Toast.LENGTH_LONG).show();
            } else if (name == null) {
                Toast.makeText(getApplicationContext(), "You must provide an API key for a single character", Toast.LENGTH_LONG).show();
            }

        }
    }
}
