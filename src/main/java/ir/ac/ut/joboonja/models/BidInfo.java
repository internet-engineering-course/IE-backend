package ir.ac.ut.joboonja.models;

public class BidInfo {
    private Integer userId;
    private String projectId;
    private Integer bidAmount;


    public BidInfo(Integer userId, String projectId, Integer bidAmount) {
        this.userId = userId;
        this.projectId = projectId;
        this.bidAmount = bidAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Integer bidAmount) {
        this.bidAmount = bidAmount;
    }
}
