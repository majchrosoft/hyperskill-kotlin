package minesweeper

import kotlin.random.Random

var mineCells = mutableListOf<Int>()

fun placeMines(firstMove: Int, minesNo: Int, dim: Int) {
    val randoms = mutableListOf<Int>()

    if (minesNo == dim) {
        mineCells = (0..<dim).toMutableList()
    }

    while (randoms.count() < minesNo) {
        var nextInt = Random.nextInt(dim - 1)
        if (nextInt in randoms || nextInt == firstMove) {
            continue
        }
        randoms.add(nextInt)
    }
    mineCells = randoms;
}