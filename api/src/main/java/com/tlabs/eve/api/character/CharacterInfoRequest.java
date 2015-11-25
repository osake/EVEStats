/**
 * 
 */
package com.tlabs.eve.api.character;



import com.tlabs.eve.api.EveAPIRequest.Public;

public final class CharacterInfoRequest extends CharacterRequest<CharacterInfoResponse> implements Public {
    public static final int MASK = 16777216;

    public CharacterInfoRequest(String charID) {
        super(CharacterInfoResponse.class, "/eve/CharacterInfo.xml.aspx", MASK, charID);
    }
}
