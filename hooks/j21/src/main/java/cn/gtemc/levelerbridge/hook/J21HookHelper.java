package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import cn.gtemc.levelerbridge.hook.provider.EcoJobsLevelerProvider;
import cn.gtemc.levelerbridge.hook.provider.EcoSkillsLevelerProvider;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static cn.gtemc.levelerbridge.hook.HookHelper.hasPlugin;

@SuppressWarnings("unused")
public final class J21HookHelper {

    static {
        HookHelper.j21ProvidersGetter = J21HookHelper::getSupportedPlugins;
    }

    private J21HookHelper() {}

    private static Map<String, LevelerProvider<Player>> getSupportedPlugins() {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>();
        if (hasPlugin("EcoSkills")) {
            MiscUtils.addToMap(EcoSkillsLevelerProvider.INSTANCE, providers);
        }
        if (hasPlugin("EcoJobs")) {
            MiscUtils.addToMap(EcoJobsLevelerProvider.INSTANCE, providers);
        }
        return providers;
    }
}
