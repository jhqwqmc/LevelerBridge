package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.registry.NamespacedId;
import dev.aurelium.auraskills.api.skill.Skill;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AuraSkillsLevelerProvider implements LevelerProvider<Player> {
    public static final AuraSkillsLevelerProvider INSTANCE = new AuraSkillsLevelerProvider();

    private AuraSkillsLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "auraskills";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        if (target == null) {
            return 0;
        }
        Skill skill = AuraSkillsApi.get().getGlobalRegistry().getSkill(NamespacedId.fromDefault(target));
        if (skill == null) {
            return 0;
        }
        return AuraSkillsApi.get().getUser(player.getUniqueId()).getSkillLevel(skill);
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        if (target == null) {
            return 0;
        }
        Skill skill = AuraSkillsApi.get().getGlobalRegistry().getSkill(NamespacedId.fromDefault(target));
        if (skill == null) {
            return 0;
        }
        return AuraSkillsApi.get().getUser(player.getUniqueId()).getSkillXp(skill);
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        if (target == null) {
            return;
        }
        Skill skill = AuraSkillsApi.get().getGlobalRegistry().getSkill(NamespacedId.fromDefault(target));
        if (skill == null) {
            return;
        }
        int skillLevel = AuraSkillsApi.get().getUser(player.getUniqueId()).getSkillLevel(skill);
        AuraSkillsApi.get().getUser(player.getUniqueId()).setSkillLevel(skill, skillLevel + level, true);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        if (target == null) {
            return;
        }
        Skill skill = AuraSkillsApi.get().getGlobalRegistry().getSkill(NamespacedId.fromDefault(target));
        if (skill == null) {
            return;
        }
        AuraSkillsApi.get().getUser(player.getUniqueId()).addSkillXp(skill, experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        if (target == null) {
            return;
        }
        Skill skill = AuraSkillsApi.get().getGlobalRegistry().getSkill(NamespacedId.fromDefault(target));
        if (skill == null) {
            return;
        }
        AuraSkillsApi.get().getUser(player.getUniqueId()).setSkillLevel(skill, level, true);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        if (target == null) {
            return;
        }
        Skill skill = AuraSkillsApi.get().getGlobalRegistry().getSkill(NamespacedId.fromDefault(target));
        if (skill == null) {
            return;
        }
        AuraSkillsApi.get().getUser(player.getUniqueId()).setSkillXp(skill, experience);
    }
}
