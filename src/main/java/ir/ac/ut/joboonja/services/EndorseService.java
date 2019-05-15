package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.entities.Endorse;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.exceptions.ForbiddenException;
import ir.ac.ut.joboonja.models.EndorsableSkill;
import ir.ac.ut.joboonja.repositories.EndorseRepository;
import ir.ac.ut.joboonja.repositories.impl.EndorseRepositoryImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static ir.ac.ut.joboonja.services.UserService.getDefaultUser;

public class EndorseService {
    private static EndorseRepository endorseRepository = new EndorseRepositoryImpl();

    public static Endorse endorseSkill(Integer endorsedId, String skillName) {
        Integer endorserId = getDefaultUser().getId();
        Endorse endorse = new Endorse(endorserId, endorsedId, skillName);
        User user = UserService.getUserById(endorsedId);
        if (endorserId.equals(endorsedId))
            throw new ForbiddenException("You can't endorse yourself!");
        if (endorseRepository.endorseExists(endorse))
            throw new BadRequestException("Already Endorsed!");
        if (user.getSkills().indexOf(new Skill(skillName, 0)) == -1)
            throw new BadRequestException("User doesn't have endorsed skill!");

        endorseRepository.insertEndorse(endorse);
        UserService.updateUserSkillPoint(endorsedId, skillName, 1);
        return endorse;
    }

    public static List<EndorsableSkill> getUserEndorsableSkills(Integer endorserId, Integer endorsedId) {
        User endorsed = UserService.getUserById(endorsedId);

        if (endorsedId.equals(endorserId))
            return endorsed.getSkills().stream()
                .map(skill -> new EndorsableSkill(skill, false))
                .collect(Collectors.toList());

        List<Endorse> endorses = endorseRepository.getEndorses(endorserId);
        List<EndorsableSkill> result = new LinkedList<>();
        for (Skill skill: endorsed.getSkills()) {
            boolean endorsable = true;
            for (Endorse endorse : endorses)
                if (endorse.getEndorsedId().equals(endorsedId) && skill.getName().equals(endorse.getSkillName()))
                    endorsable = false;
            result.add(new EndorsableSkill(skill, endorsable));
        }
        return result;
    }
}
