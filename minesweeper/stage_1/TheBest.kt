package minesweeper.stage_1

package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    var mines = readLine()!!.toInt()
    val field = CharArray(9 * 9) {if (it < mines) 'X' else '.'}
    field.shuffle()
    for (i in 0 until 9) println(field.slice(i * 9 until (i + 1) * 9).joinToString(""))
}package minesweeper
import java.util.*
import kotlin.random.Random.Default.nextInt

fun main2() {
    println("How many mines do you want on the field?")

    var bombs = Scanner(System.`in`).nextInt()
    var restCells = 9 * 9

    repeat(9){
        repeat(9) {
            if (nextInt(0, restCells) < bombs) {
                print("X")
                bombs--
            }  else print(".")
            restCells--
        }
        print("\n")
    }
}