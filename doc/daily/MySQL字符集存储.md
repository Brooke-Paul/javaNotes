# mysql 字符集

- 记一次`mysql`字符保存报错问题
  
  出错内容如下：
  ```
    Incorrect string value: '\xCD\xF5\xB1\xA6\xC7\xBF' for column 'username'
  ```
  立即上网查看字符集，在 `Unicode` 中 `UTF-8`不是固定字长编码的，而是一种变长的编码方式。它可以使用1~4个字节表示一个符号，根据不同的符号而变化字节长度。   
  而在`mysql`中 `utf8` 只存储最多3个字节。 如果存在大于三个字节的存储，将会报错。 在`MySQL5.5.3`版本以后，保存四个字节可以使用`utf8mb4`字符集。   
  `utf8mb4` 比 `utf8` 多了 直接存储表情的功能， 而且支持 `utf8` 的所有字符存储，所以推荐使用 `utf8mb4`。   
  
- 查看数据库编码格式
  
  ```java
  show variables like 'character_set_database';
  ``` 
- 查看MySQL数据库服务器和数据库MySQL字符集
  
  ```java
  show variables like '%char%';
  ```   
  
- 查看数据库表的编码格式
  
  ```java
  show create database <数据库名>;
  show create table <表名>;
  ``` 
  
- 修改数据库编码格式为 `utf8mb4`  
  ```java
  alter database <数据库名> character set utf8mb4;
  ``` 
 
- 修改表编码格式为 `utf8mb4`  
  ```java
  alter table <表名> character set utf8mb4;  
  ```
- 修改字段编码格式为 `utf8mb4`    
  ```java
  alter table <表名> modify column <字段名> <字段类型> character set utf8mb4 collate utf8mb4_unicode_ci;
  ```


- 总结：
  字符集问题困扰解决，修改编码集为 `utf8mb4`。