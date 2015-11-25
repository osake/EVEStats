package com.tlabs.eve.api.mail;



import com.tlabs.eve.api.character.CharacterRequest;

///char/MailMessages.xml.aspx
public final class MailMessagesRequest extends CharacterRequest<MailMessagesResponse> {
    public static final int MASK = 2048;

    public MailMessagesRequest(String characterID) {
        super(MailMessagesResponse.class, "/char/MailMessages.xml.aspx", MASK, characterID);
    }

}
