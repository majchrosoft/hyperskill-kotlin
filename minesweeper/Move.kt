package minesweeper

class Move(val cell: Int, val action: Action) {
}


fun askForMove(): Move {
    println(MSG_MARK_OR_CLAIM)
    val (colArg, rowArg, action) = readln().split(" ")
    val cell = (rowArg.toInt() - 1) * DIM_COL + colArg.toInt() - 1
    return Move(cell, findActionByName(action))
}

fun askForMinesNumber(): Int {
    println(MSG_HOW_MANY_MINES)
    return readln().toInt()
}