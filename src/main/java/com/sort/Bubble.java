package com.sort;

import java.util.Arrays;

public class Bubble {
    public static void main(String[] args) {
        int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void bubbleSort(int[] array){
        if(array == null||array.length<=1){
            return;
        }
        int length = array.length;
        for (int i=0;i<length;i++) {
            for (int j = 0; j < length-1-i; j++) {
                if(array[j+1] < array[j]){
                    int temp = array[j];
                    array[j] =  array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }
}
