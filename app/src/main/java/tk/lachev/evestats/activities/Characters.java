package tk.lachev.evestats.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tlabs.eve.EveNetwork;
import com.tlabs.eve.api.NamesRequest;
import com.tlabs.eve.api.NamesResponse;
import com.tlabs.eve.net.DefaultEveNetwork;

import java.util.List;

import tk.lachev.evestats.R;
import utils.*;
import utils.Character;

public class Characters extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_characters);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Character> list = db.getAllCharacters();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] names = getNames(list);

        adapter = new RecyclerAdapter(names);


        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (list.isEmpty()) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText("There doesn't seem to be anything here. Add characters by selecting the option in the menu.");
        }
    }

    private String[] getNames(List<Character> list) {
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).get_name();
        }

        return new String[0];
    }

    public class GetNames extends AsyncTask<EveNetwork, List<Character>, String[]> {
        NamesResponse response;

        List<Character> _list;
        String[] _names;
        public GetNames(List<Character> _list) {
            this._list = _list;
        }

        @Override
        protected String[] doInBackground(EveNetwork... params) {

            final EveNetwork eveNetwork = new DefaultEveNetwork();

            final NamesRequest namesRequest;

            return _names;
        }
    }
}
