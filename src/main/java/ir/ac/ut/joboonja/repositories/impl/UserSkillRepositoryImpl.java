package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.ResourcePool;
import ir.ac.ut.joboonja.entities.UserSkill;
import ir.ac.ut.joboonja.repositories.UserSkillRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSkillRepositoryImpl implements UserSkillRepository {
    @Override
    public void insert(UserSkill userSkill) {

    }

    public static String getCreateScript() {
        return "create table if not exists UserSkill\n" +
                "(\n" +
                "\tuserId integer\n" +
                "\t\tconstraint UserSkill_User_id_fk\n" +
                "\t\t\treferences User\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tskillName varchar(100)\n" +
                "\t\tconstraint UserSkill_Skill_id_fk\n" +
                "\t\t\treferences Skill\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tpoints integer,\n" +
                "\tprimary key (userId, skillName)\n" +
                ");";
    }
}
