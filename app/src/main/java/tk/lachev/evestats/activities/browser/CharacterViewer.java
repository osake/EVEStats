package tk.lachev.evestats.activities.browser;

import android.os.Bundle;
import android.app.Activity;

import tk.lachev.evestats.R;

public class CharacterViewer extends Activity {

    Character _c;

    public CharacterViewer(Character c) {
        this._c = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
