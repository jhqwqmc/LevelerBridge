package cn.gtemc.levelerbridge.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface LevelerBridge<P> {

    Optional<LevelerProvider<P>> provider(@NotNull String plugin);

    Collection<LevelerProvider<P>> providers();

    int getLevel(@NotNull String plugin, @NotNull P player, String target);

    double getExperience(@NotNull String plugin, @NotNull P player, String target);

    void addLevel(@NotNull String plugin, @NotNull P player, String target, int level);

    void addExperience(@NotNull String plugin, @NotNull P player, String target, double experience);

    void setLevel(@NotNull String plugin, @NotNull P player, String target, int level);

    void setExperience(@NotNull String plugin, @NotNull P player, String target, double experience);

    interface Builder<P> {

        Builder<P> register(LevelerProvider<P> provider);

        @Nullable LevelerProvider<P> removeById(String id);

        LevelerBridge<P> build();
    }
}
