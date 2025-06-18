package phonebook


class Entry(index: Int, row: String, phone: Int, fullName: String)

fun mapToEntry(index: Int, row: String): Entry {
    val rowValues = row.split(" ")
    return Entry(index, row, rowValues[0].toInt(), rowValues[1])
}