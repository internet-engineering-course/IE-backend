package ir.ac.ut.joboonja;

import ir.ac.ut.joboonja.database.SchemaManager;
import ir.ac.ut.joboonja.services.AuctionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Application {
    @PostConstruct
    public void init() {
        System.out.println("\n\n\n &&&&&&&&&&&&&&&&& \n\n\n");
        SchemaManager.initialSchema();
    }
}
