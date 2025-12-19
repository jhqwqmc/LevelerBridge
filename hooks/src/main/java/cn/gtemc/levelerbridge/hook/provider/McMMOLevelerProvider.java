package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.exceptions.InvalidSkillException;
import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.player.UserManager;
import com.gmail.nossr50.util.skills.SkillTools;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class McMMOLevelerProvider implements LevelerProvider<Player> {
    public static final McMMOLevelerProvider INSTANCE = new McMMOLevelerProvider();

    private McMMOLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "mcmmo";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        PrimarySkillType skillType = getPrimarySkillType(target);
        if (skillType == null) {
            return 0;
        }
        return ExperienceAPI.getLevel(player, skillType);
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        return ExperienceAPI.getXP(player, target);
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        ExperienceAPI.addLevel(player, target, level);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        ExperienceAPI.addRawXP(player, target, (float) experience, "UNKNOWN");
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        ExperienceAPI.setLevel(player, target, level);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        PrimarySkillType skillType = getNonChildSkillType(target);
        if (skillType == null) {
            return;
        }
        getPlayer(player).setSkillXpLevel(skillType, (float) experience);
    }

    @Nullable
    private static PrimarySkillType getPrimarySkillType(String target) {
        if (target == null) {
            return null;
        }
        return mcMMO.p.getSkillTools().matchSkill(target);
    }

    private static McMMOPlayer getPlayer(Player player) throws McMMOPlayerNotFoundException {
        if (!UserManager.hasPlayerDataKey(player)) {
            throw new McMMOPlayerNotFoundException(player);
        } else {
            return UserManager.getPlayer(player);
        }
    }

    @Nullable
    private static PrimarySkillType getNonChildSkillType(String skillType) throws InvalidSkillException, UnsupportedOperationException {
        PrimarySkillType skill = getPrimarySkillType(skillType);
        if (skill == null) {
            return null;
        }
        if (SkillTools.isChildSkill(skill)) {
            throw new UnsupportedOperationException("Child skills do not have XP");
        } else {
            return skill;
        }
    }
}
