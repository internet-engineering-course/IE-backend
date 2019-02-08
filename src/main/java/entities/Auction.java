package entities;

import models.BidInfo;

import java.util.LinkedList;
import java.util.List;

public class Auction {
    private String projectTitle;
    private List<BidInfo> offers;

    public Auction(String projectTitle, List<BidInfo> offers) {
        this.projectTitle = projectTitle;
        this.offers = offers;
    }

    public Auction(String projectTitle) {
        this.projectTitle = projectTitle;
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
            return projectTitle.equals(project.projectTitle);
        }
        else {
            return false;
        }
    }

    public void addOffer(BidInfo bidInfo) {
        offers.add(bidInfo);
    }
}
