package contacts.utils
import contacts.models.Contact
import contacts.enums.Prompt

import contacts.utils.Output

interface LogListener {
    val output: Output
    fun onBeforeInput(prompt: Prompt)
    fun onRecordCreated(contact: Contact)
    fun onPhoneBookCreated()
}
