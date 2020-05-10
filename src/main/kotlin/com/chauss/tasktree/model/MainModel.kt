package com.chauss.tasktree.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate

class Task(title: String, description: String? = null, deadline: LocalDate? = null) {
    private val titleProperty = SimpleStringProperty(this, "title", title)
    var title: String by titleProperty

    private val descriptionProperty = SimpleStringProperty(this, "description", description)
    var description: String? by descriptionProperty

    private val deadlineProperty = SimpleObjectProperty(deadline)
    var deadline: LocalDate? by deadlineProperty

    private val childrenProperty = SimpleListProperty(mutableListOf<Task>().asObservable())
    val children: ObservableList<Task> by childrenProperty
}