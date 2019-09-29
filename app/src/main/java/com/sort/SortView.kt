package com.sort

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View

open class SortView: View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    private var paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @ColorInt var color:Int = Color.BLACK
    var lineMargin = 4

    var line:IntArray = intArrayOf(1,2,3,4)
        set(value) {
            field = value
            invalidate()
        }

    var hintLines:SparseArray<Line> = SparseArray(2)
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.color = color
        paint.strokeWidth = 10.0f
        paint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val width = measuredWidth
        val height = measuredHeight
        val lineWidth = width*1.0f/line.size
        val unitHeight = height*1.0f/line.size

        line.forEachIndexed { index, value ->
            val rect = RectF(
                getLeftPx(lineWidth , index),
                height-unitHeight*(value+1),
                getRightPx(lineWidth , index) ,
                height.toFloat())

            val hintLine = hintLines.get(index)

            val tempColor = paint.color
            if (hintLine!=null){
                paint.color = hintLine.color
            }
            canvas.drawRect(rect , paint)

            paint.color = tempColor
        }
    }

    fun getLeftPx(lineWidth :Float ,index:Int):Float = lineWidth * index + lineMargin

    fun getRightPx(lineWidth :Float ,index:Int):Float = lineWidth * index + lineWidth-lineMargin

    class Line(color: Int,type:Int = 0){
        var color:Int = color
        var type = 0 // 0 左边 1右边 2中间 3右边基准值 4左边基准值
        var text:String? = null //文字
    }
}


class QuickSortView : SortView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    var leftIndex:Int = -1

    var rightIndex:Int = -1

    var keyIndex:Int = -1
    var leftLimit:Int = -1
    var rightLimit:Int = -1

    private val rect = RectF()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.CYAN
        paint.style = Paint.Style.FILL
    }

    fun update(){
        val sparseArray = SparseArray<Line>(4)
        if (leftIndex>=0){
            sparseArray.put(leftIndex , Line(Color.RED))
        }
        if (rightIndex>=0){
            sparseArray.put(rightIndex , Line(Color.BLUE))
        }
        if (keyIndex>=0){
            sparseArray.put(keyIndex , Line(Color.YELLOW))
        }
        hintLines = sparseArray
    }

    override fun onDraw(canvas: Canvas) {
        if (keyIndex >= 0){
            val lineWidth = width*1.0f/line.size
            //比较区域的背景
            rect.left = getLeftPx(lineWidth , leftLimit)
            rect.top = 0.0f
            rect.right = getRightPx(lineWidth , rightLimit)
            rect.bottom = measuredHeight.toFloat()
            canvas.drawRect(rect,paint)
        }
        super.onDraw(canvas)
    }

}