package phonebook

import java.io.File
import kotlin.math.*

inline fun <T> measureTime(action: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = action()
    val duration = System.currentTimeMillis() - start
    return result to duration
}

fun formatTimeElapse(msg: String, time: Long): String {
    val minutes = (time / 1000 / 60)
    val seconds = (time - (minutes * 60 * 1000)) / 1000
    val milliseconds = time - (minutes * 60 * 1000) - (seconds * 1000)

    return String.format(
        msg,
        minutes,
        seconds,
        milliseconds
    )
}
