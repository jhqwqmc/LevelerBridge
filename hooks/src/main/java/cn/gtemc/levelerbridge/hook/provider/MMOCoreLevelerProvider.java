package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerLevelChangeEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.PlayerProfessions;
import net.Indyuce.mmocore.experience.Profession;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MMOCoreLevelerProvider implements LevelerProvider<Player> {
    public static final MMOCoreLevelerProvider INSTANCE = new MMOCoreLevelerProvider();

    private MMOCoreLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "mmocore";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        Profession profession = MMOCore.plugin.professionManager.get(target);
        if (profession == null) {
            return 0;
        }
        return PlayerData.get(player).getCollectionSkills().getLevel(profession);
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        Profession profession = MMOCore.plugin.professionManager.get(target);
        if (profession == null) {
            return 0;
        }
        return PlayerData.get(player).getCollectionSkills().getExperience(profession);
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        Profession profession = MMOCore.plugin.professionManager.get(target);
        if (profession == null) {
            return;
        }
        PlayerProfessions skills = PlayerData.get(player).getCollectionSkills();
        skills.setLevel(profession, skills.getLevel(profession) + level, PlayerLevelChangeEvent.Reason.UNKNOWN);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        Profession profession = MMOCore.plugin.professionManager.get(target);
        if (profession == null) {
            return;
        }
        PlayerProfessions skills = PlayerData.get(player).getCollectionSkills();
        skills.setExperience(profession, skills.getExperience(profession) + experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        Profession profession = MMOCore.plugin.professionManager.get(target);
        if (profession == null) {
            return;
        }
        PlayerProfessions skills = PlayerData.get(player).getCollectionSkills();
        skills.setLevel(profession, level, PlayerLevelChangeEvent.Reason.UNKNOWN);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        Profession profession = MMOCore.plugin.professionManager.get(target);
        if (profession == null) {
            return;
        }
        PlayerProfessions skills = PlayerData.get(player).getCollectionSkills();
        skills.setExperience(profession, experience);
    }
}
