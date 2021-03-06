package tk.lachev.evestats.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import tk.lachev.evestats.R;
import utils.ServiceHandler;

import static android.graphics.Color.rgb;
import static tk.lachev.evestats.R.id;
import static tk.lachev.evestats.R.layout;

public class ServerStatus extends AppCompatActivity {


    public TextView serverStatus;
    public TextView playersOnline;
    public TextView serverTime;

    public TextView serverStatusSing;
    public TextView playersOnlineSing;
    public TextView serverTimeSing;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_server_status);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);


        serverStatus = (TextView) findViewById(R.id.server_status_textview);
        playersOnline = (TextView) findViewById(id.players_online_textview);
        serverTime = (TextView) findViewById(id.server_time);

        serverStatusSing = (TextView) findViewById(id.server_status_textview_sing);
        playersOnlineSing = (TextView) findViewById(id.players_online_textview_sing);
        serverTimeSing = (TextView) findViewById(id.server_time_sing);

        final ServerStatusGet serverStatusGet = new ServerStatusGet(serverStatus, playersOnline, serverTime, serverStatusSing, playersOnlineSing, serverTimeSing);

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                refreshTime();
                new ServerStatusGet(serverStatus, playersOnline, serverTime, serverStatusSing, playersOnlineSing, serverTimeSing).execute();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Log.d(ServerStatus.class.toString(), "Executing");
        serverStatusGet.execute();
    }
    void refreshTime() {
        serverStatus.setText("Refreshing...");
        playersOnline.setText("Refreshing...");
        serverStatusSing.setText("Refreshing...");
        playersOnlineSing.setText("Refreshing...");
        serverStatus.setTextColor(rgb(234, 234, 234));
        playersOnline.setTextColor(rgb(234, 234, 234));
        serverStatusSing.setTextColor(rgb(234, 234, 234));
        playersOnlineSing.setTextColor(rgb(234, 234, 234));
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        f.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        serverTime.setText("Current server time: " + f.format(GregorianCalendar.getInstance().getTime()));
        serverTimeSing.setText("Current server time: " + f.format(GregorianCalendar.getInstance().getTime()));
    }

}
class ServerStatusGet extends AsyncTask<EveNetwork, ServerStatusRequest, ServerStatusResponse> {

    private final TextView _playersOnline;
    private final TextView _statusText;
    private final TextView _serverTime;
    private final TextView _playersOnlineSing;
    private final TextView _statusTextSing;
    private final TextView _serverTimeSing;
    private String serviceStatusStr = null;
    private String userCountsStr = null;

    public ServerStatusGet(TextView playersOnline, TextView statusText, TextView serverTime, TextView playersOnlineSing, TextView statusTextSing, TextView serverTimeSing) {
        this._playersOnline = playersOnline;
        this._statusText = statusText;
        this._serverTime = serverTime;
        this._playersOnlineSing = playersOnlineSing;
        this._statusTextSing = statusTextSing;
        this._serverTimeSing = serverTimeSing;
    }

    @Override
    protected ServerStatusResponse doInBackground(EveNetwork... params) {

        String url = "http://public-crest-sisi.testeveonline.com/";
        ServiceHandler sh = new ServiceHandler();
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        if (jsonStr != null) {
            final String TAG_SERVICESTATUS = "serviceStatus";
            final String TAG_SERVICESTATUS_EVE = "eve";
            final String TAG_USERCOUNTS = "userCounts";
            final String TAG_USERCOUNTS_EVE = "eve_str";

                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    JSONObject serviceStatus = new JSONObject(jsonObject.getString(TAG_SERVICESTATUS));
                    serviceStatusStr = serviceStatus.getString(TAG_SERVICESTATUS_EVE);

                    JSONObject usercounts = new JSONObject(jsonObject.getString(TAG_USERCOUNTS));
                    userCountsStr = usercounts.getString(TAG_USERCOUNTS_EVE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        final EveNetwork eve = new DefaultEveNetwork();
        final ServerStatusRequest request = new ServerStatusRequest();

        return eve.execute(request);
    }

    @Override
    protected void onPostExecute(ServerStatusResponse status) {

        if(status.getServerOpen()) {
            _statusText.setText("Online!");
            _statusText.setTextColor(ColorStateList.valueOf(Color.GREEN));
            _playersOnline.setText(status.getOnlinePlayers() + " players online currently on Tranquility!");
        }
        else if (!status.getServerOpen() && !status.hasError()){
            _statusText.setText("Offline!");
            _statusText.setTextColor(ColorStateList.valueOf(Color.RED));
            _playersOnline.setText("There are no players currently online currently on Tranquility.");
        }
        else {
            _statusText.setText("Cannot reach server.");
            _statusText.setTextColor(ColorStateList.valueOf(Color.RED));
            _playersOnline.setText("Couldn't reach server to get player count.");
        }

        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        f.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        _serverTime.setText("Current server time: " + f.format(GregorianCalendar.getInstance().getTime()));
        _serverTimeSing.setText("Current server time: " + f.format(GregorianCalendar.getInstance().getTime()));

        if(serviceStatusStr != null && serviceStatusStr.equals("online")) {
            _statusTextSing.setText("Online!");
            _statusTextSing.setTextColor(ColorStateList.valueOf(Color.GREEN));
            _playersOnlineSing.setText(userCountsStr + " players online currently on Singularity!");
        }
        else if (serviceStatusStr != null && serviceStatusStr.equals("offline")) {
            _statusTextSing.setText("Offline!");
            _statusTextSing.setTextColor(ColorStateList.valueOf(Color.RED));
            _playersOnlineSing.setText("There are no players currently online on Singularity.");
        }
        else {
            _statusTextSing.setText("Cannot reach server.");
            _statusTextSing.setTextColor(ColorStateList.valueOf(Color.RED));
            _playersOnlineSing.setText("Couldn't reach server to get player count.");
        }
    }
}