package cn.gtemc.levelerbridge.api;

import org.jetbrains.annotations.NotNull;

public interface LevelerProvider<P> {

    @NotNull String plugin();

    int getLevel(@NotNull P player, String target);

    double getExperience(@NotNull P player, String target);

    void addLevel(@NotNull P player, String target, int level);

    void addExperience(@NotNull P player, String target, double experience);

    void setLevel(@NotNull P player, String target, int level);

    void setExperience(@NotNull P player, String target, double experience);
}
