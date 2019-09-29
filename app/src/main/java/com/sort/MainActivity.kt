package com.sort

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.SharedMemory
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val items = mutableListOf<SortBean>()
        items.add(SortBean("quick sort" , QuickSortActivity::class.java))
        items.add(SortBean("view pager" , PageActivity::class.java))
        val linerManager = LinearLayoutManager(this)
        recycleView.layoutManager = linerManager
        val adapter = MyAdapter(items)
        recycleView.adapter = adapter

        bindService1()

        Handler().postDelayed({
            sendBroadcast(Intent("MyService"))
        }, 10000)

    }


    private fun bindService1(){

        var intent =  Intent(this, MyService::class.java);

        bindService(intent, connection , BIND_AUTO_CREATE)
    }

    private var connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("bindService", "onServiceDisconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("bindService", "onServiceConnected")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        startService(Intent(this, MyService::class.java))
    }

}

data class SortBean(
    var name:String,
    var clazz:Class<*>
)

class MyAdapter(private val items:List<SortBean>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_layout , viewGroup , false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context , items[position].clazz)
            ActivityCompat.startActivity(it.context , intent,null)
        }


    }

}

class ViewHolder(rootView:View) : RecyclerView.ViewHolder(rootView) {

    private val textView = rootView.findViewById<TextView>(R.id.textView)

    fun setData(bean:SortBean){
        textView.text = bean.name
    }

}
