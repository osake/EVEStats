package com.tlabs.eve.api.character;



import com.tlabs.eve.api.EveAPIRequest;
import com.tlabs.eve.api.EveAPIRequest.Authenticated;
import com.tlabs.eve.api.EveAPIResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public abstract class CharacterRequest<T extends EveAPIResponse> extends EveAPIRequest<T> implements Authenticated {

    private String keyID = null;
    private String key = null;

    private String characterID;

    public CharacterRequest(Class<T> tea, String page, int mask, String characterID) {
        super(tea, page, mask);
        Validate.isTrue(StringUtils.isNotBlank(characterID), "characterID");

        putParam("characterID", characterID);
        this.characterID = characterID;
    }

    public final String getCharacterID() {
        return characterID;
    }

    public final String getKeyID() {
        return keyID;
    }

    public final void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public final String getKey() {
        return key;
    }

    public final void setKey(String key) {
        this.key = key;
    }

}
