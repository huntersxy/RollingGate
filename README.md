# 卷帘门 | Rolling Gate

> 一个NeoForge模组，控制Minecraft技术选项
>
> A NeoForge mod that controls Minecraft technology options

[![Development Builds](https://github.com/Anvil-Dev/RollingGate/actions/workflows/ci.yml/badge.svg)](https://github.com/Anvil-Dev/RollingGate/actions/workflows/ci.yml)
[![GitHub downloads](https://img.shields.io/github/downloads/Anvil-Dev/RollingGate/total?label=Github%20downloads&logo=github)](https://github.com/Anvil-Dev/RollingGate/releases)

## 声明 | Disclaimer

> 本模组仅支持 Minecraft 1.21+ ，暂无向更低版本兼容的计划。
>
> This mod only supports Minecraft 1.21+ and has no plans to be compatible with lower versions at the moment.
>
> 本模组代码基于 [LGPL-3.0](./LICENSE) 协议开源，其它资产基于 [CC-BY-NC-ND 4.0](./LICENSE_ASSETS) 发布，请遵守协议。
>
> This module code is open source based on the [LGPL-3.0](./LICENSE) protocol, while other assets are released based on
> the [CC-BY-NC-ND 4.0](./LICENSE_ASSETS) protocol. Please comply with the protocol.

## 快速开始 | Quick Start

1. 下载模组，放置到 `.minecraft/mods` 或 `.minecraft/versions/{version}/mods` 文件夹
2. 进入存档，使用 `/rg` 命令显示模组控制面板以调整规则的启用状态

## 配置 | Configuration

* 在 `configs/rolling-gate.json` 中修改默认配置，该配置将影响所有存档
* 在存档目录中创建 `rolling-gate.json` 文件以覆盖默认配置
* 配置文件格式为 JSON，示例如下：

```json5
{
  // string
  "rule1": "value",
  // int
  "rule2": 1,
  // double
  "rule3": 1.0,
  // boolean
  "rule4": true
}
```

## 命令 | Command

* `/rg` - 显示模组控制面板
    * `reload` - 重新加载配置文件
    * `category [category]` - 查看分组下的所有规则
    * `[rule] [value]` - 设置规则的值
    * `[rule]` - 查看规则详细介绍
    * `default [rule] [value]` - 设置当前存档的规则默认值

* `/rg` - Display mod control panel
    * `reload` - Reload configuration file
    * `category [category]` - View all rules under the group
    * `[rule] [value]` - Set the value of the rule
    * `[rule]` - View detailed information of rules
    * `default [rule] [value]` - Set default rules for the current save

## 规则 | Rules

| 规则         | 类型     | 默认值     | 允许值              | 描述         |
|------------|--------|---------|------------------|------------|
| `language` | String | `zh_cn` | `en_us`, `zh_cn` | 设置卷帘门的默认语言 |

| Rule       | Type   | Default Value | Allowed Value    | Description                              |
|------------|--------|---------------|------------------|------------------------------------------|
| `language` | String | `zh_cn`       | `en_us`, `zh_cn` | Set the default language for RollingGate |

## 贡献 | Contribution

* Fork 仓库 | Fork Repository
    * [![Fork Repository](https://img.shields.io/badge/Fork%20Repository-blue?logo=github)](https://github.com/Anvil-Dev/RollingGate/fork)

* 提交 PR | Submit PR
    * [![Submit PR](https://img.shields.io/badge/Submit%20PR-green?logo=github)](https://github.com/Anvil-Dev/RollingGate/pulls)

## 贡献者 | Contributors

<!--suppress ALL -->
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Gu-ZT">
        <img src="https://avatars.githubusercontent.com/u/34372427?v=100&s=100" width="100px" height="100px" alt=""/><br />
        <sub><b>Gugle</b></sub>
      </a><br />
      <a title="Code">💻</a><br />
      <a href="https://space.bilibili.com/19822751">19822751</a>
    </td>
    <td align="center">
      <a href="https://github.com/Cjsah">
        <img src="https://avatars.githubusercontent.com/u/46415647?v=100&s=100" width="100px" height="100px" alt=""/><br />
        <sub><b>꧁[C̲̅j̲̅s̲̅a̲̅h̲̅]꧂</b></sub>
      </a><br />
      <a title="Code">💻</a><br />
      <a href="https://space.bilibili.com/19170004">19170004</a>
    </td>
  </tr>
</table>

## 联系我们 | Contact Us

* [![Contact Us](https://img.shields.io/badge/Contact%20Us-white?logo=tencentqq)](https://qm.qq.com/q/alOmGR4G6k)

## 赞助我们 | Sponsor Us

* [![Sponsor Us](https://img.shields.io/badge/Sponsor%20Us-blue?logo=githubsponsors)](https://www.anvilcraft.dev/#/support)

## 赞助者 | Sponsors

## 编写附属 | Write Additional

* 编写一个类添加规则
* Write a class addition rule

```java

@RGServerRules(value = "your_mod_id", languages = {"zh_cn", "en_us"})
public class YourModServerRules {
    @Rule(allowed = {"your_rule_value1", "your_rule_value2"}, categories = "your_rule_category")
    public static String yourRule = "your_rule_value1";
}
```
