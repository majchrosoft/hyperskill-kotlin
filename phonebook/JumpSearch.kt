package phonebook

import kotlin.math.floor
import kotlin.math.sqrt


fun calcSubOpt(opt: Int) = floor(sqrt(opt.toDouble())).toInt()

fun jumpSearch(needle: String, index: Int, optBlockSize: Int, haystack: List<String>): String {
//    println("DEBUG::" + index + "::" + optBlockSize + haystack)
    for (iter in 0..optBlockSize + 1) {
        val i = index + optBlockSize * iter
//        println("iter::" + iter)
//        println("i::" + i)
//        println("optBlockSize::" + optBlockSize)
//        println("iter::" + iter)
//        println()
        val returnik = when (true) {
            ((haystack.size == 1) and (haystack[i] != needle)) -> "-1"
            // found a needle
            (haystack[i] == needle) -> haystack[i]
            // needle is less than index
            (haystack[i] > needle) ->
                // if it's not the last optBlock, then continue with next optBlock
                if (i + optBlockSize < haystack.size)
                    "-2"
                // it's the last optBlock - start new SubOptBlock
                else jumpSearch(
                    needle,
                    0,
                    calcSubOpt(optBlockSize),
                    haystack.subList(i, haystack.size)
                )

            // needle is greater than index - is means that script is already in "next" optBlock
            // and must find in previous optBlock by searching with new subOptBlock
            (haystack[i] < needle) ->
                // check if it's not already begin of section

                if (
                    haystack.elementAtOrNull(i - optBlockSize) != null)
                    jumpSearch(
                        needle,
                        0,
                        calcSubOpt(optBlockSize),
                        haystack.subList(
                            i - optBlockSize,
                            i
                        )
                    )
                // if it's already begin of section - inform, that needle wasn't find
                else "-1"

            else -> "-1"
        }

        if (returnik == "-2")
            continue
        else
            return returnik
    }
    return "-1"
}

fun optBlockSize(haystack: List<String>): Int {
    return floor(sqrt(haystack.size.toDouble())).toInt()
}

fun runJumpSearch(needles: List<String>, haystack: List<String>): List<String> {
    return needles.map { jumpSearch(it, 0, optBlockSize(haystack), haystack) }
        .map { if (it in haystack) haystack.indexOf(it).toString() else "-1" }
}