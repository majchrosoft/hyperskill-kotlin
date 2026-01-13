package contacts.repository

import contacts.models.Contact
import java.io.*

object Contacts {
    var contacts = mutableListOf<Contact>()
    var fileName: String? = null

    fun save() {
        val file = fileName ?: return
        try {
            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeObject(contacts)
            }
        } catch (e: IOException) {
            // Silently fail or log? The task doesn't specify.
        }
    }

    fun load() {
        val file = fileName ?: return
        val f = File(file)
        if (!f.exists()) return
        try {
            ObjectInputStream(FileInputStream(f)).use {
                @Suppress("UNCHECKED_CAST")
                contacts = it.readObject() as MutableList<Contact>
            }
        } catch (e: Exception) {
            // Silently fail or log?
        }
    }
}
