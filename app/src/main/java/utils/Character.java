package utils;

/**
 * Created by Boris Lachev on 11/28/2015.
 */
public class Character {
    int _id;
    String _apiKey;
    String _keyId;
    String _name;

    public Character(String apiKey, String keyId, String name) {
        this._apiKey = apiKey;
        this._keyId = keyId;
        this._name = name;
    }

    public Character() {

    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", get_keyId(), get_apiKey(),get_name());
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_apiKey() {
        return _apiKey;
    }

    public void set_apiKey(String _apiKey) {
        this._apiKey = _apiKey;
    }

    public String get_keyId() {
        return _keyId;
    }

    public void set_keyId(String _keyId) {
        this._keyId = _keyId;
    }

    public Character(int i, String string, String cursorString, String s) {

    }
}
