package com.matyushkovvv.chess

import android.view.View
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import kotlin.math.min

class ChessView(context: Context?, attrs: AttributeSet?): View(context, attrs) {

    private final val scaleFactor = 0.9f
    private final var cellSize: Float = 0f
    private final var originX: Float = 0f
    private final var originY: Float = 0f

    @RequiresApi(Build.VERSION_CODES.O)
    private final val lightColor = Color.argb(255, 238,238,210)
    @RequiresApi(Build.VERSION_CODES.O)
    private final val darkColor = Color.argb(255, 118,150,86)

    private final val imgResIDs = setOf(
        R.drawable.king_white,
        R.drawable.queen_white,
        R.drawable.rook_white,
        R.drawable.knight_white,
        R.drawable.bishop_white,
        R.drawable.pawn_white,
        R.drawable.king_black,
        R.drawable.queen_black,
        R.drawable.rook_black,
        R.drawable.knight_black,
        R.drawable.bishop_black,
        R.drawable.pawn_black
    )

    private final val bitmaps = mutableMapOf<Int, Bitmap>()
    private final val paint = Paint()

    var chessDelegate: ChessDelegate? = null

    init {
        loadBitmap()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {

        val chessBoardSide = min(canvas.height, canvas.width) * scaleFactor
        cellSize = chessBoardSide / 8f
        originX = (canvas.width - chessBoardSide) / 2f
        originY = (canvas.height - chessBoardSide) / 2f

        drawChessBoard(canvas)
        drawPieces(canvas)
    }

    private fun drawPieces(canvas: Canvas) {
        val chessModel = ChessModel()
        chessModel.reset()

        for(row in 0..7) {
            for(col in 0..7) {
                chessDelegate?.pieceAt(col, row)?.let { drawPieceAt(canvas, col, row, it.resID)}
            }
        }
    }

    private fun drawPieceAt(canvas: Canvas, col: Int, row: Int, resID: Int) {
        val whiteQueenBitmap = bitmaps[resID]!!
        canvas.drawBitmap(
            whiteQueenBitmap,
            null,
            RectF(
                originX + col * cellSize,
                originY + (7 - row) * cellSize,
                originX + (col + 1) * cellSize,
                originY + ((7 - row) + 1) * cellSize
            ),
            paint
        )
    }
    private fun loadBitmap() {
        imgResIDs.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawChessBoard(canvas: Canvas) {
        for(row in 0..7) {
            for(col in 0..7) {
                drawSquareAt(canvas, col, row, (col + row) % 2 == 1)
            }
        }

        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE
        paint.color = Color.argb(255, 0, 0, 0)
        canvas.drawRect(originX, originY, originX + 8 * cellSize, originY + 8 * cellSize, paint)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawSquareAt(canvas: Canvas, col: Int, row: Int, isDark: Boolean) {
        paint.color = if (isDark) darkColor else lightColor
        canvas.drawRect(originX + cellSize * col, originY + cellSize * row, originX + cellSize * (col + 1), originY + cellSize * (row + 1), paint)
    }
}