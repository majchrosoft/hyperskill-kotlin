package contacts.controller
import contacts.repository.Menu
import contacts.utils.Output

fun exitAction(output: Output) {
    Menu.isRunning = false
}
