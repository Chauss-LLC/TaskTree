package com.chauss.tasktree.view

import com.chauss.tasktree.model.Task
import javafx.scene.layout.Pane
import tornadofx.*

class MainView : View() {
    var rootTask = Task("MainTitle")
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
            label(task.title)
            setOnMouseDragged {
                relocate(it.sceneX - this.width / 2, it.sceneY - this.height / 2)
            }

        }
        root.add(element)
        for ((i, child) in task.children.withIndex()) {
            drawCurrent(child, coordinateX + i * 100.0, coordinateY + 100.0)
        }
        return element
    }
}