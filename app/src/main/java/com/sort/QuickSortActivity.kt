package com.sort

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_quick_sort.*

class QuickSortActivity : AppCompatActivity() {
    private var size = 10

    private var nums = IntArray(size)

    val quickSort:QuickSort = QuickSort()

    private var status = 0 //0 空闲 1.进行中 2.暂停 3.over

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_sort)
        sortView.lineMargin = 0

        quickSort.blcok = {index ->
            callback(index)
        }

        beginBtn.setOnClickListener {
            begin()
        }
        pauseBtn.setOnClickListener {
            pause()
        }
        numBtn.setOnClickListener {
            size = numView.text.toString().toInt()
            reset()
        }

        numView.setText(size.toString())
        numView.setSelection(size.toString().length)
        reset()

        bindService1()


    }

    private fun bindService1(){

        var intent =  Intent(this, MyService::class.java);

        bindService(intent, connection , BIND_AUTO_CREATE)
    }

    private var connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("bindService", "onServiceDisconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("bindService", "onServiceConnected")
        }

    }

    private fun callback(index:Index){
        if (index.over){
            status = 3
            beginBtn.text = "重置"
        }else{
            //更新UI
            if (index.h>= 0){
                sortView.rightIndex = index.h
            }
            if (index.l >= 0){
                sortView.leftIndex = index.l
            }
            if (index.keyIndex >= 0){
                sortView.keyIndex = index.keyIndex
                sortView.leftLimit = index.leftLimit
                sortView.rightLimit = index.rightLimit
            }

            if (index.looper){
                sortView.leftIndex = -1
                sortView.rightIndex = -1
                sortView.keyIndex = -1
            }
            sortView.update()
        }

        updateConsole(index)
    }

    private fun updateConsole(index:Index){
        var text= if (index.over){
            "排序结束"
        }else{
            "keyIndex=${index.keyIndex} L=${index.l} H=${index.h}"
        }
        text.let {
            consoleLayout.add(ConsoleInfo(it))
        }

    }

    private fun begin(){
        if (status == 0){
            //开始
            size = numView.text.toString().toInt()
            quickSort.begin(nums)
            status = 1
        }else if (status == 3){
            //重置
            status = 0
            beginBtn.text = "开始"
            reset()
        }
    }

    private fun pause(){
        if (status == 1){
            status = 2
            quickSort.pause()
            pauseBtn.text = "继续"
        }else if (status == 2){
            status = 1
            quickSort.resume()
            pauseBtn.text = "暂停"
        }
    }

    private fun reset() {
        nums = IntArray(size)
        (0 until size).forEachIndexed { index, value ->
            nums[index] = value
        }
        Reset().reset(nums)
        sortView.line = nums
    }

    override fun onDestroy() {
        super.onDestroy()
        quickSort.stop()
        unbindService(connection)
    }

}
