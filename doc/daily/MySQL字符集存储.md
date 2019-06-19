# mysql 字符集

- 记一次`mysql`字符保存问题
  
  出错内容如下：
  ```
    Incorrect string value: '\xCD\xF5\xB1\xA6\xC7\xBF' for column 'username'
  ```
  立即上网查看字符集，在 `Unicode` 中 `UTF-8`不是固定字长编码的，而是一种变长的编码方式。它可以使用1~4个字节表示一个符号，根据不同的符号而变化字节长度。   
  而在`mysql`中 `utf8` 只存储最多3个字节。
  

