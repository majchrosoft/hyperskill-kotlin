package phonebook

fun quickSort(A: MutableList<String>, l: Int, r: Int) {
    if (l >= r) {
        return
    }
    // partition the array around the pivot
    val pivot = partition(A, l, r)
    quickSort(A, l, pivot - 1)  // recursively sort the lower side
    quickSort(A, pivot + 1, r)  // recursively sort the upper side

}

fun quickSortDesc(A: MutableList<String>, l: Int, r: Int) {
    if (l >= r) {
        return
    }
    // partition the array around the pivot
    val pivot = partitionDesc(A, l, r)
    quickSortDesc(A, l, pivot - 1)  // recursively sort the lower side
    quickSortDesc(A, pivot + 1, r)  // recursively sort the upper side

}

fun partition(A: MutableList<String>, l: Int, r: Int): Int {
    val x = A[r]                  // the pivot
    var i = l - 1
    for (j in l..r - 1) {       // process each element except the pivot
        if (A[j] < x) {            // does it belong to the lower side?
            i += 1
            A[j] = A[i].also { A[i] = A[j] }  // put the element to the lower side
        }
    }
    A[i + 1] = A[r].also { A[r] = A[i + 1] }  // put the pivot to the right of the lower side
    return i + 1              // return the new index of the pivot
}

fun partitionDesc(A: MutableList<String>, l: Int, r: Int): Int {
    val x = A[r]                  // the pivot
    var i = l - 1
    for (j in l..r - 1) {       // process each element except the pivot
        if (A[j] > x) {            // does it belong to the lower side?
            i += 1
            A[j] = A[i].also { A[i] = A[j] }  // put the element to the lower side
        }
    }
    A[i + 1] = A[r].also { A[r] = A[i + 1] }  // put the pivot to the right of the lower side
    return i + 1              // return the new index of the pivot
}