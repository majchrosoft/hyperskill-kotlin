package contacts.controller

import contacts.service.SearchContact
import contacts.utils.Output

fun searchAction(output: Output) {
    SearchContact.handle(output)
}
