package tk.lachev.evestats.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tlabs.eve.EveNetwork;
import com.tlabs.eve.api.ServerStatusRequest;
import com.tlabs.eve.api.ServerStatusResponse;
import com.tlabs.eve.net.DefaultEveNetwork;

import org.w3c.dom.Text;

import java.net.URL;

import tk.lachev.evestats.R;

import static tk.lachev.evestats.R.*;

public class ServerStatus extends AppCompatActivity {


    public TextView text1;
    public TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_server_status);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        text1 = (TextView) findViewById(R.id.server_status_textview);
        text2 = (TextView) findViewById(id.players_online_textview);

        final ServerStatusGet serverStatusGet = new ServerStatusGet(text1, text2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                text1.setText("Refreshing...");
                text2.setText("Refreshing...");
                new ServerStatusGet(text1, text2).execute();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.d(ServerStatus.class.toString(), "Executing");
        serverStatusGet.execute();
    }

}
class ServerStatusGet extends AsyncTask<EveNetwork, ServerStatusRequest, ServerStatusResponse> {

    private final TextView _playersOnline;
    private final TextView _statusText;


    public ServerStatusGet(TextView statusText, TextView playersOnline) {
        this._statusText = statusText;
        this._playersOnline = playersOnline;
    }

    @Override
    protected ServerStatusResponse doInBackground(EveNetwork... params) {
        final EveNetwork eve = new DefaultEveNetwork();
        final ServerStatusRequest request = new ServerStatusRequest();
        final ServerStatusResponse status = eve.execute(request);
        return status;
    }

    @Override
    protected void onPostExecute(ServerStatusResponse status) {
        Log.d("App", "onPostExecute");
        if(status.getServerOpen()) {
            Log.d("APP", "SERVER IS OPEN, CHANGING TEXT");
            _statusText.setText("Online!");
            _statusText.setTextColor(ColorStateList.valueOf(Color.GREEN));
            _playersOnline.setText(status.getOnlinePlayers() + " players online currently on Tranquility!");
        }
        else {
            _statusText.setText("Offline!");
            _statusText.setTextColor(ColorStateList.valueOf(Color.RED));
        }

    }
}