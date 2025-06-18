package phonebook

import kotlin.math.floor

fun foundBinary(array: MutableList<String>, needle: String): Int {
    var left = 1                            // the starting value of the left border
    var right = array.size                  // the starting value of the right border
    while (left <= right) {                // while the left border is to the left
// of the right one (or if they match)
        val middle =
            floor((left.toDouble() + right) / 2).toInt()    // finding the middle of the array
        if (array[middle] == needle) { // if the value from the middle of the array
// is equal to the target one
            return middle               // returning the index of this element
        } else if (array[middle] > needle) {// else if the value from the middle is greater
// than the target one
            right = middle - 1
        } else {                           // else (if the value from the middle is less than // the target one)
            left = middle + 1           // setting a new value to the left border (the one
        }
        // to the right of the middle one)
    }
    return -1
}