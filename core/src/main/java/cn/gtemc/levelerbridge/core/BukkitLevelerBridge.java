package cn.gtemc.levelerbridge.core;

import cn.gtemc.levelerbridge.api.LevelerBridge;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface BukkitLevelerBridge extends LevelerBridge<Player> {

    static BukkitBuilder builder() {
        return new BukkitLevelerBridgeImpl.BukkitBuilderImpl();
    }

    interface BukkitBuilder extends Builder<Player> {

        BukkitBuilder register(LevelerProvider<Player> provider);

        @Nullable LevelerProvider<Player> removeById(String id);

        BukkitLevelerBridge build();
    }

}
