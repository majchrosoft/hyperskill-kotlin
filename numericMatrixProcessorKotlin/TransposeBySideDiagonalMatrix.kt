package processor

class TransposeBySideDiagonalMatrix(): TransposeMatrixAbstract() {
    override fun transpose(mtx: Array<DoubleArray>): Array<DoubleArray>{
           return Array(mtx[0].size){
                rowInd ->
                DoubleArray(mtx.size) {
                    colInd -> mtx[mtx.size -colInd - 1][mtx[0].size - rowInd -1]
                }
            }
    }
}