package contacts.service

import contacts.builders.contacts
import contacts.exceptions.ValidationException
import contacts.models.Contact
import contacts.utils.ConsoleLogListener
import contacts.utils.LogListener
import contacts.utils.Output

object AddContact {
    fun handle(output: Output) {
        val logger = object : LogListener by ConsoleLogListener(output) {
            override val output: Output = output
            override fun onRecordCreated(contact: Contact) {
                output.print("The record added.")
            }
            override fun onPhoneBookCreated() {}
        }

        output.print("Enter the type (person, organization): ")
        val type = readln().lowercase()

        try {
            contacts(logger) {
                when (type) {
                    "person" -> person {
                        firstName()
                        surName()
                        birthDate()
                        gender()
                        phone()
                    }
                    "organization" -> organisation {
                        name()
                        address()
                        phone()
                    }
                    else -> {}
                }
            }
            output.print("")
        } catch (e: ValidationException) {
            output.print(e.message ?: "Wrong format!")
        }
    }
}
