package com.matyushkovvv.chess

import android.util.Log

class ChessModel {

    var piecesBox = mutableSetOf<ChessPiece>()

    init {
        reset()
    }

    fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) return
        val movingPiece = pieceAt(fromCol, fromRow) ?: return

        pieceAt(toCol, toRow)?.let {
            if(it.player == movingPiece.player) {
                Log.d(TAG,
                    "this action cannot be performed:\n" +
                            "movePiece(${fromCol}, ${fromRow}, ${toCol}, ${toRow})")
                return
            }

            piecesBox.remove(it)
        }

        piecesBox.remove(movingPiece)
        piecesBox.add(ChessPiece(toCol, toRow, movingPiece.player, movingPiece.rank, movingPiece.resID))

        Log.d(TAG, toString())
    }

    fun reset() {
        piecesBox.removeAll(piecesBox)

        for(i in 0..7) {
            piecesBox.add(ChessPiece(i, 1, ChessPlayer.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
            piecesBox.add(ChessPiece(i, 6, ChessPlayer.BLACK, ChessRank.PAWN, R.drawable.pawn_black))
        }

        for(i in 0..1) {
            piecesBox.add(ChessPiece(0 + i * 7, 0, ChessPlayer.WHITE, ChessRank.ROCK, R.drawable.rook_white))
            piecesBox.add(ChessPiece(0 + i * 7, 7, ChessPlayer.BLACK, ChessRank.ROCK, R.drawable.rook_black))

            piecesBox.add(ChessPiece(1 + i * 5, 0, ChessPlayer.WHITE, ChessRank.KNIGHT, R.drawable.knight_white))
            piecesBox.add(ChessPiece(1 + i * 5, 7, ChessPlayer.BLACK, ChessRank.KNIGHT, R.drawable.knight_black))

            piecesBox.add(ChessPiece(2 + i * 3, 0, ChessPlayer.WHITE, ChessRank.BISHOP, R.drawable.bishop_white))
            piecesBox.add(ChessPiece(2 + i * 3, 7, ChessPlayer.BLACK, ChessRank.BISHOP, R.drawable.bishop_black))
        }

        piecesBox.add(ChessPiece(3, 0, ChessPlayer.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        piecesBox.add(ChessPiece(3, 7, ChessPlayer.BLACK, ChessRank.QUEEN, R.drawable.queen_black))

        piecesBox.add(ChessPiece(4, 0, ChessPlayer.WHITE, ChessRank.KING, R.drawable.king_white))
        piecesBox.add(ChessPiece(4, 7, ChessPlayer.BLACK, ChessRank.KING, R.drawable.king_black))

        Log.d(TAG, toString())
    }

    fun pieceAt(col: Int, row: Int) : ChessPiece? {
        for(piece in piecesBox) {
            if(col == piece.col && row == piece.row) {
                return piece
            }
        }

        return null
    }

    override fun toString(): String {
        var desc = "\n"

        for(row in 7 downTo 0) {

            desc += "$row"
            for(col in 0..7) {

                val piece = pieceAt(col, row)
                if (piece == null) {
                    desc += " ."
                } else {
                    desc += " "
                    desc += when (piece.rank) {
                        ChessRank.KING -> { if(piece.player == ChessPlayer.WHITE) "k" else "K"}
                        ChessRank.QUEEN -> { if(piece.player == ChessPlayer.WHITE) "q" else "Q"}
                        ChessRank.BISHOP -> { if(piece.player == ChessPlayer.WHITE) "b" else "B"}
                        ChessRank.KNIGHT -> { if(piece.player == ChessPlayer.WHITE) "n" else "N"}
                        ChessRank.PAWN -> { if(piece.player == ChessPlayer.WHITE) "p" else "P"}
                        ChessRank.ROCK -> { if(piece.player == ChessPlayer.WHITE) "r" else "R"}
                    }
                }

            }

            desc += "\n"
        }

        desc += "  0 1 2 3 4 5 6 7"
        return desc
    }
}