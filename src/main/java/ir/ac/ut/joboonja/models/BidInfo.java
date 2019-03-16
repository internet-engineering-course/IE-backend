package ir.ac.ut.joboonja.models;

public class BidInfo {
    private Integer biddingUser;
    private String id;
    private Integer bidAmount;


    public BidInfo(Integer biddingUser, String id, Integer bidAmount) {
        this.biddingUser = biddingUser;
        this.id = id;
        this.bidAmount = bidAmount;
    }

    public Integer getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(Integer biddingUser) {
        this.biddingUser = biddingUser;
    }

    public String getProjectTitle() {
        return id;
    }

    public void setProjectTitle(String id) {
        this.id = id;
    }

    public Integer getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Integer bidAmount) {
        this.bidAmount = bidAmount;
    }
}
