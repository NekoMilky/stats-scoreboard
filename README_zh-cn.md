![](versions/common/src/main/resources/assets/icon.png)

统计计分板
===
这是一个简单的模组，可在<strong>单人游戏和服务器中</strong>于侧边栏显示所有玩家的统计数据！

<strong>1000+准则！</strong> 每个准则都唯一对应一个侧边栏计分板，用于显示玩家相应的统计数据。

玩家可以选择自己偏好的准则，并在侧边栏上<strong>交替显示</strong>它们。

其他语言：
- [English](README.md)

依赖
---
- Minecraft 1.21 ~ 1.21.11 / 26.1 ~ 26.1.2
- [Fabric Loader 0.17.0+](https://github.com/FabricMC/fabric-loader)
- [Fabric API](https://github.com/FabricMC/fabric-api)

准则
---
#### 方块相关
- `block.mined.all` - 挖掘方块数。
- `block.mined.<namespace>.<block>` - 挖掘指定方块数。
- `block.placed.all` - 放置方块数。
- `block.placed.<namespace>.<block>` - 放置指定方块数。
#### 自定义
- `custom.<namespace>.<stat>` - 自定义统计数据。
#### 实体相关
- `entity.killed.all` - 杀死实体数。
- `entity.killed.<namespace>.<entity>` - 杀死指定实体数。
- `entity.killed_by.all` - 被实体杀死次数。
- `entity.killed_by.<namespace>.<entity>` - 被指定实体杀死次数。
#### 物品相关
- `item.broken.all` - 损坏物品数。
- `item.broken.<namespace>.<item>` - 损坏指定物品数。
- `item.crafted.all` - 合成物品数。
- `item.crafted.<namespace>.<item>` - 合成指定物品数。
- `item.dropped.all` - 丢弃物品数。
- `item.dropped.<namespace>.<item>` - 丢弃指定物品数。
- `item.picked_up.all` - 捡起物品数。
- `item.picked_up.<namespace>.<item>` - 捡起指定物品数。
- `item.used.all` - 使用物品次数。
- `item.used.<namespace>.<item>` - 使用指定物品次数。

命令
---
#### 普通
- `/statsscoreboard`与`/ssb` - 主命令，展示信息。
- `/statsscoreboard about` - 展示信息。
- `/statsscoreboard sidebar` - 显示你已选择的准则。
    - `/statsscoreboard sidebar add <criterion>` - 选择一个新准则。
    - `/statsscoreboard sidebar remove <criterion>` - 移除一个已选择的准则。
- `/statsscoreboard sidebarRotationInterval` - 显示你的侧边栏轮换的时间间隔。
    - `/statsscoreboard sidebarRotationInterval <interval>` - 修改此间隔。
#### 仅OP
- `/statsscoreboard configAutoSaveInterval` - 显示配置文件自动保存的时间间隔。
    - `/statsscoreboard configAutoSaveInterval <interval>` - 修改此间隔。
- `/statsscoreboard defaultSidebar` - 显示新玩家的默认选择准则。
    -  `/statsscoreboard defaultSidebar <criterion>` - 修改此准则。
- `/statsscoreboard refreshAllStatsDataInterval` - 显示统计数据全量更新的时间间隔。
    - `/statsscoreboard refreshAllStatsDataInterval <interval>` - 修改此间隔。
- `/statsscoreboard sidebarDisplayUUID` - 显示侧边栏是否使用UUID代替玩家名。这通常发生于在安装此mod前就已经加入过服务器的玩家身上，直到他们重新登录服务器。
    - `/statsscoreboard sidebarDisplayUUID <enable>` - 启用/禁用。

FAQ
---
<strong>Q1. 侧边栏会显示安装此mod前的统计数据吗？</strong>

A1. 会。这个mod会读取统计文件数据。

<strong>Q2. 为什么有些玩家显示的是UUID而不是名称？</strong>

A2. 大概率是因为他们在服务器安装此mod前就加入过服务器，并且之后再无登录。此mod将在他们再次登录时自动地同步玩家名称。

<strong>Q3. 侧边栏是对每个玩家都独立显示的吗？</strong>

A3. 对。不同玩家的侧边栏数据是严格隔离的。

<strong>Q4. 侧边栏可以显示多个准则吗？</strong>

A4. 可以。它们会交替显示到侧边栏。

<strong>Q5. 侧边栏可以隐藏吗？</strong>

A5. 可以。只需要把你所有已选择的准则都移除。

问题&反馈
---
如果你遇到Bug或有功能建议，欢迎通过[GitHub Issues](https://github.com/NekoMilky/stats-scoreboard/issues)提交。

构建
---
```bash
git clone https://github.com/NekoMilky/stats-scoreboard.git
cd stats-scoreboard
./gradlew build
```
构建产物能在目录`stats-scoreboard/versions/{version}/build/lib/`中找到。

许可
---
此项目使用[MIT 许可证](LICENSE)。

致谢
---
此项目使用了以下开源项目：
- [@Patbox/SidebarAPI - Simple API for Fabric server side sidebars](https://github.com/Patbox/SidebarAPI) - LGPL 3.0 许可证
- [@NucleoidMC/Server-Translations - A library for handling translations server side](https://github.com/NucleoidMC/Server-Translations) - MIT 许可证
- [@FabricMC/fabric-api - Essential hooks for modding with Fabric](https://github.com/FabricMC/fabric-api) - Apache 2.0 许可证
- [@FabricMC/fabric-loader - Fabric's mostly-version-independent mod loader](https://github.com/FabricMC/fabric-loader) - Apache 2.0 许可证
- [@FabricMC/fabric-loom - Gradle build system plugin used to automate the setup of a minecraft mod development environment](https://github.com/FabricMC/fabric-loom) - MIT 许可证
- [@FabricMC/yarn - Libre Minecraft mappings, free to use for everyone. No exceptions](https://github.com/fabricMC/yarn) - CC0 1.0 许可证
- [@google/gson - A Java serialization/deserialization library to convert Java Objects into JSON and back](https://github.com/google/gson) - Apache 2.0 许可证
- [@qos-ch/slf4j - Simple Logging Facade for Java](https://github.com/qos-ch/slf4j) - MIT 许可证
