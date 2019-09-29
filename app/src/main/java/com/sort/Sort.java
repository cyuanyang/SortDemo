package com.sort;

public class Sort {

    /**
     * 快速排序
     */
    public static void quickSort(int[] arr ){
        if (arr.length<=0){
            return;
        }
        quickSort(arr , 0 , arr.length-1);
    }

    /**
     * 例如：
     *
     * [4,2,1,5,9,2]
     *
     * 1.
     * [4,2,1,2,9,5]
     *
     * 2.
     * [2,2,1,4,9,5]
     * @param arr
     * @param low
     * @param high
     */
    private static void quickSort(int[] arr , int low , int high){

        if (low>=high){
            return;
        }

        int l = low;
        int h = high;
        //取低位为参考值
        int value = arr[l];
        while (l<h){
            //高位开始
            while (arr[h] > value && l < h){
                h--;
            }
            while (arr[l] <= value && l < h){
                l++;
            }

            if (l<h){
                int temp = arr[l];
                arr[l] = arr[h];
                arr[h] = temp;
            }
        }

        //和标准值进行交换
        arr[low] = arr[l];
        arr[l] = value;

        //递归低位
        quickSort(arr , low ,l-1);
        //递归高位
        quickSort(arr , h+1 , high);

    }
}
