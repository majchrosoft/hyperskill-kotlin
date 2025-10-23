package processor

class InverseMatrixOperator(
) : MatrixOperator() {

    private fun calculateSubMtxFunc(): (Array<DoubleArray>, Pair<Int, Int>) -> Array<DoubleArray> =
        { mtx, (exRow, exCol) ->
            mtx.toMutableList()
                .apply { removeAt(exRow) }
                .map {
                    it.toMutableList().apply { removeAt(exCol) }.toDoubleArray()
                }.toTypedArray()
        }

    // Perform addition operation
    fun determinant(
        matrix: Array<DoubleArray>,
    ): Double {
        val countMaxZero: (Array<DoubleArray>) -> Triple<Int, Int, Array<DoubleArray>> = { matrix ->
            matrix.mapIndexed { index, row ->
                index to row.count { it.toInt() == 0 }
            }.maxByOrNull { it.second }!!
                .let { Triple(it.first, it.second, matrix) }
        }

        // return direct calculation for matrix of size less or equal 2
        if (matrix.size == 1)
            return matrix[0][0]
        if (matrix.size == 2)
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0])

        //// calculate recursively for matrix greater than 2
        ////
        // choose the most efficient row or col
        val matrixT = TransposeMatrix().transpose(matrix)
        // check matrix
        val chosenMatrixData = listOf(
            countMaxZero(matrix),
            countMaxZero(matrixT)
        ).maxBy { it.second }

        // define matrix and chosen row
        val index = chosenMatrixData.first
        val mtx = chosenMatrixData.third
        val calculateSubMtx = calculateSubMtxFunc()

        // map row to subMatrices call on them determinant func and sum up
        return mtx[index].mapIndexed { colInd, value ->
            if (value == 0.0) {
                value
            } else {
                val sign = if ((index + colInd) % 2 == 0) 1 else -1
                val subMtx = calculateSubMtx(mtx, index to colInd)
                sign * value * this.determinant(subMtx)
            }
        }.sum()
    }

    // Perform addition operation
    fun cofactor(
        matrix: Array<DoubleArray>,
    ): Array<DoubleArray> {
        val signFn = { row: Int, col: Int -> if ((row + col) % 2 == 0) 1 else -1 }
        val calculateSubMtx = calculateSubMtxFunc()

        // return direct calculation for matrix of size less or equal 2
        if (matrix.size == 1)
            return matrix

        // calculate cofactors
        return Array(matrix.size) { row ->
            DoubleArray(matrix.size) { col ->
                val sign = signFn(row, col)
                val subMtx = calculateSubMtx(matrix, row to col)
                sign * this.determinant(subMtx)
            }
        }
    }

    fun adjugate(
        matrix: Array<DoubleArray>,
    ): Array<DoubleArray> {
        return TransposeMatrix().transpose(this.cofactor(matrix))
    }

    fun inverse(
        matrix: Array<DoubleArray>,
    ): Array<DoubleArray> {
        val det = determinant(matrix)
        if (det == 0.0) {
            throw CheckMatrixOperatorException("cannot inverse matrix with determinant equal to 0")
        }
        val adj = adjugate(matrix)
        return MultiplyByScalarMatrixOperator().multiply(adj, det)
    }
}