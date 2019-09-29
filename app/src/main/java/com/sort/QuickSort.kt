package com.sort

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

class QuickSort {

    var blcok:((index:Index) -> Unit)? = null
    var interval = 1000L

    private val semaphore = Semaphore(1)

    private var stop = false //主动停止
    private var isPause = false

    private val handle = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            println("msg ======")
            when {
                msg.what == 1 -> {
                    //一圈结束
                    blcok?.invoke(Index(looper = true))
                }
                msg.what == 3 ->{
                    //一圈开始
                    val index = msg.obj as Index
                    blcok?.invoke(index)
                }
                msg.what == 2 -> {
                    //结束
                    blcok?.invoke(Index(over = true))
                }
                else -> {
                    //比较
                    val index = msg.obj as Index
                    blcok?.invoke(index)
                    nextStep()
                }
            }
        }
    }

    private fun nextStep(){
        if (!isPause){
            handle.postDelayed({semaphore.release()} , interval)
        }
    }

    fun begin(arr:IntArray){
        isPause = false
        thread{
            quickSort(arr , 0 , arr.size-1)

            //回调
            val endMsg = Message.obtain()
            endMsg.what = 2
            handle.sendMessage(endMsg)
        }
    }

    fun stop(){
        this.stop = true
    }

    fun pause(){
        isPause = true
    }

    fun resume(){
        isPause = false
        nextStep()
    }

    private fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (stop){
            return
        }
        if (low >= high) {
            return
        }
        var l = low
        var h = high
        //取低位为参考值
        val value = arr[l]
        dispatchStartLooperEvent(l , l , h)

        while (l < h) {

            //高位开始
            while (arr[h] > value && l < h) {
                h--

                semaphore.acquire()
                dispatchCompareRightEvent(h)
                if (stop){
                    return
                }
            }
            while (arr[l] <= value && l < h) {
                l++

                //send message
                semaphore.acquire()
                dispatchCompareLeftEvent(l)
                if (stop){
                    return
                }
            }

            if (l < h) {
                val temp = arr[l]
                arr[l] = arr[h]
                arr[h] = temp
            }
        }

        //和标准值进行交换
        arr[low] = arr[l]
        arr[l] = value

        //一圈结束后回调
        dispatchEndLooperEvent()

        //递归低位
        quickSort(arr, low, l - 1)
        //递归高位
        quickSort(arr, h + 1, high)
    }

    private fun dispatchStartLooperEvent(keyIndex: Int,leftLimit: Int,rightLimit: Int){
        val msg = Message.obtain()
        msg.obj = Index(keyIndex = keyIndex , leftLimit = leftLimit , rightLimit = rightLimit)
        msg.what = 3
        handle.sendMessage(msg)
    }

    private fun dispatchCompareRightEvent(h: Int){
        val msg = Message.obtain()
        msg.obj = Index(h = h)
        msg.what = 0
        handle.sendMessage(msg)
    }

    private fun dispatchCompareLeftEvent(l: Int){
        val msg = Message.obtain()
        msg.obj = Index(l = l)
        msg.what = 0
        handle.sendMessage(msg)
    }

    private fun dispatchEndLooperEvent(){
        val loopMsg = Message.obtain()
        loopMsg.what = 1
        handle.sendMessage(loopMsg)
    }

}

data class Index(
    val l:Int = -1,
    val h:Int = -1,

    val keyIndex:Int = -1,
    val leftLimit:Int = -1,
    val rightLimit:Int = -1,

    val looper:Boolean = false,
    val over:Boolean = false
)


