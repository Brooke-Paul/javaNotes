---
title: Synchronized与ReadWriteLock的区别
date: 2019-01-07
comments: true 
tags:
- Lock
categories:  
- Java并发
---

## Synchronized
`Synchronized`关键字是最基本的互斥同步，经过编译之后，会在同步块的前后形成`monitorenter`和`monitorexit`两个字节码指令，在和执行`monitorenter`
指令时，首先会尝试获取对象的锁，把锁的计数器加一。相应的，在执行`monitorexit`指令时会将计数器的值减一，当计数器的值为零时锁会被释放。如果获取对象锁失败，那么当前线程就会阻塞等待，直到对象锁被另一个线程释放为止。


##  Synchronized与ReadWriteLock相同点
1.在多线程并发编程中都能实现同步，当某个方法被`Synchronized`修饰时，其它的线程无法访问当前方法，只有阻塞等待此方法同步完成后获取锁。
2.`ReadWriteLock`为读写锁，适用情况为读多写少的场景，和`Synchronized`一样可以锁住某个方法，但是得手动释放锁。
3.`Synchronized` 和 `ReadWriteLock` 都是可重入锁，即一个线程在获取某个锁后，还可以继续获取同一个锁。

## Synchronized与ReadWriteLock区别
在内置锁`Synchronized`中使用简洁，不需要每次使用后手动释放锁，而`ReadWriteLock`必须每次使用后手动释放锁。
在内置锁`Synchronized`中不能手动中断锁操作，而`ReadWriteLock`可以手动取消锁。
`ReadWriteLock`是一种性能优化措施，执行读锁时效率比`Synchronized` 更高。


总结：
显示锁的设置上要灵活些，比如定时，轮询，以及中断操作等，但是使用难度要大些，并且容易忽略锁的释放。
当`Synchronized`无法满足多线程同步时才使用`ReadWriteLock`，否则尽量使用`Synchronized`。
`ReadWriteLock`使用场景，当允许多个读线程并发的访问被保护的对象时可以使用读写锁，提高程序可伸缩性。