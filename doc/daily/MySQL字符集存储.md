# mysql 字符集

- 记一次`mysql`字符保存报错问题
  
  插入一条数据，出错内容如下：
  ```
    Incorrect string value: '\xCD\xF5\xB1\xA6\xC7\xBF' for column 'username'
  ```
  立即上网查看字符集，在 `Unicode` 中 `UTF-8`不是固定字长编码的，而是一种变长的编码方式。它可以使用1~4个字节表示一个符号，根据不同的符号而变化字节长度。   
  而在`mysql`中 `utf8` 只存储最多3个字节。 如果存在大于三个字节的存储，将会报错。 在`MySQL5.5.3`版本以后，保存四个字节可以使用`utf8mb4`字符集。   
  `utf8mb4` 比 `utf8` 多了 直接存储表情的功能， 而且支持 `utf8` 的所有字符存储，所以推荐使用 `utf8mb4`。   
  


- 第一步：查看数据库编码格式
  
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
  
- 第二步：处理方式修改字符集
    
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
      
- 第三步： 如果以上步骤基本不行， 那么手动更改数据库配置，并且重启数据库。  
     
     ```java

        [client]
        default-character-set=utf8mb4
         
        [mysql]
        default-character-set=utf8mb4
         
        [mysqld]
        init_connect='SET collation_connection = utf8mb4_unicode_ci'
        init_connect='SET NAMES utf8mb4'
        character-set-server=utf8mb4
        collation-server=utf8mb4_unicode_ci
        
      ```    
    
  
- 第四步：在程序中执行数据写入发现还是无法存入？
      
      ```java
      原因是由于已存在的库表的字符集不会被更改，所以删库删表后重新建立库表。数据写入应该没问题吧😀？
      ```
      

- 继续第五步： 看`java`代码实现
      
      ```java
       以查询的方式执行字符集的更改，然后在进行数据写入。
       connection.prepareStatement("set names utf8mb4").executeQuery();
       connection.createStatement().executeUpdate(sql);
      ```     
      
 - `ok`，到这里基本搞定，重启项目写入`sql`。困扰一天的问题终于得到解决。     