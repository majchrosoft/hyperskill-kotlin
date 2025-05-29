package minesweeper

const val MSG_HOW_MANY_MINES = "How many mines do you want on the field?"
const val MSG_CONGRATS = "Congratulations! You found all the mines!"
const val MSG_MARK_OR_CLAIM = "Set/unset mines marks or claim a cell as free"
const val MSG_YOU_STEPPED_ON_A_MINE = "You stepped on a mine and failed!"

fun main() {
    val minesNo = askForMinesNumber()
    printMinefield()

    var firstMove = true;
    while (true) {
        var move = askForMove()
        if (move.action == Action.EXPLORE_CELL && firstMove) {
            placeMines(move.cell, minesNo, DIM)
            firstMove = false
        }
        if (move.action == Action.MARK_MINE)
            when (true) {
                (move.cell in markedCells) -> unmarkMine(move.cell)
                else -> markMine(move.cell)
            }
        if (move.action == Action.EXPLORE_CELL)
            exploreCell(move.cell)

        if (cellState == CellState.MINE) {
            printMinefield(true)
            println(MSG_YOU_STEPPED_ON_A_MINE)
            break;
        }
        printMinefield()

        if (
            !firstMove
            && markedCells.sorted() == mineCells.sorted()
        ) {
            println(MSG_CONGRATS)
            break;
        }
    }
}
