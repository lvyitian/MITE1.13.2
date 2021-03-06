[English Version](https://github.com/XiaoYuOvO/MITE1.13.2/blob/master/ChangeLogEN.md)
# B0.5.5 更新日志
## 世界
* 添加了新的世界--地底世界(暂时只能使用`/execute in minecraft:underworld run tp @s ~ ~ ~`来进入)
## 世界生成
* 添加了除艾德曼以外的矿物的生成
---
# B0.5.3 更新日志
## Bug修复
* 修正睡眠时间,让睡觉起来就是日出,使睡觉不会重置天数
* 修复工作台不能在矿洞打开的Bug
* 修正摔落伤害,使之与原版MITE一样
## 世界生成
* 降低砂砾在世界中的生成大小和范围
---
# B0.5.2 更新日志
## 方块
* 1、添加各级工作台
* 2、添加各级熔炉
## 物品
* 添加绿宝石,钻石,石英,玻璃,黑曜石,燧石碎片
## 物品掉落
* 修改砂砾的各种掉落物的掉落率,如下表

掉落物品| 几率
------------ | -------------
沙砾（它本身） |3/4 
燧石碎片 |5/32 
铜粒 |1/18 
银粒 |1/54 
燧石 |1/96 
金粒 |1/162 
黑曜石碎片 |1/486 
绿宝石碎片 |1/1458 
钻石碎片 |1/4374 
秘银粒 |1/13122 
艾德曼粒 |1/26244 
---
# B0.5.0 更新日志
## 方块
* 添加各种材料的铁砧
## 游戏机制
* 1、现在我们需要解锁结构，使它们可以在世界上生成。各个结构要\
     求在进度页面中，您需要达到10级才能解锁所有结构的根本进度
* 2、让附魔书不能合并在一起

## 生成
* 现在针叶林的动物更少

## 渲染
* 1、让玩家总能看到物品的耐久
* 2、修复铁砧的纹理

## Bug修复
* 1、修复不能制作烟花，染色衣服和复制成书
* 2、修复由于数据包传输错误而无法进行多人游戏的问题
* 3、修复[MC-101233](https://bugs.mojang.com/browse/MC-101233)

## 其他
* 删除了教程中的撸树环节
---
# B0.4.0更新日志
## 游戏机制
* 现在物品掉耐久更快,工具的为挖掘的方块的硬度的100倍(斧子为45倍)\
  剑为每次50点耐久
     
## 物品
* 1、添加原版1.6.4MITE的各等级的剑、镐、锹、斧、锄
* 2、添加一种新的材料--钨,比秘银好,比艾德曼差,耐久是秘银的两倍

## 方块
* 添加新增加的各种材料的矿物和矿物块
---
# B0.3.1更新日志
## 游戏机制
* 世界生成时血量为20，应为6
---
# B0.3.0更新日志
## 游戏机制
* 1、升级需要更高经验值:
          设等级为n
          每级所需经验=10(n+1)
          每级总经验=5n²+15n
* 2、跑跳破坏方块饥饿值加多 (X1.5)
* 3、泥土 沙子 树叶 破坏速度减慢:
         分别为11s 10.5s 10s
* 4、 死亡掉落死前经验的三分之一，如果没有等级则会掉为负数

## 生成
* 1、动物生成率减少,生成权重、每组大小减半
* 2、每隔128天生成一次生物

## 物品
* 移除木镐、木斧、木锄头

## Bug修复
* 血量没有真正的3格

## 反作弊
* 1、坐标删除
* 2、禁止使用作弊
* 3、更新存档文件版本，现只支持MITE的存档的打开
---
# B0.2.1更新日志
## 物品
* 添加燧石工具
* 加入沙拉
* 让种子可食用，加0.4饱和度

## 物品掉落
* 使燧石的掉落几率降低
* 让叶子掉落木棍

## 游戏机制
* 改变了饥饿机制，使游戏更难

## 方块
* 泥土会像砾石和沙子一样掉下来

## 技术性
* 使语言文件可以包含在jar文件中