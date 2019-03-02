package database;

import entities.Endorse;

import java.util.List;

public interface EndorseRepository {
    void insertEndorse(Endorse endorse);
    List<Endorse> getEndorses(Integer endorserId);
}
