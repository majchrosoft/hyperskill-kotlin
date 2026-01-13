package contacts.builders

import contacts.enums.Prompt
import contacts.models.Contact
import contacts.utils.LogListener
import contacts.exceptions.ValidationException
import contacts.models.Person
import contacts.models.Organisation

import contacts.utils.readGender

@ContactsDsl
abstract class ContactBuilder(
    protected val listener: LogListener
) {
    var phone = ""

    fun phone() {
        listener.onBeforeInput(Prompt.PHONE)
        phone = readln()
    }

    abstract fun build(): Contact
}

@ContactsDsl
class PersonBuilder(listener: LogListener) : ContactBuilder(listener) {
    var firstName = ""
    var surName = ""
    var birthDate = ""
    var gender = ""

    fun firstName() {
        listener.output.print("Enter the name: ")
        firstName = readln()
    }

    fun surName() {
        listener.output.print("Enter the surname: ")
        surName = readln()
    }

    fun birthDate() {
        listener.output.print("Enter the birth date: ")
        val input = readln()
        if (input.isEmpty() || !isValidDate(input)) {
            listener.output.print("Bad birth date!")
            birthDate = ""
        } else {
            birthDate = input
        }
    }

    fun gender() {
        gender = readGender(listener.output)
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            java.time.LocalDate.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun build(): Contact {
        val person = Person(firstName, surName, birthDate, gender, phone)
        val errors = person.validate()
        if (errors.isNotEmpty()) {
            throw ValidationException(errors.joinToString("\n"))
        }
        listener.onRecordCreated(person)
        return person
    }
}

@ContactsDsl
class OrganisationBuilder(listener: LogListener) : ContactBuilder(listener) {
    var name = ""
    var address = ""

    fun name() {
        listener.output.print("Enter the organization name: ")
        name = readln()
    }

    fun address() {
        listener.output.print("Enter the address: ")
        address = readln()
    }

    override fun build(): Contact {
        val org = Organisation(name, address, phone)
        val errors = org.validate()
        if (errors.isNotEmpty()) {
            throw ValidationException(errors.joinToString("\n"))
        }
        listener.onRecordCreated(org)
        return org
    }
}
