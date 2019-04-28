package ir.ac.ut.joboonja;

import ir.ac.ut.joboonja.database.SchemaManager;
import ir.ac.ut.joboonja.repositories.impl.memory.MemoryDataBase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Application {
    @PostConstruct
    public void init() {
        SchemaManager.initialSchema();
        SchemaManager.syncData();
    }
}
