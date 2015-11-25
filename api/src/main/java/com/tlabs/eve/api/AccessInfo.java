package com.tlabs.eve.api;



import com.tlabs.eve.api.character.CharacterSheet;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**@since Eve API V3 (30 Aug 2011*/
public class AccessInfo implements Serializable {

    private static final long serialVersionUID = -4920885693131283908L;

    public static final int UNKNOWN = -1;
    public static final int CHARACTER = 0;
    public static final int CORPORATION = 1;
    public static final int ACCOUNT = 2;

    private int accessMask = 0;

    private int type = UNKNOWN;

    private long expires = 0;
    private long keyID = -1;
    private String key = "";

    private String name;//Not in XML

    private List<CharacterSheet> characters = new LinkedList<>();

    public AccessInfo() {
        super();
    }

    public final int getAccessMask() {
        return accessMask;
    }

    public final void setAccessMask(int accessMask) {
        this.accessMask = accessMask;
    }

    public final int getType() {
        return type;
    }

    public final void setType(String type) {
        if ("Account".equalsIgnoreCase(type)) {
            this.type = ACCOUNT;
            return;
        }
        if ("Character".equalsIgnoreCase(type)) {
            this.type = CHARACTER;
            return;
        }
        if ("Corporation".equalsIgnoreCase(type)) {
            this.type = CORPORATION;
            return;
        }

        this.type = UNKNOWN;
    }

    public final void setType(int type) {
        this.type = type;
    }

    public final long getExpires() {
        return expires;
    }

    public final void setExpires(long expires) {
        this.expires = expires;
    }

    public final void setExpires(String expires) {
        this.expires = EveAPI.parseDateTime(expires);
    }

    public final long getKeyID() {
        return keyID;
    }

    public final void setKeyID(long keyID) {
        this.keyID = keyID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addCharacter(CharacterSheet c) {
        this.characters.add(c);
    }

    public List<CharacterSheet> getCharacters() {
        return this.characters;
    }

    public String getName() {
        return (null == name) ? Long.toString(this.keyID) : this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
