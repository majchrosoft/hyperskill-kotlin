package contacts.utils

fun readGender(output: Output): String {
    while (true) {
        output.print("Enter the gender (M, F): ")
        val input = readln().trim()
        if (input.isEmpty()) {
            output.print("Bad gender!")
            return ""
        }
        if (input in listOf("M", "F")) {
            return input
        }
        output.print("Bad gender!")
    }
}
