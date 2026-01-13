package contacts.builders

import contacts.models.Contact
import contacts.repository.Contacts
import contacts.utils.LogListener

@DslMarker
annotation class ContactsDsl

@ContactsDsl
class ContactsBuilder(
    internal val listener: LogListener
) {
    val contacts = mutableListOf<Contact>()

    fun person(block: PersonBuilder.() -> Unit): Contact {
        val person = PersonBuilder(listener).apply(block).build()
        contacts.add(person)
        return person
    }

    fun organisation(block: OrganisationBuilder.() -> Unit): Contact {
        val org = OrganisationBuilder(listener).apply(block).build()
        contacts.add(org)
        return org
    }

    fun build() {
        if (contacts.isNotEmpty()) {
            Contacts.contacts.addAll(contacts)
            Contacts.save()
            listener.onPhoneBookCreated()
        }
    }
}

fun contacts(listener: LogListener, block: ContactsBuilder.() -> Unit) {
    ContactsBuilder(listener).apply(block).build()
}