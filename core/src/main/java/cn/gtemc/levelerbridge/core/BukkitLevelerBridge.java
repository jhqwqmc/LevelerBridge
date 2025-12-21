package cn.gtemc.levelerbridge.core;

import cn.gtemc.levelerbridge.api.LevelerBridge;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

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
        return new BukkitLevelerBridgeImpl.BukkitBuilderImpl(true);
    }

    /**
     * Retrieves a {@code BukkitBuilder} used to construct and configure an {@code BukkitLevelerBridge} instance.
     *
     * @param loggingEnabled Whether to enable log printing.
     * @return An {@code BukkitLevelerBridge} {@code BukkitBuilder} instance.
     */
    static BukkitBuilder builder(boolean loggingEnabled) {
        return new BukkitLevelerBridgeImpl.BukkitBuilderImpl(loggingEnabled);
    }

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
         * @return The removed provider, or null if it did not exist.
         */
        @Nullable LevelerProvider<Player> removeById(String id);

        /**
         * Builds and returns an immutable {@link BukkitLevelerBridge} instance.
         *
         * @return The completed {@link BukkitLevelerBridge} instance.
         */
        BukkitLevelerBridge build();
    }
}
