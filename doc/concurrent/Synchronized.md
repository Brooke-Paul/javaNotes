## Synchronized 详解

`synchronized`是`Java`中的关键字，是一种同步锁，也被称为内置锁，相当于互斥锁。它修饰的对象有以下几种：   
- 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象。
```java
 /**
     * Synchronized 修饰代码块
     *
     * @return
     */
    public int printCount() {
        synchronized (this) {
            count++;
        }
        return count;
    }
```

- 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象。
```java
 /**
     * Synchronized 修饰方法
     *
     * @return
     */
    public synchronized int printCount2() {
        return count++;
    }
```

- 修饰一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象。
```java
 /**
     * Synchronized 修饰静态方法
     *
     * @return
     */
    public static synchronized void printCount3() {
        count++;
        System.out.println(count);
    }
```
- 修饰一个类，其作用的范围是synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象。
```java
 /**
     * Synchronized 修饰一个类
     *
     * @return
     */
    public void printCount4() {
        synchronized (SynchronizedDemo.class) {
            count++;
            System.out.println(count);
        }
    }
```