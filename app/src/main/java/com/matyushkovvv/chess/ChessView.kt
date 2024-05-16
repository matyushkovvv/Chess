package com.matyushkovvv.chess

import android.view.View
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet

class ChessView(context: Context?, attrs: AttributeSet?): View(context, attrs) {

    private final var cellSize: Float = 130f
    private final var originX: Float = 155f
    private final var originY: Float = 800f
    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.LTGRAY

        for(j in 0..7) {
            for(i in 0..7) {

                if((i + j) % 2 == 0) {
                    paint.color = Color.LTGRAY
                } else {
                    paint.color = Color.DKGRAY
                }

                canvas.drawRect(originX + cellSize * i, originY + cellSize * j, originX + cellSize * (i + 1), originY + cellSize * (j + 1), paint)
            }
        }
    }
}