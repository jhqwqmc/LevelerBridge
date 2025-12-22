package cn.gtemc.levelerbridge.core;

import cn.gtemc.levelerbridge.api.LevelerBridge;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * BukkitLevelerBridge is a unified leveling bridging interface for the Bukkit platform.
 * <p>
 * It provides access to various leveling systems using {@link Player} as the player type.
 */
public interface BukkitLevelerBridge extends LevelerBridge<Player> {

    /**
     * Retrieves a {@code BukkitBuilder} used to construct and configure an {@code BukkitLevelerBridge} instance.
     *
     * @return An {@code BukkitLevelerBridge} {@code BukkitBuilder} instance.
     */
    static BukkitBuilder builder() {
        return new BukkitLevelerBridgeImpl.BukkitBuilderImpl();
    }

    /**
     * Registers a {@link LevelerProvider} into the LevelerBridge.
     *
     * @param provider The leveler provider to register.
     * @return The current instance, supporting method chaining.
     */
    BukkitLevelerBridge register(LevelerProvider<Player> provider);

    /**
     * Removes a registered provider based on the plugin name.
     *
     * @param id The lower-case name of the plugin.
     * @return The current instance, supporting method chaining.
     */
    BukkitLevelerBridge removeById(String id);

    /**
     * Interface for building and configuring {@link BukkitLevelerBridge} instances.
     * <p>
     * All available built-in leveler providers for Bukkit are automatically loaded upon creation.
     * Custom providers can be registered or existing ones removed through this builder.
     */
    interface BukkitBuilder extends Builder<Player> {

        /**
         * Registers a {@link LevelerProvider} into the LevelerBridge.
         *
         * @param provider The leveler provider to register.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder register(LevelerProvider<Player> provider);

        /**
         * Removes a registered provider based on the plugin name.
         *
         * @param id The lower-case name of the plugin.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder removeById(String id);

        /**
         * Sets whether the LevelerBridge is immutable.
         *
         * @param immutable Whether the LevelerBridge is immutable.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder immutable(boolean immutable);

        /**
         * Sets the action to perform when a plugin is successfully hooked.
         *
         * @param onSuccess onSuccess a consumer receiving the name of the hooked plugin.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder onHookSuccess(Consumer<String> onSuccess);

        /**
         * Sets the action to perform when a hook attempt fails.
         *
         * @param onFailure onFailure a bi-consumer receiving the plugin name and the error cause.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder onHookFailure(BiConsumer<String, Throwable> onFailure);

        /**
         * Detects and registers all supported plugins.
         *
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder detectSupportedPlugins();

        /**
         * Detects and registers all supported plugins.
         *
         * @param filter The predicate to filter plugins.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder detectSupportedPlugins(@NotNull Predicate<Plugin> filter);

        /**
         * Builds and returns an immutable {@link BukkitLevelerBridge} instance.
         *
         * @return The completed {@link BukkitLevelerBridge} instance.
         */
        BukkitLevelerBridge build();
    }
}
