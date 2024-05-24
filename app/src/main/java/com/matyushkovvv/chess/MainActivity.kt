package com.matyushkovvv.chess

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ChessDelegate {

    private var chessModel = ChessModel()
    private lateinit var chessView: ChessView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chessView = findViewById<ChessView>(R.id.chess_view)
        chessView.chessDelegate = this
        findViewById<Button>(R.id.reset_button).setOnClickListener {
            chessModel.reset()
            chessView.invalidate()
        }
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return chessModel.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        chessModel.movePiece(fromCol, fromRow, toCol, toRow)

        // Перерисовываем всю доску заново
        chessView.invalidate()
    }
}