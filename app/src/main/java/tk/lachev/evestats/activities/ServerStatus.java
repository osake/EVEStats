package tk.lachev.evestats.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tlabs.eve.EveNetwork;
import com.tlabs.eve.api.ServerStatusRequest;
import com.tlabs.eve.api.ServerStatusResponse;
import com.tlabs.eve.net.DefaultEveNetwork;

import org.w3c.dom.Text;

import java.net.URL;

import tk.lachev.evestats.R;

public class ServerStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ServerStatusGet serverStatusGet = new ServerStatusGet();
        Log.d(ServerStatus.class.toString(), "Executing");
        serverStatusGet.execute();
    }

}
class ServerStatusGet extends AsyncTask<EveNetwork, ServerStatusRequest, ServerStatusResponse> {

    @Override
    protected ServerStatusResponse doInBackground(EveNetwork... params) {
        final EveNetwork eve = new DefaultEveNetwork();
        final ServerStatusRequest request = new ServerStatusRequest();
        final ServerStatusResponse status = eve.execute(request);
        Log.d(ServerStatus.class.toString(), "Returninig status");
        return status;
    }

    @Override
    protected void onPostExecute(ServerStatusResponse status) {
        Log.d("App", "onPostExecute");
        TextView tv = null;
        tv = (TextView) tv.findViewById(R.id.server_status_textview);
        if(status.getServerOpen()) {
            tv.setText("Online");
        }
        else {
            tv.setText("Offline");
        }
    }
}