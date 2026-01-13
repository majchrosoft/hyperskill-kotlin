package contacts.models

import contacts.utils.Output

data class MenuEntry(val name: String, val action: (Output) -> Unit)
