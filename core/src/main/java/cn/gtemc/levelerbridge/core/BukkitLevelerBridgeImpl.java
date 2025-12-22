package cn.gtemc.levelerbridge.core;

import cn.gtemc.levelerbridge.api.LevelerBridgeException;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.hook.HookHelper;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class BukkitLevelerBridgeImpl implements BukkitLevelerBridge {
    private final Map<String, LevelerProvider<Player>> providers;
    private final boolean immutable;

    private BukkitLevelerBridgeImpl(Map<String, LevelerProvider<Player>> providers, boolean immutable) {
        this.providers = immutable ? Collections.unmodifiableMap(providers) : providers;
        this.immutable = immutable;
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

    @Override
    public boolean immutable() {
        return this.immutable;
    }

    @Override
    public BukkitLevelerBridge register(LevelerProvider<Player> provider) {
        if (this.providers.containsKey(provider.plugin())) {
            throw new LevelerBridgeException("Leveler provider '" + provider.plugin() + "' already registered");
        }
        this.providers.put(provider.plugin(), provider);
        return this;
    }

    @Override
    public BukkitLevelerBridge removeById(String id) {
        this.providers.remove(id);
        return this;
    }

    final static class BukkitBuilderImpl implements BukkitBuilder {
        private final Map<String, LevelerProvider<Player>> providers;
        private Consumer<String> onHookSuccess;
        private BiConsumer<String, Throwable> onHookFailure;
        private boolean immutable;

        BukkitBuilderImpl() {
            this.providers = new HashMap<>();
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
        public BukkitBuilder removeById(String id) {
            this.providers.remove(id);
            return this;
        }

        @Override
        public BukkitBuilder immutable(boolean immutable) {
            this.immutable = immutable;
            return this;
        }

        @Override
        public BukkitBuilder onHookSuccess(Consumer<String> onSuccess) {
            this.onHookSuccess = onSuccess;
            return this;
        }

        @Override
        public BukkitBuilder onHookFailure(BiConsumer<String, Throwable> onFailure) {
            this.onHookFailure = onFailure;
            return this;
        }

        @Override
        public BukkitBuilder detectSupportedPlugins() {
            return detectSupportedPlugins(plugin -> true);
        }

        @Override
        public BukkitBuilder detectSupportedPlugins(@NotNull Predicate<Plugin> filter) {
            this.providers.putAll(HookHelper.getSupportedPlugins(this.onHookSuccess, this.onHookFailure, filter));
            return this;
        }

        @Override
        public BukkitLevelerBridge build() {
            return new BukkitLevelerBridgeImpl(this.providers, this.immutable);
        }
    }
}
