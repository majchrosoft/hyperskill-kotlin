package contacts.events

import contacts.models.Contact

sealed interface ContactsEvent {
    data class RecordCreated(val person: Contact) : ContactsEvent
    object PhoneBookCreated : ContactsEvent
    data class Prompt(val message: String) : ContactsEvent
}
