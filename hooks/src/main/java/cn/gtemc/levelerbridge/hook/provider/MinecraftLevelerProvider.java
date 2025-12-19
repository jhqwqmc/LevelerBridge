package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MinecraftLevelerProvider implements LevelerProvider<Player> {
    public static final MinecraftLevelerProvider INSTANCE = new MinecraftLevelerProvider();

    private MinecraftLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "minecraft";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        return player.getLevel();
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        return player.getExp();
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        player.giveExpLevels(level);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        player.giveExp((int) experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        player.setLevel(level);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        if (experience < player.getExperiencePointsNeededForNextLevel()) {
            float xpNeededForNextLevel = player.getExperiencePointsNeededForNextLevel();
            float maxProgressThreshold = (xpNeededForNextLevel - 1.0F) / xpNeededForNextLevel;
            float experienceProgress = MiscUtils.clamp((float) (experience / xpNeededForNextLevel), 0.0F, maxProgressThreshold);
            player.setExp(experienceProgress);
        }
    }
}
