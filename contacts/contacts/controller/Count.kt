package contacts.controller

import contacts.repository.Contacts
import contacts.utils.Output

fun countAction(output: Output) {
    val count = Contacts.contacts.size
    output.print("The Phone Book has $count records.")
    output.print("")
}
