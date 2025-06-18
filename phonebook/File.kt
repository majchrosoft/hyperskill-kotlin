import java.io.File

fun fileFromPath(path: String): File {
    return File(path)
}

fun saveStringsToFile(strings: List<String>, file: File) {
    file.writeText(strings.joinToString("\n")) // Combine strings with newlines before writing
}
