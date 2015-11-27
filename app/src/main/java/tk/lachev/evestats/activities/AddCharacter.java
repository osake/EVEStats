package tk.lachev.evestats.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.tlabs.eve.api.ServerStatusRequest;
import com.tlabs.eve.api.ServerStatusResponse;
import com.tlabs.eve.net.DefaultEveNetwork;
import com.tlabs.eve.parser.BooleanDeserializer;

import tk.lachev.evestats.R;

public class AddCharacter extends AppCompatActivity {

    Boolean veriCodeFull = false;
    Boolean keyIdFull = false;

    public EditText fieldApiCode;
    public EditText fieldKeyId;
    Button submitButton;

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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            apiKey = fieldApiCode.toString();
            keyId = fieldKeyId.toString();
        }

        @Override
        protected AccessInfoResponse doInBackground(EveNetwork... params) {

            final EveNetwork eve = new DefaultEveNetwork();

            final AccessInfoRequest request = new AccessInfoRequest(keyId, apiKey);

            return eve.execute(request);
        }

        @Override
        protected void onPostExecute(AccessInfoResponse response) {
            AccessInfo accessInfo = response.getAccessInfo();
            if (accessInfo.getType() == AccessInfo.CHARACTER) {
                if (accessInfo.getExpires() == 0) {

                } else {
                    Toast.makeText(getApplicationContext(), "You must provide a non-expiring API key.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "You must provide a full-access API key", Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }
}
