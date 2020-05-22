package com.chauss.tasktree.view

import com.chauss.tasktree.model.Task
import javafx.scene.layout.Pane
import tornadofx.*

class MainView : View() {
    var rootTask = Task("MainTitle")
    private val linesLayout = pane()
    private val buttonsLayout = pane()
    override val root = stackpane {
        add(linesLayout)
        add(buttonsLayout)
    }

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
            button(task.title) {
                setOnMouseDragged {
                    this@pane.relocate(it.sceneX - this.width / 2, it.sceneY - this.height / 2)
                }
            }

        }
        buttonsLayout.add(element)
        for ((i, child) in task.children.withIndex()) {
            val drewElement = drawCurrent(child, coordinateX + i * 100.0, coordinateY + 100.0)
            with(linesLayout) {
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