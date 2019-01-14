package com.learning.lock;

/**
 * @Author xuetao
 * @Description: Synchronized 采用悲观锁的方式实现同步
 * @Date 2019-01-15
 * @Version 1.0
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        CountTest countTest = new CountTest();
        for (int j = 0; j < 100; j++) {
            Thread thread = new Thread(countTest);
            thread.start();

        }

    }

}

class CountTest implements Runnable {
    private int count;

    @Override
    public void run() {
        synchronized (CountTest.class) {

            count++;
            System.out.println("current count:" + this.count);
        }
    }
}
//    javap -c CountTest
//    4: monitorenter 加锁操作，monitorenter指令执行时会让对象的锁计数加1
//    5: aload_0
//    6: dup
//    7: getfield      #3                  // Field count:I
//    10: iconst_1
//    11: iadd
//    12: putfield      #3                  // Field count:I
//    15: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
//    18: new           #5                  // class java/lang/StringBuilder
//    21: dup
//    22: invokespecial #6                  // Method java/lang/StringBuilder."<init>":()V
//    25: ldc           #7                  // String current count:
//    27: invokevirtual #8                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//    30: aload_0
//    31: getfield      #3                  // Field count:I
//    34: invokevirtual #9                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
//    37: invokevirtual #10                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
//    40: invokevirtual #11                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//    43: aload_1
//    44: monitorexit 释放锁操作，monitorexit指令执行时会让对象的锁计数减1
