package com.matyushkovvv.chess

interface ChessDelegate {
    fun pieceAt(col: Int, row: Int) : ChessPiece?
}