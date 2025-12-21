package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerBridgeException;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import cn.gtemc.levelerbridge.api.util.ThrowableRunnable;
import cn.gtemc.levelerbridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class HookHelper {
    private static final Logger log = LoggerFactory.getLogger(HookHelper.class);
    static Function<Boolean, Map<String, LevelerProvider<Player>>> j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.levelerbridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new LevelerBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = l -> Map.of();
        }
    }

    private HookHelper() {}

    static void tryHook(ThrowableRunnable runnable, String plugin, boolean loggingEnabled) {
        if (Bukkit.getPluginManager().getPlugin(plugin) == null) {
            return;
        }
        try {
            runnable.run();
            if (loggingEnabled) {
                log.info("[LevelerBridge] {} hooked", plugin);
            }
        } catch (Throwable e) {
            if (loggingEnabled) {
                log.warn("[LevelerBridge] Failed to hook {}", plugin, e);
            }
        }
    }

    public static Map<String, LevelerProvider<Player>> getSupportedPlugins(boolean loggingEnabled) {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>(j21ProvidersGetter.apply(loggingEnabled));
        MiscUtils.addToMap(MinecraftLevelerProvider.INSTANCE, providers);
        tryHook(() -> MiscUtils.addToMap(AuraSkillsLevelerProvider.INSTANCE, providers), "AuraSkills", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(AureliumSkillsLevelerProvider.INSTANCE, providers), "AureliumSkills", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(JobsRebornLevelerProvider.INSTANCE, providers), "Jobs", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(McMMOLevelerProvider.INSTANCE, providers), "McMMO", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(MMOCoreLevelerProvider.INSTANCE, providers), "MMOCore", loggingEnabled);
        return providers;
    }
}
