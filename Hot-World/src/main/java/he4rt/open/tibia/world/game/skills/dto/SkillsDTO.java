package he4rt.open.tibia.world.game.skills.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import he4rt.open.tibia.base.data.enums.AbilityEnum;
import he4rt.open.tibia.base.data.enums.JobEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class SkillsDTO {
    private int id;

    @JsonDeserialize(using = JsonJobNameDesserialize.class)
    private JobEnum name;
    private String description;

    private int clientid;
    private int gainHpAmount;
    private int gainManaAmount;
    private int attackspeed;
    private int basespeed;
    private int soulmax;
    private int gainsoulticks;
    private int fromvoc;

    private int gaincap;
    private int gainhp;
    private int gainmana;
    private int gainhpticks;
    private int gainhpamount;
    private int gainmanaticks;
    private int gainmanaamount;

    private FormulaDTO formula;

    @JsonDeserialize(using = JsonSkillMultiplierDesserialize.class)
    private Map<AbilityEnum, Double> skills;
    private double manamultiplier;


}
