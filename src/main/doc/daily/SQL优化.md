# SQL优化（mysql)

## 针对自身所缺乏的sql知识整理

1. 首先介绍一下`explain`,`explain`关键词用于展示某条`sql`语句是否展示了索引以及确认该`sql`语句是否必须进行优化。    

`explain` 使用实例：   
![](../../img/CD2AD4DB-EED9-4391-9DF7-C5A951CEDBFF.jpg)
关于`explain`的解释：
- `table`表示查询的表
- `type`表示查询使用类型，性能从左到右依次递减 `system` -> `const` -> `eq_req` -> `ref` -> `range` -> `index` -> `ALL`
在优化时至少达到`range`级别，最好可以达到`ref`。