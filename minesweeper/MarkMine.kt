package minesweeper

val markedCells = mutableListOf<Int>()

fun markMine(cell: Int) {
    markedCells.add(cell)
};

fun unmarkMine(cell: Int) {
    markedCells.remove(cell)
};