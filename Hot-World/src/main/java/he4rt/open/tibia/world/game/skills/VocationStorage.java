package he4rt.open.tibia.world.game.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import he4rt.open.tibia.base.data.entity.PlayerAbilityEntity;
import he4rt.open.tibia.base.data.enums.AbilityEnum;
import he4rt.open.tibia.base.data.enums.JobEnum;
import he4rt.open.tibia.world.game.skills.dto.SkillsDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Getter
@Setter
@Component
public class VocationStorage {

    private Map<JobEnum, SkillsDTO> skillsDTO = new HashMap();

    public static Map<AbilityEnum, Pair<Integer, Integer>> rates = new HashMap();

    public VocationStorage(ObjectMapper objectMapper, Properties configProperties) throws IOException {

        SkillsDTO[] s = objectMapper.readValue(new File(String.format("%s%s", configProperties.getProperty("directoryData"), "vocation.json")), SkillsDTO[].class);

        for (SkillsDTO dto : s) {

            skillsDTO.put(dto.getName(), dto);
        }

        rates.put(AbilityEnum.FIST, Pair.of(50, 10));
        rates.put(AbilityEnum.CLUB, Pair.of(50, 10));
        rates.put(AbilityEnum.SWORD, Pair.of(50, 10));
        rates.put(AbilityEnum.AXE, Pair.of(50, 10));
        rates.put(AbilityEnum.DISTANCE, Pair.of(30, 10));
        rates.put(AbilityEnum.SHILDING, Pair.of(100, 10));
        rates.put(AbilityEnum.FISHING, Pair.of(20, 10));
        rates.put(AbilityEnum.MAGIC, Pair.of(1600, 10));

    }

    public double getPercent(PlayerAbilityEntity playerAbilityEntity, JobEnum jobEnum) {

        if (Objects.equals(playerAbilityEntity.getAbilityEnum(), AbilityEnum.LEVEL)) {

            var currentLevelExp = GetExpForLevel(playerAbilityEntity.getLevel());

            var nextLevelExp = GetExpForLevel(playerAbilityEntity.getLevel() + 1);

            return CalculatePercentage(playerAbilityEntity.getExp() - currentLevelExp, nextLevelExp - currentLevelExp);

        }

        if (Objects.equals(playerAbilityEntity.getAbilityEnum(), AbilityEnum.MAGIC)) {

            return GetManaPercentage(playerAbilityEntity.getAbilityEnum(), playerAbilityEntity.getExp(), playerAbilityEntity.getLevel());

        }

        Double rate = skillsDTO.get(jobEnum).getSkills().get(playerAbilityEntity.getAbilityEnum());

        return CalculatePercentage(playerAbilityEntity.getExp(), GetPointsForLevel(playerAbilityEntity.getAbilityEnum(), playerAbilityEntity.getLevel() + 1, rate));

    }

    private double GetManaPercentage(AbilityEnum abilityEnum, double manaSpent, int level) {
        Pair<Integer, Integer> rate = VocationStorage.rates.get(abilityEnum);

        var reqMana = 1600 * Math.pow(rate.getSecond(), level);
        var modResult = reqMana % 20;
        if (modResult < 10)
            reqMana -= modResult;
        else
            reqMana -= modResult + 20;

        if (manaSpent > reqMana) manaSpent = 0;

        return CalculatePercentage(manaSpent, reqMana);
    }

    private double GetPointsForLevel(AbilityEnum abilityEnum, int skillLevel, double vocationRate) {
        Pair<Integer, Integer> rate = VocationStorage.rates.get(abilityEnum);
        return rate.getFirst() * Math.pow(vocationRate, skillLevel - rate.getSecond());
    }

    private double CalculatePercentage(double count, double nextLevelCount) {
        return Math.min(100, count * 100 / nextLevelCount);
    }

    private double GetExpForLevel(int level) {
        return 50 * Math.pow(level, 3) / 3 - 100 * Math.pow(level, 2) + 850 * level / 3 - 200;
    }
}
