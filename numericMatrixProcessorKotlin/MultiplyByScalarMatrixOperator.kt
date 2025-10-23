package processor

class MultiplyByScalarMatrixOperator(
) : MatrixOperator() {

    // Perform addition operation
    fun multiply(
        matrix1: Array<DoubleArray>,
        scalar: Double
    ): Array<DoubleArray> {
        // Assuming the check has already passed
        return Array(matrix1.size) { rowInd ->
            DoubleArray(matrix1[rowInd].size) { colInd -> matrix1[rowInd][colInd] * scalar }
        }
    }
}