# PonziScheme

这是一个Bukkit插件, 目的是宣传服务器以及推广服务器论坛, 目前已实现的功能有:

1. Flarum论坛活跃奖励
2. B站视频三连奖励

## 特点

* 自定义任务内容和奖励
* 支持MySQL和SQLite数据库

## 快速开始

1. 将插件`.jar`文件复制到服务器的`plugins`文件夹中
2. 进入插件文件夹, 编辑`tasks.yml`文件, 自定义任务
3. 重载配置文件, 如果使用权限系统, 请赋予玩家必要的权限

## 任务配置

完整的任务配置请参考默认配置的`tasks.yml`文件. 此节将说明各项配置的含义.

基本配置如下, 无论任务类型都需要配置以下内容:

```yaml
example:
  name: '§r§f示例任务'
  type: '{任务类型}'
  icon:
    ==: org.bukkit.inventory.ItemStack
    v: 3839
    type: NETHER_STAR
  reward:
    commands:
      - 'experience add %PLAYER% 1000'
    items:
      '1':
        ==: org.bukkit.inventory.ItemStack
        v: 3839
        type: ENCHANTED_GOLDEN_APPLE
```

* `example`是任务id, 用于识别任务, 任务id不能重复, 玩家完成任务后数据库中会存储这个id, 一旦上线后不应该再修改.
* `name`是任务显示名称, 支持颜色代码.
* `type`是任务类型, 目前支持`flarum_post_activate`和`bilibili_video_sanlian`
* `icon`是任务图标, 为物品序列化后的yaml文本, 可通过`/ps reader`指令打开物品读取器来读取物品的yaml.
* `reward`是任务奖励, 包含`commands`和`items`两种类型:
  * `commands`是命令列表, 玩家完成任务后会执行这些命令, `%PLAYER%`会被自动替换为玩家名称.
  * `items`是物品列表, 同icon一样, 也是物品序列化后的yaml文本.

### Flarum任务配置

论坛活跃任务, 类型为`flarum_post_activate`, 还必需要配置以下内容:

```yaml
example:
  condition:
    repeat: 'days'
    count: '1'
```

* `condition`是任务条件, 包含`repeat`和`count`两个子健:
  * `repeat`是任务的重复周期, 目前支持`days`和`weeks`, 代表每周活跃或每日活跃
  * `count`是指达成任务条件所需要的活跃次数, 例如`repeat: 'days'`和`count: '1'`代表每日活跃一次

### B站视频三连任务配置

B站视频三连任务, 类型为`bilibili_video_sanlian`, 还必需要配置以下内容:

```yaml
example:
  bvid: '{视频BV号}'
  timeLimit: '5m'
  condition:
    - 'like'
    - 'coin'
    - 'favor'
```

* `bvid`是视频BV号, 可以在B站视频页面的URL中找到, 例如 `https://www.bilibili.com/video/BV1KV411w7jp` 中的 `BV1KV411w7jp`
* `timeLimit`是任务限制时间, 支持`s`, `m`, `h`和`d`4种单位, 例如`timeLimit: '5m'`代表任务限制5分钟, `timeLimit: '2m30s'`代表任务限制2分钟30秒, 
此值建议设置在5分钟之内, 因为本插件实际上只能通过规定时间内视频三连的次数来判断是否达成任务, 无法知道是谁达成的任务.
* condition是任务条件, List类型, 可选的值为`like`, `coin`和`favor`, 分别代表点赞, 投币和收藏, 如果不希望做任何检查, 可以留空`condition: []`.