package hu.prooktatas.boxdraw.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.ContextMenu
import android.view.MotionEvent
import android.view.View
import hu.prooktatas.boxdraw.TAG
import hu.prooktatas.boxdraw.model.Box

//class DrawingBoard(ctx: Context, attrs: AttributeSet): View(ctx, attrs) {
class DrawingBoard(ctx: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatImageView(ctx, attrs) {
    val boxes = mutableListOf<Box>()

    private var currentBox: Box? = null

    private val pencilBox = Paint().apply {
        color = 0x22ff0000
    }

    private val pencilBg = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let {
            val x = event.x
            val y = event.y

            val currentPos = PointF(x, y)

            when(it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    currentBox = Box(currentPos, currentPos)
                    boxes.add(currentBox!!)
                }

                MotionEvent.ACTION_UP -> {
                    currentBox = null
                }

                MotionEvent.ACTION_MOVE -> {
                    currentBox?.let { box ->
                        box.end = currentPos
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    currentBox = null
                }

                else -> {
                    // do nothing
                }
            }
        }


        invalidate()
        return true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPaint(pencilBg)

            boxes.forEach { box ->
                drawRect(box.start.x, box.start.y, box.end.x, box.end.y, pencilBox)
            }
        }
    }

}