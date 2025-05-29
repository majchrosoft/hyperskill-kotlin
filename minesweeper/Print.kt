package minesweeper

fun printMinefield(withMines: Boolean = false) {
    println(" |" + (1..DIM_COL).joinToString("") + "|")
    println("-|" + "-".repeat(DIM_COL) + "|")
    mineFieldCells
        .map {
            when (true) {
                (it in markedCells) -> CellState.MARKED.sign
                (it in number1Cells) -> CellState.NUMBER_1.sign
                (it in number2Cells) -> CellState.NUMBER_2.sign
                (it in number3Cells) -> CellState.NUMBER_3.sign
                (it in number4Cells) -> CellState.NUMBER_4.sign
                (it in number5Cells) -> CellState.NUMBER_5.sign
                (it in number6Cells) -> CellState.NUMBER_6.sign
                (it in number7Cells) -> CellState.NUMBER_7.sign
                (it in number8Cells) -> CellState.NUMBER_8.sign
                (it in exploredCells) -> CellState.EXPLORED.sign
                (withMines && (it in mineCells)) -> CellState.MINE.sign
                else -> CellState.UNEXPLORED.sign
            }
        }.joinToString("")
        .chunked(DIM_ROW).forEachIndexed { it, row ->
            println("${it + 1}|${row}|")
        }
    println("-|" + "-".repeat(DIM_COL) + "|")
}