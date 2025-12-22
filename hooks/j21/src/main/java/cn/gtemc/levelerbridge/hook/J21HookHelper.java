package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import cn.gtemc.levelerbridge.hook.provider.EcoJobsLevelerProvider;
import cn.gtemc.levelerbridge.hook.provider.EcoSkillsLevelerProvider;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static cn.gtemc.levelerbridge.hook.HookHelper.tryHook;

@SuppressWarnings("unused")
public final class J21HookHelper {

    static {
        HookHelper.j21ProvidersGetter = J21HookHelper::getSupportedPlugins;
    }

    private J21HookHelper() {}

    private static Map<String, LevelerProvider<Player>> getSupportedPlugins(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure) {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>();
        tryHook(() -> MiscUtils.addToMap(EcoSkillsLevelerProvider.INSTANCE, providers), "EcoSkills", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(EcoJobsLevelerProvider.INSTANCE, providers), "EcoJobs", onSuccess, onFailure);
        return providers;
    }
}
