package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerBridgeException;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import cn.gtemc.levelerbridge.api.util.ThrowableRunnable;
import cn.gtemc.levelerbridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class HookHelper {
    static BiFunction<Consumer<String>, BiConsumer<String, Throwable>, Map<String, LevelerProvider<Player>>> j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.levelerbridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new LevelerBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = (s, f) -> Map.of();
        }
    }

    private HookHelper() {}

    static void tryHook(ThrowableRunnable runnable, String plugin, Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure) {
        if (Bukkit.getPluginManager().getPlugin(plugin) == null) {
            return;
        }
        try {
            runnable.run();
            if (onSuccess != null) {
                onSuccess.accept(plugin);
            }
        } catch (Throwable e) {
            if (onFailure != null) {
                onFailure.accept(plugin, e);
            }
        }
    }

    public static Map<String, LevelerProvider<Player>> getSupportedPlugins(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure) {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>(j21ProvidersGetter.apply(onSuccess, onFailure));
        MiscUtils.addToMap(MinecraftLevelerProvider.INSTANCE, providers);
        tryHook(() -> MiscUtils.addToMap(AuraSkillsLevelerProvider.INSTANCE, providers), "AuraSkills", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(AureliumSkillsLevelerProvider.INSTANCE, providers), "AureliumSkills", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(JobsRebornLevelerProvider.INSTANCE, providers), "Jobs", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(McMMOLevelerProvider.INSTANCE, providers), "McMMO", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(MMOCoreLevelerProvider.INSTANCE, providers), "MMOCore", onSuccess, onFailure);
        return providers;
    }
}
