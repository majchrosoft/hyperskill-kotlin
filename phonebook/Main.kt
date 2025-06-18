package phonebook

import fileFromPath
import saveStringsToFile

const val MSG_FOUND_ENTRIES = "Found %d / %d entries. "
const val MSG_SEARCHING_LINEAR = "Start searching (linear search)..."
const val MSG_SEARCHING_QUICK_PLUS_BINARY = "Start searching (quick sort + binary search)..."
const val MSG_SEARCHING_BUBBLE_JUMP = "Start searching (bubble sort + jump search)..."
const val MSG_SORTING_TIME = "Sorting time: %d min. %d sec. %d ms."
const val MSG_STOPPED_MOVED_TO_LINEAR = " - STOPPED,\nmoved to linear search"
const val MSG_TIME_TAKEN = "Time taken: %d min. %d sec. %d ms."
const val MSG_SEARCHING_TIME = "Searching time: %d min. %d sec. %d ms."
const val PERIOD_THRESHOLD = 1
const val MSG_TIME = "%d min. %d sec. %d ms."
const val MSG_HASH_TABLE =
    "Start searching (hash table)... \nFound %d / %d entries. %s\nCreating time: %s\nSearching time: %s"
const val MSG_BUBBLE_TABLE =
    "Start searching (bubble sort + jump search)... \nFound %d / %d entries. %s\nSorting time: %s\nSearching time: %s"
//fun bubbleSort(List<>)

