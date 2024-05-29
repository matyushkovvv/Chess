package com.matyushkovvv.chess

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ChessDelegate {

    private lateinit var chessView: ChessView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chessView = findViewById<ChessView>(R.id.chess_view)
        chessView.chessDelegate = this
        findViewById<Button>(R.id.reset_button).setOnClickListener {
            ChessGame.reset()
            chessView.invalidate()
        }
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return ChessGame.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        ChessGame.movePiece(fromCol, fromRow, toCol, toRow)

        // Перерисовываем всю доску заново
        chessView.invalidate()
    }
}