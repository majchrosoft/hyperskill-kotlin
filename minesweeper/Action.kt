package minesweeper

enum class Action(val action: String) {
    MARK_MINE("mine"),
    EXPLORE_CELL("free");
}

fun findActionByName(name: String): Action {
    for (action in Action.entries) {
        if (action.action == name) {
            return action;
        }
    }

    throw RuntimeException()
}