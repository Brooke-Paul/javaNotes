package com.learning.sort;

/**
 * @Author xuetao
 * @Description: 排序实现
 * @Date 2019-06-21
 * @Version 1.0
 */
public class Sort {


    public static void main(String[] args) {
        int[] array = {0, 4, 5, 3, 9, 6, 2, 1};


//        bubblingSort(array);
        selectSort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * 冒泡排序升序排列
     *
     * @param array
     */
    public static void bubblingSort(int[] array) {

        for (int i = 1; i < array.length; i++) {
            boolean isSort = true;
            for (int j = 1; j <= array.length - i; j++) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                    isSort = false;
                }
            }

            if (isSort) {
                break;
            }
        }
    }

    /**
     * 选择排序升序排列
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
    }
}
