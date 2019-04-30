package ir.ac.ut.joboonja.entities;

import java.util.LinkedList;
import java.util.List;

public class Auction {
    private String projectId;
    private List<Bid> offers;

    public Auction(String projectId, List<Bid> offers) {
        this.offers = offers;
        this.projectId = projectId;
    }

    public Auction(String projectId) {
        this.projectId = projectId;
        this.offers = new LinkedList<Bid>();
    }

    public List<Bid> getOffers() {
        return offers;
    }

    public void setOffers(List<Bid> offers) {
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

    public void addOffer(Bid bid) {
        offers.add(bid);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
