package utils;

/**
 * Created by Boris Lachev on 11/28/2015.
 */
public class Character {
    int _id;
    String _apiKey;
    String _keyId;

    public Character(String _apiKey, String _keyId) {
        this._apiKey = _apiKey;
        this._keyId = _keyId;
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

    public Character(int _id, String _apiKey, String _keyId) {

        this._id = _id;
        this._apiKey = _apiKey;
        this._keyId = _keyId;
    }

    public Character() {

    }
}
