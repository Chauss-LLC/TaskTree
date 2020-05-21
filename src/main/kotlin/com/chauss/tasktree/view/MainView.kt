package com.chauss.tasktree.view

import com.chauss.tasktree.model.*
import javafx.scene.layout.Pane
import tornadofx.*

class MainView : View() {
    var rootTask = Task("MainTitle")
    val model = TaskModel(rootTask)
    var form = borderpane(){
        isVisible = false
        center {
            form {
                fieldset("Новая задача") {
                    field("Краткое описание") {
                        textfield(model.title)
                    }
                    fieldset("Дата") {
                        datepicker(model.deadline)
                    }
                    fieldset("Подробное описание") {
                        textfield(model.description)
                    }
                }
            }
        }
        bottom{
            hbox {
                this.add(button("save") {
                    hboxConstraints {
                        marginRight = 200.0
                    }
                    action {
                        save()
                    }
                })
                this.add(button("close"){
                    action{
                        this@borderpane.isVisible = false
                    }
                })
            }
        }
    }
    private fun save(){
        model.commit()
        val task = model.item
        rootTask = task
        println("SAVING ${rootTask.title} / ${rootTask.deadline} / ${rootTask.description}")
    }

    override val root = pane()

    init {
        val oneMore = Task("Second")
        oneMore.children.addAll(Task("1"), Task("2"))
        rootTask.children.addAll(Task("First"), oneMore, Task("Third"))
        drawCurrent(rootTask, 0.0, 0.0)
    }

    private fun drawCurrent(task: Task, coordinateX: Double, coordinateY: Double): Pane {
        val element = pane {
            layoutX = coordinateX
            layoutY = coordinateY
            button{
                this.text().bind(task.titleProperty)
                action{
                    form.layoutX = 350.0
                    form.layoutY = 0.0
                    form.isVisible = true
                    add(form)
                }
            }
            setOnMouseDragged {
                    relocate(it.sceneX - this.width / 2, it.sceneY - this.height / 2)
            }

        }
        root.add(element)
        for ((i, child) in task.children.withIndex()) {
            val drewElement = drawCurrent(child, coordinateX + i * 100.0, coordinateY + 100.0)
            with(root) {
                line(coordinateX, coordinateY, coordinateX + i * 100, coordinateY + 100) {
                    startXProperty().bind(element.layoutXProperty())
                    startYProperty().bind(element.layoutYProperty())
                    endXProperty().bind(drewElement.layoutXProperty())
                    endYProperty().bind(drewElement.layoutYProperty())
                }
            }
        }
        return element
    }

}