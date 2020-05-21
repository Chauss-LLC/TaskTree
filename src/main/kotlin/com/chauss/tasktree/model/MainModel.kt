package com.chauss.tasktree.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate
import java.util.*

class Task(title: String, description: String? = null, deadline: LocalDate? = null) : Observable(){
    val titleProperty = SimpleStringProperty(this, "title", title)
    var title: String by titleProperty

    private val descriptionProperty = SimpleStringProperty(this, "description", description)
    var description: String? by descriptionProperty

    private val deadlineProperty = SimpleObjectProperty(deadline)
    var deadline: LocalDate? by deadlineProperty

    private val childrenProperty = SimpleListProperty(mutableListOf<Task>().asObservable())
    val children: ObservableList<Task> by childrenProperty
}
class TaskModel(task: Task) : ItemViewModel<Task>(task){
    val title = bind(Task::title)
    val description = bind(Task::description)
    val deadline = bind(Task::deadline)
}