package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Endorse;

import java.util.List;

public interface EndorseRepository {
    void insertEndorse(Endorse endorse);
    boolean endorseExists(Endorse endorse);
    List<Endorse> getEndorses(Integer endorserId);
}
