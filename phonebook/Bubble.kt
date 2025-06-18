package phonebook

fun bubble(
    period: Long,
    elsArg: List<String>,
    comparatorFn: (str1: String, str2: String) -> Boolean
): List<String> {
    val beginBubble = System.currentTimeMillis()
    val els = elsArg.toMutableList()
    for (ind in 0..els.size - 2) {
        var swapped = false;
        for (i in 0..els.size - 2 - ind) {
            if (comparatorFn(els[i], els[i + 1])) {
                els[i] = els[i + 1].also { els[i + 1] = els[i] }
                swapped = true
            }
        }
        if (!swapped) {
            break
        }

        if (System.currentTimeMillis() - beginBubble > period * PERIOD_THRESHOLD) {
            return listOf()
        }
    }
    return els.toList()
}