package processor

import kotlin.system.exitProcess

fun main() {

    val readLnToInt = { readln().toInt() }

    val MENU = "1. Add matrices\n" +
            "2. Multiply matrix by a constant\n" +
            "3. Multiply matrices\n" +
            "4. Transpose matrix\n" +
            "5. Calculate a determinant\n" +
            "6. Inverse matrix\n" +
            "0. Exit"

    val MENU_TRANSPOSE_CHOICES = "1. Main diagonal\n" +
            "2. Side diagonal\n" +
            "3. Vertical line\n" +
            "4. Horizontal line"

    val MENU_ADD = 1
    val MENU_MULTIPLY_BY_CONST = 2
    val MENU_MULTIPLY = 3
    val MENU_TRANSPOSE = 4
    val MENU_DETERMINANT = 5
    val MENU_INVERSE = 6
    val MENU_EXIT = 0

    val printMatrix = { it: Array<DoubleArray> -> println(StringifyMatrix(it).stringify()) }

    val checkMatrixDimensions: (Array<DoubleArray>, Int, Int) -> Boolean = { matrix, rows, cols ->
        matrix.count() == rows && matrix[rows - 1].count() == cols
    }

    val generateMatrix: () -> Array<DoubleArray> = {
        val (rows, cols) = readln().split(" ").map { it.toInt() }

        println("Enter matrix:")
        val matrix = Array(rows) {
            readln().split(" ").map { it.toDouble() }.toDoubleArray()
        }

        if (!checkMatrixDimensions(matrix, rows, cols)) {
            println("The operation cannot be performed.")
            exitProcess(1)
        }

        matrix
    }

    val multiplyByConstAction = {
        println("Enter size of matrix:")
        val m1 = generateMatrix()
        println("Enter constant: ")
        val scalar = readln().toDouble()
        val res = MultiplyByScalarMatrixOperator().multiply(m1, scalar)
        printMatrix(res)
    }

    val determinantAction= {
        println("Enter size of matrix:")
        val m1 = generateMatrix()
        val res = InverseMatrixOperator().determinant(m1)
        println("The result is:")
        println(res)
    }

    val addAction = {
        println("Enter size of first matrix:")
        val m1 = generateMatrix()
        println("Enter size of second matrix:")
        val m2 = generateMatrix()
        try {
            val res = AddMatrixOperator().add(m1, m2)
            println("The result is:")
            printMatrix(res)
        } catch (e: Exception) {
            println("The operation cannot be performed.")
        }
    }

    val multiplyAction = {
        println("Enter size of first matrix:")
        val m1 = generateMatrix()
        println("Enter size of second matrix:")
        val m2 = generateMatrix()
        try {
            val res = MultiplyMatrixOperator().multiplyThroughTransposeAndZip(m1, m2)
            println("The result is:")
            printMatrix(res)
        } catch (e: Exception) {
            println("The operation cannot be performed.")
            println(e.message)
        }
    }

    val transposeAction: (TransposeMatrixAbstract) -> Unit = { op ->
        println("Enter matrix size:")
        val m1 = generateMatrix()
        try {
            val res = op.transpose(m1)
            println("The result is:")
            printMatrix(res)
        } catch (e: Exception) {
            println("The operation cannot be performed.")
            println(e.message)
        }
    }

    val inverseAction = {
        println("Enter matrix size:")
        val m1 = generateMatrix()
        try {
            val res = InverseMatrixOperator().inverse(m1)
            printMatrix(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    val transposeActionMenu = {
        println(MENU_TRANSPOSE_CHOICES)
        val menu = readLnToInt()
        println("Your choice: > $menu")
        transposeAction(
            when (menu) {
                2 -> TransposeBySideDiagonalMatrix()
                3 -> TransposeVerticallyMatrix()
                4 -> TransposeHorizontallyMatrix()
                else -> TransposeMatrix()
            }
        )
    }

    do {
        println(MENU)
        val menu = readLnToInt()
        println("Your choice: > $menu")
        when (menu) {
            MENU_ADD -> addAction()
            MENU_MULTIPLY_BY_CONST -> multiplyByConstAction()
            MENU_MULTIPLY -> multiplyAction()
            MENU_TRANSPOSE -> transposeActionMenu()
            MENU_DETERMINANT -> determinantAction()
            MENU_INVERSE -> inverseAction()
            MENU_EXIT -> exitProcess(1)
            else -> println("Invalid option. Please try again.")
        }

    } while (menu != MENU_EXIT)

    exitProcess(1)
}