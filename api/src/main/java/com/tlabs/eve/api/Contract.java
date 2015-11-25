package com.tlabs.eve.api;



import java.io.Serializable;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

//http://wiki.eveonline.com/wikiEN/index.php?title=EVE_API_Character_Character_Contracts
public class Contract implements Serializable {

    private static final long serialVersionUID = -2606077397367047795L;

    public enum Status {
        UNKNOWN("Unknown", 0),
        IN_PROGRESS("InProgress", 1),
        OUTSTANDING("Outstanding", 2),
        COMPLETED_COMPLETED("Completed", 3),
        COMPLETED_ISSUER("CompletedByIssuer", 4),
        COMPLETED_CONTRACTOR("CompletedByContractor", 5),
        CANCELLED("Cancelled", 6),
        REJECTED("Rejected", 7),
        FAILED("Failed", 8),
        DELETED("Deleted", 9),
        REVERSED("Reversed", 10);

        private String name;
        private int value;

        Status(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public String getStatusName() {
            return this.name;
        }

        public static Status statusOf(String s) {
            for (Status st : EnumSet.allOf(Status.class)) {
                if (st.name.equalsIgnoreCase(s)) {
                    return st;
                }
            }
            return UNKNOWN;
        }

        public static Status statusOf(int a) {
            for (Status st : EnumSet.allOf(Status.class)) {
                if (st.value == a) {
                    return st;
                }
            }
            return UNKNOWN;
        }

        public static int statusInt(String s) {
            return statusOf(s).value;
        }
    }

    public enum Type {
        UNKNOWN("Unknown", 0),
        AUCTION("Auction", 1),
        EXCHANGE("ItemExchange", 2),
        COURIER("Courier", 3);

        private String name;
        private int value;

        Type(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getTypeName() {
            return this.name;
        }

        public int getValue() {
            return this.value;
        }

        public static Type typeOf(String s) {
            for (Type st : EnumSet.allOf(Type.class)) {
                if (st.name.equalsIgnoreCase(s)) {
                    return st;
                }
            }
            return UNKNOWN;
        }

        public static Type typeOf(int a) {
            for (Type st : EnumSet.allOf(Type.class)) {
                if (st.value == a) {
                    return st;
                }
            }
            return UNKNOWN;
        }

        public static int typeInt(String s) {
            return typeOf(s).value;
        }
    }

    private long contractID = -1;//contractID
    private long issuerID = -1;
    private String issuerName = "";//not in XML

    private long issuerCorpID = -1;
    private String issuerCorpName = "";//not in XML

    private long assigneeID = -1;
    private String assigneeName = "";//not in XML

    private long acceptorID = -1;
    private String acceptorName = "";//not in XML

    private long startStationID = -1;
    private String startStationName = "";//not in XML

    private long endStationID = -1;
    private String endStationName = "";//not in XML

    private long startSolarSystemID = -1;//not in XML
    private String startSolarSystemName = "";//not in XML

    private long endSolarSystemID = -1;
    private String endSolarSystemName = "";//not in XML

    private long forCorpID = -1;
    private String forCorpName = "";//not in XML

    private String title;
    private String availability;

    private Type type;
    private Status status;

    private long dateIssued;
    private long dateExpired;
    private long dateAccepted;
    private long dateCompleted;

    private int numDays;
    private double price;
    private double reward;
    private double collateral;
    private double buyout;
    private double volume;

    private List<ContractItem> items = new LinkedList<>();
    private List<ContractBid> bids = new LinkedList<>();

    public final long getContractID() {
        return contractID;
    }

    public final void setContractID(long contractID) {
        this.contractID = contractID;
    }

    public final long getIssuerID() {
        return issuerID;
    }

    public final void setIssuerID(long issuerID) {
        this.issuerID = issuerID;
    }

    public final long getIssuerCorpID() {
        return issuerCorpID;
    }

    public final void setIssuerCorpID(long issuerCorpID) {
        this.issuerCorpID = issuerCorpID;
    }

    public final long getAssigneeID() {
        return assigneeID;
    }

    public final void setAssigneeID(long assigneeID) {
        this.assigneeID = assigneeID;
    }

