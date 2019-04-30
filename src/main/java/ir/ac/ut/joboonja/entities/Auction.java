package ir.ac.ut.joboonja.entities;

import ir.ac.ut.joboonja.models.BidInfo;

import java.util.LinkedList;
import java.util.List;

public class Auction {
    private String projectId;
    private List<BidInfo> offers;

    public Auction(String projectId, List<BidInfo> offers) {
        this.offers = offers;
        this.projectId = projectId;
    }

    public Auction(String projectId) {
        this.projectId = projectId;
        this.offers = new LinkedList<BidInfo>();
    }

    public List<BidInfo> getOffers() {
        return offers;
    }

    public void setOffers(List<BidInfo> offers) {
        this.offers = offers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Auction) {
            Auction project = (Auction)obj;
            return projectId.equals(project.projectId);
        }
        else {
            return false;
        }
    }

    public void addOffer(BidInfo bidInfo) {
        offers.add(bidInfo);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
