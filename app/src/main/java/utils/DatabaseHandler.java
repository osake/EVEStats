package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris Lachev on 11/28/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "charactersManager";
    private static final String TABLE_CHARACTERS = "characters";

    private static final String KEY_ID = "id";
    private static final String KEY_API_KEY = "apiKey";
    private static final String KEY_ID_KEY = "keyId";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHARACTERS_TABLE = "CREATE TABLE " + TABLE_CHARACTERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_API_KEY + " TEXT,"
                + KEY_ID_KEY + " TEXT," + KEY_NAME + " TEXT"+ ")";
        db.execSQL(CREATE_CHARACTERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTERS);

        onCreate(db);
    }

    public void addCharacter(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_API_KEY, character.get_apiKey());
        values.put(KEY_ID_KEY, character.get_keyId());
        values.put(KEY_NAME, character.get_name());

        db.insert(TABLE_CHARACTERS, null, values);
        db.close();
    }

    public Character getCharacter(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHARACTERS, new String[] {KEY_ID, KEY_API_KEY, KEY_ID_KEY}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Character character = new Character(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return character;
    }

    public List<Character> getAllCharacters() {
        List<Character> characterList = new ArrayList<Character>();
        String selectQuery = "SELECT * FROM " + TABLE_CHARACTERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Character character = new Character();
                character.set_id(Integer.parseInt(cursor.getString(0)));
                character.set_apiKey(cursor.getString(1));
                character.set_keyId(cursor.getString(2));
                character.set_name(cursor.getString(3));
                characterList.add(character);
            } while (cursor.moveToNext());
        }
        return characterList;
    }

    public int getCharacterCount() {
        String countQuery = "SELECT * FROM " + TABLE_CHARACTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        cursor.close();

        return cursor.getCount();
    }

    public int updateCharacter(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_API_KEY, character.get_apiKey());
        values.put(KEY_ID_KEY, character.get_keyId());

        return db.update(TABLE_CHARACTERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(character.get_id())});
    }

    public void deleteCharacter(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHARACTERS, KEY_ID + " = ?",new String[] {String.valueOf(character.get_id())});
        db.close();
    }
}
