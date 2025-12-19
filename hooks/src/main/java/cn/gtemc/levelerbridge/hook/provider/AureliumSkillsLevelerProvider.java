package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.data.PlayerData;
import com.archyx.aureliumskills.skills.Skill;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AureliumSkillsLevelerProvider implements LevelerProvider<Player> {
    public static final AureliumSkillsLevelerProvider INSTANCE = new AureliumSkillsLevelerProvider();

    private AureliumSkillsLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "aureliumskills";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        Skill skill = getSkill(target);
        if (skill == null) {
            return 0;
        }
        return AureliumAPI.getSkillLevel(player, skill);
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        Skill skill = getSkill(target);
        if (skill == null) {
            return 0;
        }
        return AureliumAPI.getXp(player, skill);
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        Skill skill = getSkill(target);
        if (skill == null) {
            return;
        }
        PlayerData playerData = AureliumAPI.getPlugin().getPlayerManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        playerData.setSkillLevel(skill, playerData.getSkillLevel(skill) + level);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        Skill skill = getSkill(target);
        if (skill == null) {
            return;
        }
        AureliumAPI.addXp(player, skill, experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        Skill skill = getSkill(target);
        if (skill == null) {
            return;
        }
        PlayerData playerData = AureliumAPI.getPlugin().getPlayerManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        playerData.setSkillLevel(skill, level);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        Skill skill = getSkill(target);
        if (skill == null) {
            return;
        }
        PlayerData playerData = AureliumAPI.getPlugin().getPlayerManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        playerData.setSkillXp(skill, experience);
    }

    @Nullable
    private static Skill getSkill(String target) {
        if (target == null) {
            return null;
        }
        return AureliumAPI.getPlugin().getSkillRegistry().getSkill(target);
    }
}
