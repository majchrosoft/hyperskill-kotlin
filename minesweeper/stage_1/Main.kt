package minesweeper

import java.lang.StringBuilder
import kotlin.random.Random

const val DIM_ROW = 9
const val DIM_COL = 9
const val FIELD_EMPTY = '.'
const val FIELD_OCCUPIED = 'X'

fun generateBoardString(dim: Int, fieldDefault: Char): String {
    return fieldDefault.toString().repeat(dim)
}

fun randomIndexes(minesNo: Int, boardString: String): List<Int> {
    val randoms = mutableListOf<Int>()

    while (randoms.count() < minesNo) {
        var nextInt = Random.nextInt(boardString.length - 1)
        if (nextInt in randoms) {
            continue
        }

        randoms.add(nextInt)
    }
    return randoms;
}

fun fillBoardStringRandomly(boardString: String, minesNo: Int): String {
    val boardStringBuilder = StringBuilder(boardString)
    val randomIndexes = randomIndexes(minesNo, boardString)
    for (index in randomIndexes) {
        boardStringBuilder[index] = FIELD_OCCUPIED
    }
    return boardStringBuilder.toString();
}

fun printBoardString(boardString: String, dimRow: Int) {
    for (rowStartAt in 0..boardString.length - 1 step dimRow) {
        println(boardString.substring(rowStartAt, rowStartAt + dimRow))
    }
}

fun main() {
    val minesNo = readln().toInt()
    val dim = DIM_COL * DIM_ROW
    val shouldFillRandomly = minesNo < dim
    val fieldDefault = if (shouldFillRandomly) FIELD_EMPTY else FIELD_OCCUPIED

    var boardString = generateBoardString(dim, fieldDefault)

    boardString = if (shouldFillRandomly) fillBoardStringRandomly(boardString, minesNo) else boardString

    printBoardString(boardString, DIM_ROW)
}
