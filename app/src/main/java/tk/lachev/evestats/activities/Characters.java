package tk.lachev.evestats.activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tk.lachev.evestats.R;
import utils.Character;
import utils.DatabaseHandler;
import utils.RVAdapter;

public class Characters extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DatabaseHandler db = new DatabaseHandler(this);
        List<Character> characterList = db.getAllCharacters();

        RVAdapter adapter = new RVAdapter(characterList);
        recyclerView.setAdapter(adapter);
    }
    private String[] getNames(List<Character> list) {

        List<String> namesList = new ArrayList<String>();

        Log.d("length", String.format("%d", list.size()));
        Character c = list.get(0);
        Log.d("name", c.get_name());
        Log.d("isempty", String.format("%b", list.isEmpty()));

        if (!list.isEmpty() && list.size() > 0) {
            Character character;
            for (int i = 0; i < list.size(); i++) {
                character = list.get(i);
                Log.d("counter", String.format("%d", i));
                namesList.add(i, character.get_name());
            }
        }
        String[] names = new String[namesList.size()];
        names = namesList.toArray(names);
        Log.d("NAMES LIST FROM NAME", names.toString());
        return names;
    }
}
