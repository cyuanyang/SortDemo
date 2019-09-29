package com.sort;

/**
 * 将给定数据顺序打乱
 */
public class Reset {
    /**
     * 算法,采用随机抽样法，这样能保证概率一致
     * @param arr
     */
    public void reset(int[] arr){

        if (arr.length<=0){
            return;
        }
        reset(arr , 0);
    }

    private void reset(int[] arr , int i){
        if (i>=arr.length){
            return;
        }

        int index = i;
        int randomNum = randomNum(i,arr.length);

        //交换
        int temp = arr[i];
        arr[i] = arr[randomNum];
        arr[randomNum] = temp;

        //递归剩下的数
        reset(arr , ++index);
    }

    private int randomNum(int min , int max){
        return (int)(min+Math.random()*(max-min));
    }
}
