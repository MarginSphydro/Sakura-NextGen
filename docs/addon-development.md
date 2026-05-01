# Addon Development Guide

本文档介绍如何为 Sakura Client 开发 Addon，并同时兼容 Fabric 与 NeoForge。

## 1. 基础 API

使用 `common` 中的统一 Addon 基类：

- `dev.sakura.client.addon.SakuraAddon`

按加载器使用对应的注册入口参数：

- Fabric: `dev.sakura.client.addon.SakuraAddonSetupEvent`
- NeoForge: `dev.sakura.client.neoforge.addon.SakuraAddonSetupEvent`

## 2. 编写 Addon 类

最小 Addon 示例：

```java
package your.mod.addon;

import dev.sakura.client.addon.SakuraAddon;

public class ExampleAddon extends SakuraAddon {

    private final BoolSetting enableParticles = boolSetting("Enable Particles", true);

    public ExampleAddon() {
        super("example_addon");
    }

    @Override
    public String getDisplayName() {
        return "Example Addon";
    }

    @Override
    public String getDescription() {
        return "An example addon that demonstrates addon metadata and addon settings.";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public void onSetup() {
        // 在这里注册模块
        // registerModule(new YourModule());
    }
}
```

### Addon 元信息与 Addon Setting

- `getDisplayName()`：用于在客户端 `Client Settings -> Addons` 中显示名称。
- `getDescription()`：用于显示 Addon 的简介。
- `getVersion()` / `getAuthors()`：用于显示基础信息。
- `boolSetting(...)` / `intSetting(...)` / `enumSetting(...)` 等：用于声明 **Addon 自己的设置**，这些设置会显示在 `Client Settings -> Addons` 中，并随配置一起保存。

Addon setting 的翻译 key 约定为：

- `{addonId}.settings.{settingNameLowerCase}`

Addon 模块翻译 key 仍然为：

- `{addonId}.modules.{moduleNameLowerCase}`

## 3. Fabric 接入 (自定义 Entrypoint)

### 3.1 实现 Entrypoint 接口

```java
package your.mod.fabric;

import dev.sakura.client.addon.SakuraAddonSetupEvent;
import dev.sakura.client.fabric.addon.FabricSakuraAddonEntrypoint;
import your.mod.addon.ExampleAddon;

public class ExampleFabricAddonEntrypoint implements FabricSakuraAddonEntrypoint {

    @Override
    public void registerAddon(SakuraAddonSetupEvent event) {
        event.registerAddon(new ExampleAddon());
    }
}
```

### 3.2 在 `fabric.mod.json` 注册自定义 entrypoint

```json
{
  "entrypoints": {
    "open_epsilon:addon": [
      "your.mod.fabric.ExampleFabricAddonEntrypoint"
    ]
  }
}
```

Sakura Fabric 会在客户端初始化时自动读取 `sakura:addon` 并注册 addon。

## 4. NeoForge 接入 (Event Bus)

NeoForge 使用 `NeoForge.EVENT_BUS` 注册 addon：

```java
package your.mod.neoforge;

import dev.sakura.client.neoforge.addon.SakuraAddonSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import your.mod.addon.ExampleAddon;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class ExampleNeoHook {

    @SubscribeEvent
    public static void onAddonSetup(SakuraAddonSetupEvent event) {
        event.registerAddon(new ExampleAddon());
    }
}
```

## 5. 异常隔离行为

Sakura 已实现两层隔离：

1. **Fabric entrypoint 隔离**：单个 entrypoint 抛异常时，只会记录错误日志，不会阻断其他 addon 的注册。
2. **Addon setup 隔离**：单个 addon 的 `onSetup()` 失败时，只会记录错误日志，不会阻断其他 addon 的加载。

建议在 addon 内部继续做好自身异常处理，避免注册到一半时产生不可预期状态。

## 6. 调试建议

- 检查日志关键词：
  - `Loaded Sakura addon:`
  - `Failed to register addon entrypoint from mod:`
  - `Failed to setup Sakura addon:`
- 首次接入时先做一个最小 addon（仅日志输出），确认生命周期后再逐步注册模块。