    public final long getAcceptorID() {
        return acceptorID;
    }

    public final void setAcceptorID(long acceptorID) {
        this.acceptorID = acceptorID;
    }

    public final long getStartStationID() {
        return startStationID;
    }

    public final void setStartStationID(long startStationID) {
        this.startStationID = startStationID;
    }

    public final long getEndStationID() {
        return endStationID;
    }

    public final void setEndStationID(long endStationID) {
        this.endStationID = endStationID;
    }

    public final long getForCorpID() {
        return forCorpID;
    }

    public final void setForCorpID(long forCorpID) {
        this.forCorpID = forCorpID;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final Type getType() {
        return type;
    }

    public final void setType(String type) {
        this.type = Type.typeOf(type);
    }

    public final void setType(int type) {
        this.type = Type.typeOf(type);
    }

    public final Status getStatus() {
        return status;
    }

    public final void setStatus(String status) {
        this.status = Status.statusOf(status);
    }

    public final void setStatus(int status) {
        this.status = Status.statusOf(status);
    }

    public final String getAvailability() {
        return availability;
    }

    public final void setAvailability(String availability) {
        this.availability = availability;
    }

    public final long getDateIssued() {
        return dateIssued;
    }

    public final void setDateIssued(long dateIssued) {
        this.dateIssued = dateIssued;
    }

    public final long getDateExpired() {
        return dateExpired;
    }

    public final void setDateExpired(long dateExpired) {
        this.dateExpired = dateExpired;
    }

    public final long getDateAccepted() {
        return dateAccepted;
    }

    public final void setDateAccepted(long dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public final long getDateCompleted() {
        return dateCompleted;
    }

    public final void setDateCompleted(long dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public final int getNumDays() {
        return numDays;
    }

    public final void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public final double getPrice() {
        return price;
    }

    public final void setPrice(double price) {
        this.price = price;
    }

    public final double getReward() {
        return reward;
    }

    public final void setReward(double reward) {
        this.reward = reward;
    }

    public final double getCollateral() {
        return collateral;
    }

    public final void setCollateral(double collateral) {
        this.collateral = collateral;
    }

    public final double getBuyout() {
        return buyout;
    }

    public final void setBuyout(double buyout) {
        this.buyout = buyout;
    }

    public final double getVolume() {
        return volume;
    }

    public final void setVolume(double volume) {
        this.volume = volume;
    }

    public final String getIssuerName() {
        return issuerName;
    }

    public final void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public final String getAssigneeName() {
        return assigneeName;
    }

    public final void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public final String getAcceptorName() {
        return acceptorName;
    }

    public final void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public final String getStartStationName() {
        return startStationName;
    }

    public final void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public final String getEndStationName() {
        return endStationName;
    }

    public final void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public final String getForCorpName() {
        return forCorpName;
    }

    public final void setForCorpName(String forCorpName) {
        this.forCorpName = forCorpName;
    }

    public final String getIssuerCorpName() {
        return issuerCorpName;
    }

    public final void setIssuerCorpName(String issuerCorpName) {
        this.issuerCorpName = issuerCorpName;
    }

    public List<ContractItem> getItems() {
        return items;
    }

    public void setItems(List<ContractItem> items) {
        this.items = items;
    }

    public List<ContractBid> getBids() {
        return bids;
    }

    public void setBids(List<ContractBid> bids) {
        this.bids = bids;
    }

    public long getStartSolarSystemID() {
        return startSolarSystemID;
    }

    public void setStartSolarSystemID(long startSolarSystemID) {
        this.startSolarSystemID = startSolarSystemID;
    }

    public String getStartSolarSystemName() {
        return startSolarSystemName;
    }

    public void setStartSolarSystemName(String startSolarSystemName) {
        this.startSolarSystemName = startSolarSystemName;
    }

    public long getEndSolarSystemID() {
        return endSolarSystemID;
    }

    public void setEndSolarSystemID(long endSolarSystemID) {
        this.endSolarSystemID = endSolarSystemID;
    }

    public String getEndSolarSystemName() {
        return endSolarSystemName;
    }

    public void setEndSolarSystemName(String endSolarSystemName) {
        this.endSolarSystemName = endSolarSystemName;
    }
}
