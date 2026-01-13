package contacts.utils

import contacts.enums.Prompt
import contacts.models.Contact

class ConsoleLogListener(override val output: Output = ConsoleOutput()) : LogListener {
    override fun onBeforeInput(prompt: Prompt) {
        val message = when (prompt) {
            Prompt.FIRST_NAME -> "Enter the name of the person:"
            Prompt.SURNAME -> "Enter the surname of the person:"
            Prompt.PHONE -> "Enter the number:"
        }
        output.print(message)
    }

    override fun onRecordCreated(contact: Contact) {
        output.print("A record created!")
    }

    override fun onPhoneBookCreated() {
        output.print("A Phone Book with a single record created!")
    }
}
