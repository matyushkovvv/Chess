package com.matyushkovvv.chess

import android.view.View
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import kotlin.math.min

class ChessView(context: Context?, attrs: AttributeSet?): View(context, attrs) {

    private final val scaleFactor = 0.9f
    private final var cellSize: Float = 0f
    private final var originX: Float = 0f
    private final var originY: Float = 0f
    
    private final val lightColor = Color.parseColor("#769656")
    private final val darkColor = Color.parseColor("#eeeed2")

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

    private var movingPieceBitmap: Bitmap? = null
    private var movingPiece: ChessPiece? = null
    private var fromCol: Int = -1
    private var fromRow: Int = -1
    private var movingPieceX: Float = -1f
    private var movingPieceY: Float = -1f

    var chessDelegate: ChessDelegate? = null

    init {
        loadBitmap()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)
    }

    override fun onDraw(canvas: Canvas) {

        val chessBoardSide = min(height, width) * scaleFactor
        cellSize = chessBoardSide / 8f
        originX = (width - chessBoardSide) / 2f
        originY = (height - chessBoardSide) / 2f

        drawChessBoard(canvas)
        drawPieces(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                fromCol = ((event.x - originX) / cellSize).toInt()
                fromRow = 7 - ((event.y - originY) / cellSize).toInt()

                chessDelegate?.pieceAt(fromCol, fromRow)?.let {
                    movingPiece = it
                    movingPieceBitmap = bitmaps[it.resID]
                }
                // Log.d(TAG, "down (${col}, ${row})")
            }
            MotionEvent.ACTION_MOVE -> {
                movingPieceX = event.x
                movingPieceY = event.y
                invalidate()
                // Log.d(TAG, "move")
            }
            MotionEvent.ACTION_UP -> {
                val col = ((event.x - originX) / cellSize).toInt()
                val row = 7 - ((event.y - originY) / cellSize).toInt()

                chessDelegate?.movePiece(fromCol, fromRow, col, row)
                movingPieceBitmap = null
                movingPiece = null

                Log.d(TAG, "from (${fromCol}, ${fromRow}) to (${col}, ${row})")
            }
        }
        return true
    }

    private fun drawPieces(canvas: Canvas) {
        for(row in 0..7) {
            for(col in 0..7) {
                // if(row != fromRow || col != fromCol) {
                //     chessDelegate?.pieceAt(col, row)?.let { drawPieceAt(canvas, col, row, it.resID)}
                // }

                chessDelegate?.pieceAt(col, row)?.let {
                    if (it != movingPiece) {
                        drawPieceAt(canvas, col, row, it.resID)
                    }
                }
            }
        }

        movingPieceBitmap?.let {
            canvas.drawBitmap(
                it,
                null,
                RectF(
                    movingPieceX - cellSize / 2,
                    movingPieceY - cellSize / 2,
                    movingPieceX + cellSize / 2,
                    movingPieceY + cellSize / 2
                ),
                paint
            )
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

    private fun drawChessBoard(canvas: Canvas) {
        for(row in 0..7) {
            for(col in 0..7) {
                drawSquareAt(canvas, col, row, (col + row) % 2 == 1)
            }
        }

        //paint.strokeWidth = 3f
        //paint.style = Paint.Style.STROKE
        //paint.color = Color.argb(255, 0, 0, 0)
        //canvas.drawRect(originX, originY, originX + 8 * cellSize, originY + 8 * cellSize, paint)
    }

    private fun drawSquareAt(canvas: Canvas, col: Int, row: Int, isDark: Boolean) {
        paint.color = if (isDark) darkColor else lightColor
        canvas.drawRect(originX + cellSize * col, originY + cellSize * row, originX + cellSize * (col + 1), originY + cellSize * (row + 1), paint)
    }
}