package ir.ac.ut.joboonja;

import ir.ac.ut.joboonja.database.impl.MemoryDataBase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Application {
    @PostConstruct
    public void init() {
        MemoryDataBase.getInstance().initialize();
    }
}
