## Synchronized 详解

`synchronized`是`Java`中的关键字，是一种同步锁，也被称为内置锁，相当于互斥锁。它修饰的对象有以下几种：   
- 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号括起来的代码，作用的对象是调用这个代码块的对象。
```java
 /**
      * Synchronized 修饰代码块 对象锁
      *
      * @return
      */
     public void printCount1() throws InterruptedException {
         synchronized (this) {
             System.out.println(Thread.currentThread().getName());
             Thread.sleep(4000);
             count++;
         }
         System.out.println("printCount1: " + count);
     }
```

- 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象。
```java
  /**
      * Synchronized 修饰方法 对象锁
      *
      * @return
      */
     public synchronized void printCount2() throws InterruptedException {
         System.out.println(Thread.currentThread().getName());
         Thread.sleep(4000);
         count++;
         System.out.println("printCount2: " + count);
     }
```

- 修饰一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象。
```java
 /**
      * Synchronized 修饰静态方法 类锁
      *
      * @return
      */
     public static synchronized void printCount3() throws InterruptedException {
         System.out.println(Thread.currentThread().getName());
         Thread.sleep(4000);
         count++;
         System.out.println("printCount3: " + count);
     }
```
- 修饰一个类，其作用的范围是`synchronized`后面括号括起来的部分，作用的对象是这个类的所有对象。
```java
 /**
      * Synchronized 修饰一个类 类锁
      *
      * @return
      */
     public void printCount4() throws InterruptedException {
         synchronized (SynchronizedDemo.class) {
             System.out.println(Thread.currentThread().getName());
             Thread.sleep(4000);
             count++;
             System.out.println("printCount4: " + count);
         }
     }
```
测试发现：   
```java
public static void main(String[] args) {
        SynchronizedDemo synchronizedDemo1 = new SynchronizedDemo();
        SynchronizedDemo synchronizedDemo2 = new SynchronizedDemo();
        //相同对象实例 方法会被阻塞
//        Thread thread1 = new Thread(synchronizedDemo1);
//        Thread thread2 = new Thread(synchronizedDemo1);

        //不同对象实例 方法不会被阻塞
        Thread thread1 = new Thread(synchronizedDemo1);
        Thread thread2 = new Thread(synchronizedDemo2);

        thread1.start();
        thread2.start();


    }

    @Override
    public void run() {
        try {
            //对象锁 synchronized修饰普通方法,即为对象锁,多个线程访问同一个对象实例的这个方法时,方法会阻塞,并且只有一个线程执行完,另一个线程才会执行
            printCount1();


            //类锁 synchronized 不管多少个对象实例，都会同步
//            printCount4();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

### 总结 

`synchronized` 修饰锁分为 对象锁 和 类锁
- 对象锁 `synchronized`修饰普通方法,即为对象锁,多个线程访问同一个对象实例的这个方法时,方法会阻塞,并且只有一个线程执行完,另一个线程才会执行。如果线程访问的不同对象实例的方法，那么方法不会阻塞。
- 类锁 `synchronized` 不管多少个对象实例，都会同步。