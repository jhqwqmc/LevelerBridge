package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerBridgeException;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import cn.gtemc.levelerbridge.api.util.ThrowableRunnable;
import cn.gtemc.levelerbridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class HookHelper {
    static J21ProvidersGetter j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.levelerbridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new LevelerBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = (s, f, p) -> Map.of();
        }
    }

    private HookHelper() {}

    static void tryHook(
            ThrowableRunnable runnable,
            String pluginName,
            Consumer<String> onSuccess,
            BiConsumer<String, Throwable> onFailure,
            Predicate<Plugin> filter
    ) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null || !filter.test(plugin)) {
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

    public static Map<String, LevelerProvider<Player>> getSupportedPlugins(
            Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> filter
    ) {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>(j21ProvidersGetter.get(onSuccess, onFailure, filter));
        MiscUtils.addToMap(MinecraftLevelerProvider.INSTANCE, providers);
        tryHook(() -> MiscUtils.addToMap(AuraSkillsLevelerProvider.INSTANCE, providers), "AuraSkills", onSuccess, onFailure, filter);
        tryHook(() -> MiscUtils.addToMap(AureliumSkillsLevelerProvider.INSTANCE, providers), "AureliumSkills", onSuccess, onFailure, filter);
        tryHook(() -> MiscUtils.addToMap(JobsRebornLevelerProvider.INSTANCE, providers), "Jobs", onSuccess, onFailure, filter);
        tryHook(() -> MiscUtils.addToMap(McMMOLevelerProvider.INSTANCE, providers), "McMMO", onSuccess, onFailure, filter);
        tryHook(() -> MiscUtils.addToMap(MMOCoreLevelerProvider.INSTANCE, providers), "MMOCore", onSuccess, onFailure, filter);
        return providers;
    }

    @FunctionalInterface
    interface J21ProvidersGetter {
        Map<String, LevelerProvider<Player>> get(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> filter);
    }
}
