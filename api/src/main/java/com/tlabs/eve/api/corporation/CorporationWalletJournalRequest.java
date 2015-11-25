package com.tlabs.eve.api.corporation;



import com.tlabs.eve.api.WalletJournalResponse;

public final class CorporationWalletJournalRequest extends CorporationRequest<WalletJournalResponse> {

    public static final int MASK = 1048576;

    public CorporationWalletJournalRequest(String corporationID, int walletID) {
        this(corporationID, walletID, 50, -1);
    }

    public CorporationWalletJournalRequest(String corporationID, int walletID, int rowCount, long fromID) {
        super(WalletJournalResponse.class, "/corp/WalletJournal.xml.aspx", MASK, corporationID);
        putParam("accountKey", Integer.toString(walletID));
        putParam("rowCount", Integer.toString(rowCount));
        if (-1 != fromID) {
            putParam("fromID", Long.toString(fromID));
        }
    }

}
