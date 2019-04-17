## LinkedList 简介
`LinkedList` 是继承于`AbstractSequentialList`的双向链表， 它可以被当做堆栈，队列或双端队列使用。  
`LinkedList` 实现了`List`接口， 能对它进行队列操作。  
`LinkedList` 实现了`Cloneable`接口， 覆盖了函数`clone()`, 支持克隆。  
`LinkedList` 实现了`Deque`接口， 能将 `LinkedList`当做双端队列使用。  
`LinkedList` 实现了`Serializable`接口，意味着支持序列化， 便于在网络中传输和保存。  

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable {
    
    }
```
`LinkedList` 是采用链表的方式来实现`List`接口的,它本身有自己特定的方法，  如: `addFirst()`,`addLast()`,`getFirst()`,`getLast()`,`removeFirst()`，`removeFirst()`，`removeLast()`。

## 元素的插入与获取
#### addFirst() 和 addLast()
直接插入头部元素 和 尾部元素实现方式  
```java
public void addFirst(E e) {
    linkFirst(e);
}
private void linkFirst(E e) {
    final Node<E> f = first;
    final Node<E> newNode = new Node<>(null, e, f); //构造头部对象
    first = newNode;
    if (f == null) //如果f为空，则first == last
        last = newNode;
    else
        f.prev = newNode;  //否则 头节点为newNode
    size++;
    modCount++;
}
public void addLast(E e) {
        linkLast(e);
    }
//默认的添加方式也是由此方法实现    
void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null); //构造尾部对象
        last = newNode;
        if (l == null) //如果l为空，则first == last
            first = newNode;
        else
            l.next = newNode; //否则，尾节点为newNode
        size++;
        modCount++;
}
```
#### getFirst() 和 getLast() 
直接获取头部元素 和 尾部元素实现方式  
```java
public E getFirst() {
    final Node<E> f = first;
    if (f == null)
        throw new NoSuchElementException();
    return f.item;
}
 public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
}
```
`LinkedList` 查找元素

```java
public E get(int index) {
    checkElementIndex(index);
    return node(index).item;
}

Node<E> node(int index) {
    // assert isElementIndex(index);

    if (index < (size >> 1)) { //判断index 是否为 list size 的 一半
        Node<E> x = first;
        for (int i = 0; i < index; i++) // 从 0 ~ index 中循环遍历 
            x = x.next;
        return x;
    } else {
        Node<E> x = last;
        for (int i = size - 1; i > index; i--) // 从 index ~ zize - 1 中遍历
            x = x.prev;
        return x;
    }
}
```

#### 由此得出结论：
  `LinkedList` 采用了链表结构，所以在添加和删除方面效率高，查找比较慢  

## 元素的删除
#### removeFirst() 和 removeLast() 
`LinkedList` 移除首个元素 和 移除最后一个元素
```java
//removeFirst 主要实现
private E unlinkFirst(Node<E> f) {
        // assert f == first && f != null;
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null; //将当前元素清空
        f.next = null; // help GC
        first = next;  //指针后移
        if (next == null)
            last = null;
        else
            next.prev = null; //将节点prev置空
        size--;
        modCount++;
        return element;
}

//removeLast 主要实现
private E unlinkLast(Node<E> l) {
        // assert l == last && l != null;
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null; //将当前元素清空
        l.prev = null; // help GC
        last = prev; //指针前移
        if (prev == null)
            first = null;
        else
            prev.next = null; //将节点next置空
        size--;
        modCount++;
        return element;
}
```

主要移除元素底层实现(根据下标移除，根据元素移除)  
链表结构图如下：
![](https://dj-cdn.g2work.com/2018/09/29/bc14a140-e1d6-41ca-8ea8-75ff8ac14099.png?imageMogr2/auto-orient)  
将链表重新组装
```java
E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next; //获取当前节点下一节点
        final Node<E> prev = x.prev; //获取当前节点上一节点

        if (prev == null) { //如果上一节点为空，则首节点下一节点
            first = next;
        } else { //将上一节点的下一节点替换为当前元素的下一节点
            prev.next = next;
            x.prev = null;
        }

        if (next == null) { //如果下一节点为空，则尾节点上一节点
            last = prev;
        } else { //将下一节点的上一节点替换为当前元素的下一节点
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }
```

## LinkedList 用作堆栈
```java
/**
 * LinkedList 实现堆栈的先进后出
 */
public class StackDemo {
    private LinkedList linkedList = new LinkedList<>();

    /**
     * 将每次添加的元素都添加到第一个位置
     *
     * @param o
     */
    public void push(Object o) {
        linkedList.addFirst(o);
    }

    /**
     * 取出第一个元素但是不删除元素
     *
     * @return
     */
    public Object peek() {
        return linkedList.peek();
    }

    /**
     * 取出并移除元素
     *
     * @return
     */
    public Object poll() {
        return linkedList.poll();
    }

    /**
     * 获取元素个数
     *
     * @return
     */
    public int size() {
        return linkedList.size();
    }

    /**
     * 判断堆栈是否为空 
     * (即判断 linkedList是否为空) 
     *
     * @return 
     */
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public static void main(String[] args) {
        StackDemo stackDemo = new StackDemo();
        stackDemo.push("1");
        stackDemo.push("2");
        stackDemo.push("3");
        stackDemo.push("4");
        stackDemo.push("5");

        System.out.println("取出第一个元素：：：：" + stackDemo.peek());
        System.out.println("取出第一个元素：：：：" + stackDemo.poll());

        while (!stackDemo.isEmpty()) {
            System.out.print(stackDemo.poll());
        }
    }
}

```
`LinkedList` 用作堆栈 输出结果为
```java
插入第1个元素,值为 1
插入第2个元素,值为 2
插入第3个元素,值为 3
插入第4个元素,值为 4
插入第5个元素,值为 5
取出第一个元素 5
取出第一个元素 5
输出顺序为 4
输出顺序为 3
输出顺序为 2
输出顺序为 1
```

## LinkedList 用作队列


```java
/**
 * LinkedList 实现先进先出队列
 */
public class QueueDemo {


    public static void main(String[] args) {
        Queue queue = new LinkedList();
        //offer()方法是往队列尾部加入元素
        for (int i = 0; i < 5; i++) {
            queue.offer(i + 1);
            System.out.println("插入第"+ (i + 1) +"个元素," + "值为 " + (i + 1));
        }

        while (!queue.isEmpty()) {
            System.out.println("输出顺序 " + queue.poll());
        }


    }
}
```
输出结果为
```java
插入第1个元素,值为 1
插入第2个元素,值为 2
插入第3个元素,值为 3
插入第4个元素,值为 4
插入第5个元素,值为 5
输出顺序 1
输出顺序 2
输出顺序 3
输出顺序 4
输出顺序 5

```
