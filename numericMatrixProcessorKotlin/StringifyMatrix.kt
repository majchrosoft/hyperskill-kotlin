package processor

class StringifyMatrix(val matrix: Array<DoubleArray>) {
    fun stringify() = matrix.joinToString("\n") { it.joinToString(" ") }
}