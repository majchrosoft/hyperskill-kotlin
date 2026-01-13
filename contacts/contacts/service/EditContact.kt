package contacts.service

import contacts.repository.Contacts
import contacts.utils.Output

import contacts.models.Person
import contacts.models.Organisation
import java.time.LocalDateTime
import contacts.utils.readGender
import contacts.models.Contact

object EditContact {
    fun handle(output: Output) {
        if (!ContactListService.showList(output)) {
            output.print("No records to edit!")
            return
        }

        output.print("Select a record: ")
        val indexInput = readln().toIntOrNull()
        if (indexInput == null || indexInput !in 1..Contacts.contacts.size) {
            return
        }
        val contactIndex = indexInput - 1
        val contact = Contacts.contacts[contactIndex]
        
        editContact(output, contact)
    }

    fun editContact(output: Output, contact: Contact) {
        val fields = contact.getFields().joinToString(", ")

        output.print("Select a field ($fields): ")
        val fieldChoice = readln().lowercase()

        if (fieldChoice !in contact.getFields()) {
            return
        }

        var updated = false
        when (fieldChoice) {
            "birth" -> {
                output.print("Enter the birth date: ")
                val input = readln()
                if (input.isEmpty() || !isValidDate(input)) {
                    output.print("Bad birth date!")
                    contact.setFieldValue("birth", "")
                } else {
                    contact.setFieldValue("birth", input)
                }
                updated = true
            }
            "gender" -> {
                contact.setFieldValue("gender", readGender(output))
                updated = true
            }
            "number" -> {
                output.print("Enter number: ")
                val newNumber = readln()
                val error = contact.validateField("number", newNumber)
                if (error != null) {
                    output.print(error)
                } else {
                    contact.setFieldValue("number", newNumber)
                    updated = true
                }
            }
            else -> {
                val prompt = when (fieldChoice) {
                    "name" -> if (contact is Person) "Enter name: " else "Enter organization name: "
                    "surname" -> "Enter surname: "
                    "address" -> "Enter address: "
                    else -> "Enter $fieldChoice: "
                }
                output.print(prompt)
                contact.setFieldValue(fieldChoice, readln())
                updated = true
            }
        }

        if (updated) {
            contact.updatedAt = LocalDateTime.now()
            Contacts.save()
            output.print("The record updated!")
        }
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            java.time.LocalDate.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}
