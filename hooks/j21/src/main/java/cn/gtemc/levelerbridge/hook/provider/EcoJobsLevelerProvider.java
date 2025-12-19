package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import com.willfp.ecojobs.api.EcoJobsAPI;
import com.willfp.ecojobs.jobs.Job;
import com.willfp.ecojobs.jobs.Jobs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EcoJobsLevelerProvider implements LevelerProvider<Player> {
    public static final EcoJobsLevelerProvider INSTANCE = new EcoJobsLevelerProvider();

    private EcoJobsLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "ecojobs";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        Job job = Jobs.getByID(target);
        if (job == null) {
            return 0;
        }
        return EcoJobsAPI.getJobLevel(player, job);
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        Job job = Jobs.getByID(target);
        if (job == null) {
            return 0;
        }
        return EcoJobsAPI.getJobXP(player, job);
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        Job job = Jobs.getByID(target);
        if (job == null) {
            return;
        }
        EcoJobsAPI.setJobLevel(player, job, EcoJobsAPI.getJobLevel(player, job) + level);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        Job job = Jobs.getByID(target);
        if (job == null) {
            return;
        }
        EcoJobsAPI.giveJobExperience(player, job, experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        Job job = Jobs.getByID(target);
        if (job == null) {
            return;
        }
        EcoJobsAPI.setJobLevel(player, job, level);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        Job job = Jobs.getByID(target);
        if (job == null) {
            return;
        }
        EcoJobsAPI.setJobXP(player, job, experience);
    }
}
