package cn.gtemc.levelerbridge.hook.provider;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JobsRebornLevelerProvider implements LevelerProvider<Player> {
    public static final JobsRebornLevelerProvider INSTANCE = new JobsRebornLevelerProvider();

    private JobsRebornLevelerProvider() {}

    @Override
    public @NotNull String plugin() {
        return "jobs";
    }

    @Override
    public int getLevel(@NotNull Player player, String target) {
        JobProgression jobProgression = getJobProgression(player, target);
        if (jobProgression == null) {
            return 0;
        }
        return jobProgression.getLevel();
    }

    @Override
    public double getExperience(@NotNull Player player, String target) {
        JobProgression jobProgression = getJobProgression(player, target);
        if (jobProgression == null) {
            return 0;
        }
        return jobProgression.getExperience();
    }

    @Override
    public void addLevel(@NotNull Player player, String target, int level) {
        JobProgression jobProgression = getJobProgression(player, target);
        if (jobProgression == null) {
            return;
        }
        jobProgression.setLevel(jobProgression.getLevel() + level);
    }

    @Override
    public void addExperience(@NotNull Player player, String target, double experience) {
        JobProgression jobProgression = getJobProgression(player, target);
        if (jobProgression == null) {
            return;
        }
        jobProgression.addExperience(experience);
    }

    @Override
    public void setLevel(@NotNull Player player, String target, int level) {
        JobProgression jobProgression = getJobProgression(player, target);
        if (jobProgression == null) {
            return;
        }
        jobProgression.setLevel(level);
    }

    @Override
    public void setExperience(@NotNull Player player, String target, double experience) {
        JobProgression jobProgression = getJobProgression(player, target);
        if (jobProgression == null) {
            return;
        }
        jobProgression.setExperience(experience);
    }

    @Nullable
    private static JobProgression getJobProgression(Player player, String target) {
        JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
        if (jobsPlayer == null) {
            return null;
        }
        Job job = Jobs.getJob(target);
        if (job == null) {
            return null;
        }
        return jobsPlayer.getJobProgression(job);
    }
}
