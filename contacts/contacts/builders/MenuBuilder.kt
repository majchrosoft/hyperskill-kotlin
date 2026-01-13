package contacts.builders

import contacts.controller.*
import contacts.models.MenuEntry

@ContactsDsl
class MenuBuilder {
    private val entries = mutableListOf<MenuEntry>()

    fun add() {
        entries.add(MenuEntry("add", ::addAction))
    }

    fun list() {
        entries.add(MenuEntry("list", ::listAction))
    }

    fun search() {
        entries.add(MenuEntry("search", ::searchAction))
    }

    fun count() {
        entries.add(MenuEntry("count", ::countAction))
    }

    fun exit() {
        entries.add(MenuEntry("exit", ::exitAction))
    }

    fun all() {
        add()
        list()
        search()
        count()
        exit()
    }

    fun build(): List<MenuEntry> = entries
}
