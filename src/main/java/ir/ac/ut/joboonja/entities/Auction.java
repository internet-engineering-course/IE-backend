package ir.ac.ut.joboonja.entities;

import ir.ac.ut.joboonja.models.BidInfo;

import java.util.LinkedList;
import java.util.List;

public class Auction {
    private String projectTitle;
    private List<BidInfo> offers;
    private String id;

    public Auction(String id, List<BidInfo> offers) {
        this.offers = offers;
        this.id = id;
    }

    public Auction(String id) {
        this.id = id;
        this.offers = new LinkedList<BidInfo>();
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
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
            return id.equals(project.id);
        }
        else {
            return false;
        }
    }

    public void addOffer(BidInfo bidInfo) {
        offers.add(bidInfo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
