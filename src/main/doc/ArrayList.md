---
title: ArrayList/Vector的源码分析  
date: 2018-09-11 17:01:34 
comments: true 
tags:
- ArrayList
- Vector
categories:  
- java  
---

# ArrayList 
`ArrayList` 是动态数组，其实就是`Array`的复杂版本，它提供了动态增加和减少元素的功能，实现了`List`，`RandomAccess`, `Collection`接口，`ArrayList`不是线程安全的，建议在单线程中使用`ArrayList`。

`ArrayList` 包含两个重要属性分别是：

```java
/**
* elementData；
* transient 关键字修饰表示防止此字段被序列化
* 避免了浪费资源去存储没有的数据
* size:
* elementData中已存放的元素的个数，注意：不是elementData的容量
* 
*/
transient Object[] elementData;
private int size;
```
因为 `elementData`无法被序列化， 所以`ArrayList` 的序列化和反序列化依赖`writeObject` 和 `readObject`方法来实现
```java
/**
 * Save the state of the <tt>ArrayList</tt> instance to a stream (that
 * is, serialize it).
 *
 * @serialData The length of the array backing the <tt>ArrayList</tt>
 *             instance is emitted (int), followed by all of its elements
 *             (each an <tt>Object</tt>) in the proper order.
 */
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
    
/**
 * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
 * deserialize it).
 */
private void readObject(java.io.ObjectInputStream s)
    throws java.io.IOException, ClassNotFoundException {
    elementData = EMPTY_ELEMENTDATA;

    // Read in size, and any hidden stuff
    s.defaultReadObject();

    // Read in capacity
    s.readInt(); // ignored

    if (size > 0) {
        // be like clone(), allocate array based upon size not capacity
        ensureCapacityInternal(size);

        Object[] a = elementData;
        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            a[i] = s.readObject();
        }
    }
}
```

往`ArrayList` 中添加元素单个元素有两种方式
第一种：将单个元素添加到尾部，并将`size` + 1。
```java
/**
 * 向elementData中添加元素
 */
public boolean add(E var1) {
    this.ensureCapacityInternal(this.size + 1);//确保对象数组elementData有足够的容量，可以将新加入的元素e加进去
    this.elementData[this.size++] = var1;//加入新元素e，size加1
    return true;
}

/**
 * 确保数组的容量足够存放新加入的元素，若不够，要扩容
 */
public void ensureCapacity(int minCapacity) {
    modCount++;
    int oldCapacity = elementData.length;//获取数组大小（即数组的容量）
    //当数组满了，又有新元素加入的时候，执行扩容逻辑
    if (minCapacity > oldCapacity) {
        Object oldData[] = elementData;
        int newCapacity = (oldCapacity * 3) / 2 + 1;//新容量为旧容量的1.5倍+1
        if (newCapacity < minCapacity)//如果扩容后的新容量还是没有传入的所需的最小容量大或等于（主要发生在addAll(Collection<? extends E> c)中）
            newCapacity = minCapacity;//新容量设为最小容量
        elementData = Arrays.copyOf(elementData, newCapacity);//复制新容量
    }
}
```
第二种：将单个元素添加到指定位置。
```java
public void add(int index, E element) {
    rangeCheckForAdd(index);

    ensureCapacityInternal(size + 1);//确保对象数组elementData有足够的容量，可以将新加入的元素e加进去
    
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);//复制数组，将值往后移动
    elementData[index] = element;
    size++;
}
```
```java
/**
* 添加元素时判断elementData是否为空，为空则设置elementData的大小为10
*/
private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }

    ensureExplicitCapacity(minCapacity);
}
```
由此可见两种添加方式前者比后者节省资源消耗。


# Vector  
`Vector` 也是实现于`List`接口，底层数据结构和`ArrayList`类似，也是动态数组存放数据，不过是在 `add()` 方法的时候使用 `synchronized` 进行同步写数据，与`ArrayList`不同的是`Vector` 线程安全，但是开销较大，所以 `Vector` 是一个同步容器并不是一个并发容器。

```java
/**
* 在对象数组中尾部添加单个元素和在指定位置添加单个元素
* 使用synchronized 进行同步写数据
*/
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}

public void add(int index, E element) {
    insertElementAt(element, index);
}
public synchronized void insertElementAt(E obj, int index) {
    modCount++;
    if (index > elementCount) {
        throw new ArrayIndexOutOfBoundsException(index + " > " + elementCount);
    }
    ensureCapacityHelper(elementCount + 1);
    System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
    elementData[index] = obj;
    elementCount++;
}
```