fun main() {
    val filePath =
        "/Users/pawelmajchrowicz/StudioProjects/Phone Book (Kotlin)/Phone Book (Kotlin)/task/src/phonebook/task.txt"

    val personNameFilePath = "/Users/pawelmajchrowicz/Downloads/find.txt"
    val phoneBookFilePath = "/Users/pawelmajchrowicz/Downloads/directory.txt"
    val phoneBookSortedFilePath = "/Users/pawelmajchrowicz/Downloads/directory_sorted.txt"
//    val personNameFilePath =
//        "/Users/pawelmajchrowicz/StudioProjects/Phone Book (Kotlin)/Phone Book (Kotlin)/task/src/phonebook/small_find.txt"
//    val phoneBookFilePath = "/Users/pawelmajchrowicz/Downloads/small_directory.txt"
//    val personNameFilePath = "/Users/pawelmajchrowicz/Downloads/small_find.txt"
//    val phoneBookSortedFilePath = "/Users/pawelmajchrowicz/Downloads/small_directory_sorted.txt"
//    val phoneBookFilePath =
//        "/Users/pawelmajchrowicz/StudioProjects/Phone Book (Kotlin)/Phone Book (Kotlin)/task/src/phonebook/small_directory.txt"
    val phoneBookFile = fileFromPath(phoneBookFilePath)
    val phoneBookSortedFile = fileFromPath(phoneBookSortedFilePath)
    val phoneBookEls = phoneBookFile.readLines().map {
        val parts = it.split(" ")
        return@map (parts[1] + if (parts.size == 3) " " + parts[2] else "").trim()
    }
    val personFile = fileFromPath(personNameFilePath)
    val personEls = personFile.readLines().map { it.trim() }
    val foundPersons = mutableSetOf<String>()

    val findPersonInPhoneBook = { personName: String ->
        phoneBookFile.useLines { lines ->
            lines.forEach { line ->
                if (line.contains(personName.trim())) foundPersons.add(personName.trim())
            }
        }
    }


    val wholeCnt = personFile.useLines { lines -> lines.count() }

    val linearSearch = {
        val (_, period) = measureTime {
            for (personName in personEls)
                findPersonInPhoneBook(personName)
        }
        period
    }
    println(MSG_SEARCHING_LINEAR)
    val period = linearSearch()
    println(
        String.format(
            MSG_FOUND_ENTRIES,
            foundPersons.size,
            wholeCnt
        )
                + formatTimeElapse(MSG_TIME_TAKEN, period)
    )
    println()

    var bubbleTooSlow = false
    val (_, periodBubble) = measureTime {
        val phoneBookElsSorted = phoneBookEls.toMutableList()
//            bubble(period, phoneBookEls) { arg1: String, arg2: String -> arg1 > arg2 }
            quickSortDesc(phoneBookElsSorted, 0, phoneBookEls.size -1)
        if (phoneBookElsSorted.isEmpty()) {
            bubbleTooSlow = true
        }
        saveStringsToFile(phoneBookElsSorted, phoneBookSortedFile)
    }

    val foundPersonsBubble = mutableSetOf<String>()
    val begin = System.currentTimeMillis()
    var jumpTooSlow = false
    var periodJump: Long = 0;
    if (!bubbleTooSlow) {
        val sortedDir = phoneBookSortedFile.readLines()
        val (_, periodJumpInner) = measureTime {
            for (person in personEls) {
                var foundPerson = jumpSearch(person, 0, optBlockSize(sortedDir), sortedDir)
                if (foundPerson != "-1") {
                    foundPersonsBubble.add(foundPerson)
                }

                if (System.currentTimeMillis() - begin > period * PERIOD_THRESHOLD) {
                    jumpTooSlow = true
                    return@measureTime
                }
            }
        }
        periodJump = periodJumpInner
    }

    if (bubbleTooSlow) {
        println(MSG_SEARCHING_BUBBLE_JUMP)
        String.format(
            MSG_FOUND_ENTRIES,
            foundPersonsBubble.size,
            wholeCnt
        ) + formatTimeElapse(MSG_TIME_TAKEN, periodBubble)
        println(formatTimeElapse(MSG_SORTING_TIME, periodBubble) + MSG_STOPPED_MOVED_TO_LINEAR)
        val (_, timeTaken) = measureTime { linearSearch() }
        println(formatTimeElapse("Searching time: 2 min. 02 sec. 231 ms.", timeTaken))
    }

    if (jumpTooSlow) {
        println(MSG_SEARCHING_BUBBLE_JUMP)
        String.format(
            MSG_FOUND_ENTRIES,
            foundPersonsBubble.size,
            wholeCnt
        ) + formatTimeElapse(MSG_TIME_TAKEN, periodJump)
        println(formatTimeElapse(MSG_SORTING_TIME, periodJump) + MSG_STOPPED_MOVED_TO_LINEAR)
        val (_, timeTaken) = measureTime { linearSearch() }
        println(formatTimeElapse("Searching time: 2 min. 02 sec. 231 ms.", timeTaken))
    }

    if (!jumpTooSlow && !bubbleTooSlow) {
        println(String.format(
            MSG_BUBBLE_TABLE,
            foundPersonsBubble.size,
            wholeCnt,
            formatTimeElapse(MSG_TIME_TAKEN, periodJump + periodBubble),
            formatTimeElapse(MSG_TIME, periodBubble),
            formatTimeElapse(MSG_TIME, periodJump),
        ))
    }


    val phoneBookElsQuickSorted = phoneBookEls.toMutableList()
    val (_, quickSortTime) = measureTime {
        quickSort(phoneBookElsQuickSorted, 0, phoneBookEls.size - 1)
    }

    var foundByBinarySearch = 0
    val binarySearch = {
        val (_, binaryTime) = measureTime {
            for (personName in personEls)
                if (foundBinary(phoneBookElsQuickSorted, personName) > -1) foundByBinarySearch++
        }
        binaryTime // return binaryTime from lambda
    }

    println()
    println(MSG_SEARCHING_QUICK_PLUS_BINARY)
    val binaryTime = binarySearch()
    println(
        String.format(
            MSG_FOUND_ENTRIES,
            foundByBinarySearch,
            wholeCnt
        )
                + formatTimeElapse(MSG_TIME_TAKEN, quickSortTime)
                + "\n"
                + formatTimeElapse(MSG_SORTING_TIME, quickSortTime)
                + "\n" + formatTimeElapse(MSG_SEARCHING_TIME, binaryTime)
    )
    println()


    var foundHashsetCounter = 0
    val (phoneBookHashSet: HashSet<String>, hashsetCreationTimeElapsed) = measureTime { phoneBookEls.toHashSet() }
    val (_, hashsetSearchTimeElapsed) = measureTime {
        for (person in personEls)
            foundHashsetCounter = foundHashsetCounter + if (phoneBookHashSet.contains(person)) 1 else 0
    }

    println(String.format(
        MSG_HASH_TABLE,
        foundHashsetCounter,
        wholeCnt,
        formatTimeElapse(MSG_TIME_TAKEN, hashsetCreationTimeElapsed + hashsetSearchTimeElapsed),
        formatTimeElapse(MSG_TIME, hashsetCreationTimeElapsed),
        formatTimeElapse(MSG_TIME, hashsetSearchTimeElapsed),
    ))
}

//    println(foundPersons - foundPersonsBubble)
