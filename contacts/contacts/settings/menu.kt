package contacts.settings

import contacts.builders.MenuBuilder
import contacts.repository.Menu

fun menu(block: MenuBuilder.() -> Unit) {
    Menu.menuEntries.addAll(MenuBuilder().apply(block).build())
}