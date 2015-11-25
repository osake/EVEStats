package com.tlabs.eve.api.character;



import com.tlabs.eve.api.WalletTransactionsResponse;

public final class CharacterWalletTransactionsRequest extends CharacterRequest<WalletTransactionsResponse> {
    public static final int MASK = 4194304;

    public CharacterWalletTransactionsRequest(String characterID) {
        this(characterID, 50, -1);
    }

    public CharacterWalletTransactionsRequest(String characterID, int rowCount, long fromID) {
        super(WalletTransactionsResponse.class, "/char/WalletTransactions.xml.aspx", MASK, characterID);
        putParam("accountKey", Integer.toString(1000));
        putParam("rowCount", Integer.toString(rowCount));
        if (-1 != fromID) {
            putParam("fromID", Long.toString(fromID));
        }
    }
}
