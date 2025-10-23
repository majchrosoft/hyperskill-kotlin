package processor

class AddMatrixOperator(
) : MatrixOperator() {

    @Throws(CheckMatrixOperatorException::class)
    fun checkAdd(
        matrix1: Array<DoubleArray>,
        matrix2: Array<DoubleArray>
    ) {
        val rows1 = matrix1.size
        val rows2 = matrix2.size

        if (rows1 != rows2) {
            throw CheckMatrixOperatorException("Matrices must have the same number of rows for addition.")
        }

        for (row in 0 until rows1) {
            if (matrix1[row].size != matrix2[row].size) {
                throw CheckMatrixOperatorException("Matrices must have the same number of columns in each row for addition.")
            }
        }
    }

    // Perform addition operation
    fun add(
        matrix1: Array<DoubleArray>,
        matrix2: Array<DoubleArray>
    ): Array<DoubleArray> {
        try {
            checkAdd(matrix1, matrix2)
        } catch (e: Exception) {
            println("The operation cannot be performed.")
        }
        // Assuming the check has already passed
        return Array(matrix1.size) { row ->
            DoubleArray(matrix1[row].size) { col ->
                matrix1[row][col] + matrix2[row][col]
            }
        }
    }
}