package com.chauss.tasktree.view

import com.chauss.tasktree.model.Task
import tornadofx.*

class MainView : View() {
    var rootTask = Task("MainTitle")
    override val root = pane()

    init {
        val oneMore = Task("Second")
        oneMore.children.addAll(Task("1"), Task("2"))
        rootTask.children.addAll(Task("First"), oneMore, Task("Third"))
    }
}