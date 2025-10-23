package processor

class MultiplyMatrixOperator(
) : MatrixOperator() {

    @Throws(CheckMatrixOperatorException::class)
    fun checkMultiply(
        matrix1: Array<DoubleArray>,
        matrix2: Array<DoubleArray>
    ) {
        val rows1 = matrix1.size
        val cols2 = matrix2[0].size

        if (rows1 != cols2) {
            throw CheckMatrixOperatorException("Number of rows from first matrix must match number of cols from second one.")
        }
    }

    fun multiply(
        matrix1: Array<DoubleArray>,
        matrix2: Array<DoubleArray>
    ): Array<DoubleArray> {
        checkMultiply(matrix1, matrix2)
        return Array(matrix1.size) { rowInd ->
            DoubleArray(matrix2[0].size) { colInd ->
                var sum = 0.0
                for (ind in matrix1[rowInd].indices) {
                    sum += matrix1[rowInd][ind] * matrix2[ind][colInd]
                }
                sum
            }
        }
    }

    fun multiplyThroughTransposeAndZip(
        matrix1: Array<DoubleArray>,
        matrix2: Array<DoubleArray>
    ): Array<DoubleArray> {
        val transposedMtx2 = TransposeMatrix().transpose(matrix2)
        return matrix1.map { row ->
            transposedMtx2.map { col ->
                row.zip(col).sumOf { it.first * it.second }
            }.toDoubleArray()
        }.toTypedArray()
    }
}