package contacts.service

import contacts.models.Contact
import contacts.repository.Contacts
import contacts.utils.Output

object SearchContact {
    fun handle(output: Output) {
        while (true) {
            output.print("Enter search query: ")
            val query = readln()
            val regex = try {
                query.toRegex(RegexOption.IGNORE_CASE)
            } catch (e: Exception) {
                output.print("Invalid regex!")
                continue
            }

            val results = Contacts.contacts.filter { contact ->
                contact.getSearchableString().contains(regex)
            }

            if (results.isEmpty()) {
                output.print("Found 0 results:")
                output.print("")
                output.print("")
                return
            }

            output.print("Found ${results.size} results:")
            results.forEachIndexed { index, contact ->
                output.print("${index + 1}. ${contact.fullName()}")
            }
            output.print("")

            if (handleSearchResults(output, results)) {
                output.print("")
                return
            }
        }
    }

    private fun handleSearchResults(output: Output, results: List<Contact>): Boolean {
        while (true) {
            output.print("[search] Enter action (number, back, again): ")
            val action = readln().lowercase()
            when {
                action == "back" -> return true
                action == "again" -> {
                    output.print("")
                    return false
                }
                action.toIntOrNull() != null -> {
                    val index = action.toInt() - 1
                    if (index in results.indices) {
                        val contact = results[index]
                        showContactInfo(output, contact)
                        if (handleRecordMenu(output, contact)) {
                           return true
                        }
                        output.print("")
                        // If record menu returns false (e.g. back to list), we should stay in search results?
                        // Actually the requirement usually is to go back to the list of results.
                        // Let's re-show the results.
                        output.print("Found ${results.size} results:")
                        results.forEachIndexed { idx, c ->
                            output.print("${idx + 1}. ${c.fullName()}")
                        }
                        output.print("")
                    }
                }
            }
        }
    }

    fun showContactInfo(output: Output, contact: Contact) {
        output.print(contact.formatInfo())
        output.print("")
    }

    fun handleRecordMenu(output: Output, contact: Contact): Boolean {
        while (true) {
            output.print("[record] Enter action (edit, delete, menu): ")
            val action = readln().lowercase()
            when (action) {
                "edit" -> {
                    // We need a way to edit THIS specific contact.
                    // EditContact.handle(output) normally lists everyone.
                    // We should probably refactor EditContact to have a method for editing a specific contact.
                    editSpecificContact(output, contact)
                    showContactInfo(output, contact)
                }
                "delete" -> {
                    Contacts.contacts.remove(contact)
                    Contacts.save()
                    output.print("The record removed!")
                    output.print("")
                    return true // Go back to main menu
                }
                "menu" -> {
                    output.print("")
                    return true
                }
                "back" -> return false // Go back to search results
                else -> {}
            }
        }
    }

    private fun editSpecificContact(output: Output, contact: Contact) {
        EditContact.editContact(output, contact)
    }
}
