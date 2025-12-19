package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.skills.Skill;
import com.willfp.ecoskills.skills.SkillKt;
import com.willfp.ecoskills.skills.SkillLevel;
import com.willfp.ecoskills.skills.Skills;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EcoSkillsLevelerProvider implements LevelerProvider<Player> {
    public static final EcoSkillsLevelerProvider INSTANCE = new EcoSkillsLevelerProvider();

    private EcoSkillsLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "ecoskills";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        Skill byID = Skills.INSTANCE.getByID(target);
        if (byID == null) {
            return 0;
        }
        return EcoSkillsAPI.getSkillLevel(player, byID);
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        Skill byID = Skills.INSTANCE.getByID(target);
        if (byID == null) {
            return 0;
        }
        return EcoSkillsAPI.getSkillXP(player, byID);
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        Skill byID = Skills.INSTANCE.getByID(target);
        if (byID == null) {
            return;
        }
        EcoSkillsAPI.setSkillLevel(player, byID, EcoSkillsAPI.getSkillLevel(player, byID) + level);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        Skill byID = Skills.INSTANCE.getByID(target);
        if (byID == null) {
            return;
        }
        EcoSkillsAPI.giveSkillXP(player, byID, experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        Skill byID = Skills.INSTANCE.getByID(target);
        if (byID == null) {
            return;
        }
        EcoSkillsAPI.setSkillLevel(player, byID, level);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        Skill byID = Skills.INSTANCE.getByID(target);
        if (byID == null) {
            return;
        }
        int level = SkillKt.getSkills(player).get(byID).getLevel();
        SkillKt.getSkills(player).set(byID, new SkillLevel(level, experience));
    }
}
