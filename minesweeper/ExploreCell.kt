package minesweeper

val unexploredCells = (0..<DIM).toMutableList()
val wholeExploredCells = mutableListOf<Int>()
val exploredCells = mutableListOf<Int>()
val number1Cells = mutableListOf<Int>()
val number2Cells = mutableListOf<Int>()
val number3Cells = mutableListOf<Int>()
val number4Cells = mutableListOf<Int>()
val number5Cells = mutableListOf<Int>()
val number6Cells = mutableListOf<Int>()
val number7Cells = mutableListOf<Int>()
val number8Cells = mutableListOf<Int>()
val nonNumberNonExploredCells = mutableListOf<Int>()

fun exploreCell(cell: Int, refreshCellState: Boolean = true): CellState {
    wholeExploredCells.add(cell)
    unexploredCells.remove(cell)

    if (cell in mineCells) {
        cellState = CellState.MINE;
        return CellState.MINE
    } else {
        // tests want to manually unmark cell if it has been explored and it's not a mine
        if (cell in markedCells) unmarkMine(cell)
    }

    val cellStateInner = calculateCellState(cell)

    cellsState[cell] = cellStateInner
    if (cellStateInner == CellState.EXPLORED) {
        for (cellNeighbour in neighbourCellsFn(cell).filter {
            (it in unexploredCells)
                    && (cellsState[it] == CellState.UNEXPLORED)
        }) {
            exploreCell(cellNeighbour, false)
        }
    }

    when (cellStateInner) {
        CellState.NUMBER_1 -> number1Cells.add(cell)
        CellState.NUMBER_2 -> number2Cells.add(cell)
        CellState.NUMBER_3 -> number3Cells.add(cell)
        CellState.NUMBER_4 -> number4Cells.add(cell)
        CellState.NUMBER_5 -> number5Cells.add(cell)
        CellState.NUMBER_6 -> number6Cells.add(cell)
        CellState.NUMBER_7 -> number7Cells.add(cell)
        CellState.NUMBER_8 -> number8Cells.add(cell)
        CellState.EXPLORED -> exploredCells.add(cell)
        else -> nonNumberNonExploredCells.add(cell)
    }

    if (refreshCellState)
        cellState = cellStateInner;

    return cellStateInner;
}