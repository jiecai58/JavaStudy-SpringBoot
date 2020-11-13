package com.study.sort;

import java.util.Arrays;

public class Selection {
    public static void main(String[] args) {
        int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        selectionSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void selectionSort(int[] array) {
        if(null == array || array.length<=1){
            return;
        }
        int length = array.length;
        for (int i = 0; i < length -1; i++) {
            int minIndex = i;
            for (int j = i+1; j<length; j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            swap(array,minIndex,i);
        }
    }
    private static void swap(int[] array, int a, int b ){
        if(a != b){
            int temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        }
    }

}
