package models;

public class BidInfo {
    private String biddingUser;
    private String projectTitle;
    private Integer bidAmount;

    public BidInfo() {}

    public BidInfo(String biddingUser, String projectTitle, Integer bidAmount) {
        this.biddingUser = biddingUser;
        this.projectTitle = projectTitle;
        this.bidAmount = bidAmount;
    }

    public String getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(String biddingUser) {
        this.biddingUser = biddingUser;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Integer getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Integer bidAmount) {
        this.bidAmount = bidAmount;
    }
}
