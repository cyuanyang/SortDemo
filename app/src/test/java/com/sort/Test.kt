package com.sort

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class Test {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testRest(){
        var arr = intArrayOf(1,2,3,4,5,6,7,8,9,10)
        val reset = Reset()
        reset.reset(arr)

        arr.forEach {
            print(" $it")
        }
    }

    @Test
    fun testQuickSort(){
        val arr = IntArray(10)

        (0..9).forEachIndexed { index, i ->
            arr[index] = i
        }

        Reset().reset(arr)
        arr.forEach {
            print(" $it")
        }
        println()
        Sort.quickSort(arr)
        arr.forEach {
            print(" $it")
        }
    }
}
