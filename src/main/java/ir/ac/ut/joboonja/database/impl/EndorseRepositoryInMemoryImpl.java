package ir.ac.ut.joboonja.database.impl;

import ir.ac.ut.joboonja.database.EndorseRepository;
import ir.ac.ut.joboonja.entities.Endorse;

import java.util.List;

public class EndorseRepositoryInMemoryImpl implements EndorseRepository {
    @Override
    public void insertEndorse(Endorse endorse) {
        MemoryDataBase.getInstance().insertEndorse(endorse);
    }

    @Override
    public boolean endorseExists(Endorse endorse) {
        return MemoryDataBase.getInstance().endorseExists(endorse);
    }

    @Override
    public List<Endorse> getEndorses(Integer endorserId) {
        return MemoryDataBase.getInstance().getEndorses(endorserId);
    }
}
