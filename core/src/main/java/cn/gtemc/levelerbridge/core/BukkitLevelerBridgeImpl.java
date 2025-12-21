package cn.gtemc.levelerbridge.core;

import cn.gtemc.levelerbridge.api.LevelerBridgeException;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.hook.HookHelper;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

final class BukkitLevelerBridgeImpl implements BukkitLevelerBridge {
    private final Map<String, LevelerProvider<Player>> providers;

    private BukkitLevelerBridgeImpl(Map<String, LevelerProvider<Player>> providers) {
        this.providers = Collections.unmodifiableMap(providers);
    }

    @Override
    public Optional<LevelerProvider<Player>> provider(@NotNull String plugin) {
        return Optional.ofNullable(this.providers.get(plugin));
    }

    @Override
    public Collection<LevelerProvider<Player>> providers() {
        return this.providers.values();
    }

    @Override
    public int getLevel(@NotNull String plugin, @NotNull Player player, String target) {
        LevelerProvider<Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return 0;
        }
        return provider.getLevel(player, target);
    }

    @Override
    public double getExperience(@NotNull String plugin, @NotNull Player player, String target) {
        LevelerProvider<Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return 0;
        }
        return provider.getExperience(player, target);
    }

    @Override
    public void addLevel(@NotNull String plugin, @NotNull Player player, String target, int level) {
        LevelerProvider<Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return;
        }
        provider.addLevel(player, target, level);
    }

    @Override
    public void addExperience(@NotNull String plugin, @NotNull Player player, String target, double experience) {
        LevelerProvider<Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return;
        }
        provider.addExperience(player, target, experience);
    }

    @Override
    public void setLevel(@NotNull String plugin, @NotNull Player player, String target, int level) {
        LevelerProvider<Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return;
        }
        provider.setLevel(player, target, level);
    }

    @Override
    public void setExperience(@NotNull String plugin, @NotNull Player player, String target, double experience) {
        LevelerProvider<Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return;
        }
        provider.setExperience(player, target, experience);
    }

    final static class BukkitBuilderImpl implements BukkitBuilder {
        private final Map<String, LevelerProvider<Player>> providers;

        BukkitBuilderImpl(boolean loggingEnabled) {
            this.providers = HookHelper.getSupportedPlugins(loggingEnabled);
        }

        @Override
        public BukkitBuilder register(LevelerProvider<Player> provider) {
            if (this.providers.containsKey(provider.plugin())) {
                throw new LevelerBridgeException("Leveler provider '" + provider.plugin() + "' already registered");
            }
            this.providers.put(provider.plugin(), provider);
            return this;
        }

        @Override
        public @Nullable LevelerProvider<Player> removeById(String id) {
            return this.providers.remove(id);
        }

        @Override
        public BukkitLevelerBridge build() {
            return new BukkitLevelerBridgeImpl(this.providers);
        }
    }
}
