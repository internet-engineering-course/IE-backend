package database.impl;

import database.EndorseRepository;
import entities.Endorse;

import java.util.List;

public class EndorseRepositoryInMemoryImpl implements EndorseRepository {
    @Override
    public void insertEndorse(Endorse endorse) {
        MemoryDataBase.getInstance().insertEndorse(endorse);
    }

    @Override
    public List<Endorse> getEndorses(Integer endorserId) {
        return MemoryDataBase.getInstance().getEndorses(endorserId);
    }
}
