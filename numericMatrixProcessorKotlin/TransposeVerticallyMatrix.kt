package processor

class TransposeVerticallyMatrix() : TransposeMatrixAbstract() {
    override fun transpose(mtx: Array<DoubleArray>): Array<DoubleArray> {
        return Array(mtx[0].size) { rowInd ->
            DoubleArray(mtx.size) { colInd ->
                mtx[rowInd][mtx[0].size - colInd - 1]
            }
        }
    }
}