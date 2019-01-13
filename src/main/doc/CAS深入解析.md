---
title: CAS深入解析
date: 2019-01-13
comments: true 
tags:
- CAS
categories:  
- Java并发
---
## CAS
`CAS（Compare and swap）`就是比较和替换， 是一种通过硬件实现并发安全的技术。`CAS` 是 `JAVA` 并发包的实现基础，包含了三个操作数，
当且仅当V的值等于A时，`CAS` 才会通过原子方式用新值替换旧值。