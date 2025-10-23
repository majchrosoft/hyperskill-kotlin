package processor

class TransposeMatrix(): TransposeMatrixAbstract() {
    override fun transpose(mtx: Array<DoubleArray>): Array<DoubleArray>{
           return Array(mtx[0].size){
                rowInd ->
                DoubleArray(mtx.size) {
                    colInd -> mtx[colInd][rowInd]
                }
            }
    }
}