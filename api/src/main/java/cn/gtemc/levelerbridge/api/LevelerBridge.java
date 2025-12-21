package cn.gtemc.levelerbridge.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

/**
 * LevelerBridge is a unified leveling bridging interface used to manage player levels and experience across different plugins.
 * <p>
 * It supports leveling operations for various plugins by registering {@link LevelerProvider}.
 *
 * @param <P> The type of the player object.
 */
public interface LevelerBridge<P> {

    /**
     * Retrieves the corresponding {@link LevelerProvider} for the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @return An {@code Optional} containing the corresponding {@link LevelerProvider}, or {@code Optional.empty()} if it does not exist.
     */
    Optional<LevelerProvider<P>> provider(@NotNull String plugin);

    /**
     * Retrieves a collection of all registered {@link LevelerProvider}.
     *
     * @return An unmodifiable collection of all registered {@link LevelerProvider}.
     */
    Collection<LevelerProvider<P>> providers();

    /**
     * Gets the level of a player from the specified plugin and target.
     *
     * @param plugin The lower-case name of the plugin.
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @return The level value, or 0 if the provider does not exist.
     */
    int getLevel(@NotNull String plugin, @NotNull P player, String target);

    /**
     * Gets the experience of a player from the specified plugin and target.
     *
     * @param plugin The lower-case name of the plugin.
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @return The experience value, or 0.0 if the provider does not exist.
     */
    double getExperience(@NotNull String plugin, @NotNull P player, String target);

    /**
     * Adds levels to a player via the specified plugin provider.
     *
     * @param plugin The lower-case name of the plugin.
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @param level  The amount of levels to add.
     */
    void addLevel(@NotNull String plugin, @NotNull P player, String target, int level);

    /**
     * Adds experience to a player via the specified plugin provider.
     *
     * @param plugin     The lower-case name of the plugin.
     * @param player     The player object {@link P}.
     * @param target     The target identifier.
     * @param experience The amount of experience to add.
     */
    void addExperience(@NotNull String plugin, @NotNull P player, String target, double experience);

    /**
     * Sets the level of a player via the specified plugin provider.
     *
     * @param plugin The lower-case name of the plugin.
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @param level  The new level value.
     */
    void setLevel(@NotNull String plugin, @NotNull P player, String target, int level);

    /**
     * Sets the experience of a player via the specified plugin provider.
     *
     * @param plugin     The lower-case name of the plugin.
     * @param player     The player object {@link P}.
     * @param target     The target identifier.
     * @param experience The new experience value.
     */
    void setExperience(@NotNull String plugin, @NotNull P player, String target, double experience);

    /**
     * Interface for building and configuring {@link LevelerBridge} instances.
     * <p>
     * All available built-in leveler providers are automatically loaded upon creation.
     * Custom providers can be registered or existing ones removed through this builder.
     *
     * @param <P> The type of the player object.
     */
    interface Builder<P> {

        /**
         * Registers a {@link LevelerProvider} into the LevelerBridge.
         *
         * @param provider The leveler provider to register.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<P> register(LevelerProvider<P> provider);

        /**
         * Removes a registered provider based on the plugin name.
         *
         * @param id The lower-case name of the plugin.
         * @return The removed provider, or null if it did not exist.
         */
        @Nullable LevelerProvider<P> removeById(String id);

        /**
         * Builds and returns an immutable {@link LevelerBridge} instance.
         *
         * @return The completed {@link LevelerBridge} instance.
         */
        LevelerBridge<P> build();
    }
}
