package com.chauss.tasktree.view
import com.chauss.tasktree.model.Task
import com.chauss.tasktree.model.TaskModel
import javafx.scene.layout.Pane
import javafx.scene.paint.Paint
import tornadofx.*

class MainView : View() {
    var rootTask = Task("MainTitle")
    val model = TaskModel(Task(""))
    private val linesLayout = pane()
    private val buttonsLayout = pane()
    override val root = stackpane {
        add(linesLayout)
        add(buttonsLayout)
    }
    var form = borderpane {                            //форма заполнения описания и дедлайна задачи
        isVisible = false
        center {
            form {
                style{
                    backgroundColor += Paint.valueOf("moccasin")//мокасин
                }
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
                    enableWhen(model.dirty)
                    hboxConstraints {
                        marginRight = 200.0
                    }
                    action {
                        save()
                    }
                })
                this.add(button("close") {
                    action {
                        this@borderpane.removeFromParent() // удаляем форму
                    }
                })
            }
        }
    }
    // сохраняем введённые данные из формы
    private fun save() {
        model.commit()
        println("${model.title} / ${model.deadline} / ${model.description}")
    }
    //отрисовываем все задачи
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
            button {
                this.text().bind(task.titleProperty)//привязываем название кнопки к тасктайтлу
                setOnMouseClicked {
                    if (!it.isStillSincePress) return@setOnMouseClicked //пока клавиша нажата не ребиндим(происходит драгндроп)
                    // ребиндим на выбранный таск
                    model.rebind {
                        this.item = task
                    }
                    form.layoutX = this@pane.layoutX
                    form.layoutY = this@pane.layoutY
                    form.isVisible = true
                    root.add(form)
                }
                setOnMouseDragged {
                    parent.relocate(it.sceneX - this.width / 2, it.sceneY - this.height / 2)
                }
            }

        }
        root.add(element)
        // рисуем линии от родителя до детей
        buttonsLayout.add(element)
        for ((i, child) in task.children.withIndex()) {
            val drewElement = drawCurrent(child, coordinateX + i * 100.0, coordinateY + 100.0)
            with(linesLayout) {
                line(coordinateX, coordinateY, coordinateX + i * 100, coordinateY + 100) {
                    stroke = Paint.valueOf("#00008b")
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