package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class HookHelper {
    private HookHelper() {}

    public static Map<String, LevelerProvider<Player>> getSupportedPlugins(
            @Nullable Consumer<String> onSuccess,
            @Nullable BiConsumer<String, Throwable> onFailure,
            @Nullable Predicate<Plugin> filter
    ) {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>();
        Utils.addToMap(MinecraftLevelerProvider.INSTANCE, providers);
        tryHook(() -> Utils.addToMap(EcoSkillsLevelerProvider.INSTANCE, providers), "EcoSkills", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(EcoJobsLevelerProvider.INSTANCE, providers), "EcoJobs", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(AuraSkillsLevelerProvider.INSTANCE, providers), "AuraSkills", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(AureliumSkillsLevelerProvider.INSTANCE, providers), "AureliumSkills", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(JobsRebornLevelerProvider.INSTANCE, providers), "Jobs", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(McMMOLevelerProvider.INSTANCE, providers), "mcMMO", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MMOCoreLevelerProvider.INSTANCE, providers), "MMOCore", onSuccess, onFailure, filter);
        return providers;
    }

    private static void tryHook(
            ThrowableRunnable runnable,
            String pluginName,
            @Nullable Consumer<String> onSuccess,
            @Nullable BiConsumer<String, Throwable> onFailure,
            @Nullable Predicate<Plugin> filter
    ) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null || (filter != null && !filter.test(plugin))) {
            return;
        }
        try {
            runnable.run();
            if (onSuccess != null) {
                onSuccess.accept(pluginName);
            }
        } catch (Throwable e) {
            if (onFailure != null) {
                onFailure.accept(pluginName, e);
            }
        }
    }

    @FunctionalInterface
    private interface ThrowableRunnable {

        void run() throws Throwable;
    }
}
