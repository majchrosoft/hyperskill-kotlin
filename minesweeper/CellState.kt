package minesweeper

val cellsState: MutableList<CellState> = mineFieldCells.map { CellState.UNEXPLORED }.toMutableList()
var cellState: CellState = CellState.UNEXPLORED

enum class CellState(val sign: Char) {
    UNEXPLORED('.'),
    EXPLORED('/'),
    NUMBER_1('1'),
    NUMBER_2('2'),
    NUMBER_3('3'),
    NUMBER_4('4'),
    NUMBER_5('5'),
    NUMBER_6('6'),
    NUMBER_7('7'),
    NUMBER_8('8'),
    MINE('X'),
    MARKED('*');
}

fun findCellStateBySign(sign: Char): CellState {
    for (cellState in CellState.entries) {
        if (cellState.sign == sign) {
            return cellState;
        }
    }

    throw RuntimeException()
}

fun neighbourCellsFn(cell: Int): List<Int> {
    return listOf(
        cell - 1,
        cell + 1,
        cell - DIM_COL,
        cell + DIM_COL,
        cell - DIM_COL - 1,
        cell - DIM_COL + 1,
        cell + DIM_COL - 1,
        cell + DIM_COL + 1,
    );
}

fun calculateCellState(cell: Int): CellState {
    val isLeftBorderField = cell % DIM_COL == 0
    val isRightBorderField = cell % DIM_COL == DIM_COL - 1

    val neighbourCells = neighbourCellsFn(cell)

    val neighbourShouldCount = listOf(
        !isLeftBorderField,
        !isRightBorderField,
        true,
        true,
        !isLeftBorderField,
        !isRightBorderField,
        !isLeftBorderField,
        !isRightBorderField,
    );

    val countFn =
        { con: Boolean, ind: Int ->
            if (
                con
                && (ind in mineCells)
            ) 1 else 0
        }

    val sumik = neighbourCells.withIndex()
        .map { (inn, ind) -> countFn(neighbourShouldCount[inn], ind) }
        .sum()

    return if (sumik > 0) findCellStateBySign(sumik.toString()[0]) else CellState.EXPLORED
}