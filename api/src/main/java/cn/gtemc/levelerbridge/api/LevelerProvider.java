package cn.gtemc.levelerbridge.api;

import org.jetbrains.annotations.NotNull;

/**
 * Leveler Provider Interface.
 * <p>
 * Defines how to retrieve and modify player levels and experience from specific plugins.
 * Each implementation class typically corresponds to a specific external leveling plugin.
 *
 * @param <P> The type of the player object.
 */
public interface LevelerProvider<P> {

    /**
     * Retrieves the lower-case name of the plugin corresponding to this provider.
     *
     * @return The lower-case plugin name.
     */
    @NotNull String plugin();

    /**
     * Gets the current level of a player for a specific target.
     *
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @return The current level value.
     */
    int getLevel(@NotNull P player, String target);

    /**
     * Gets the current experience of a player for a specific target.
     *
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @return The current experience value.
     */
    double getExperience(@NotNull P player, String target);

    /**
     * Adds a specified amount of levels to a player for a target.
     *
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @param level  The amount of levels to add.
     */
    void addLevel(@NotNull P player, String target, int level);

    /**
     * Adds a specified amount of experience to a player for a target.
     *
     * @param player     The player object {@link P}.
     * @param target     The target identifier.
     * @param experience The amount of experience to add.
     */
    void addExperience(@NotNull P player, String target, double experience);

    /**
     * Sets the level of a player for a specific target to a given value.
     *
     * @param player The player object {@link P}.
     * @param target The target identifier.
     * @param level  The new level value.
     */
    void setLevel(@NotNull P player, String target, int level);

    /**
     * Sets the experience of a player for a specific target to a given value.
     *
     * @param player     The player object {@link P}.
     * @param target     The target identifier.
     * @param experience The new experience value.
     */
    void setExperience(@NotNull P player, String target, double experience);
}
