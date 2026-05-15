# LevelerBridge

## Supported plugins

- [AuraSkills](https://github.com/Archy-X/AuraSkills)
- [AureliumSkills](https://github.com/Archy-X/AuraSkills/tree/Beta1.3.24)
- [EcoJobs](https://github.com/Auxilor/EcoJobs)
- [EcoSkills](https://github.com/Auxilor/EcoSkills)
- [Jobs](https://github.com/Zrips/Jobs)
- [McMMO](https://github.com/mcMMO-Dev/mcMMO)
- [MMOCore](https://gitlab.com/phoenix-dvpmt/mmocore)

## How to use

### Add dependency to the project

```kts
repositories {
    maven("https://repo.gtemc.net/releases/")
}
```
```kts
dependencies {
    implementation("cn.gtemc:levelerbridge:1.0.9")
}
```

### Example code

```java
BukkitLevelerBridge levelerBridge = BukkitLevelerBridge.builder()
        .detectSupportedPlugins(
                p -> System.out.println("Hooked " + p),
                (p, e) -> System.err.println("Failed to hook " + p + ", because " + e.getMessage()),
                null
        )
        .build();

int level = levelerBridge.getLevel("pluginname", player, "levelname");
```
