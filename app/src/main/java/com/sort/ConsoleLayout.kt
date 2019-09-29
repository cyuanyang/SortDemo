package com.sort

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView

data class ConsoleInfo(
    val text:String
)

class ConsoleLayout : FrameLayout{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var contentView:ListView? = null
    private var adapter:Adapter? = null

    private val items = mutableListOf<ConsoleInfo>()

    override fun onFinishInflate() {
        super.onFinishInflate()

        val view = LayoutInflater.from(context).inflate(R.layout.console_layout , null)
        contentView = view as ListView
        this.addView(contentView , generateDefaultLayoutParams())

        adapter = Adapter(items)
        contentView?.adapter = adapter
    }

    fun add(info:ConsoleInfo){
        items.add(info)
        adapter?.notifyDataSetChanged()
    }

}

class Adapter(private var items:List<ConsoleInfo>) : BaseAdapter() {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        var viewHolder:Holder
        if (view==null){
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.console_item_layout , parent , false)
            viewHolder = Holder(view)
            view.tag = viewHolder
        }else{
            viewHolder = view.tag as Holder
        }

        viewHolder.setData(items[position])

        return view!!
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}

class Holder(itemView:View){

    private val textView : TextView = itemView.findViewById(R.id.textView)

    fun setData(info:ConsoleInfo){
        textView.text = info.text
    }

}