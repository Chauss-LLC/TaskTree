package com.chauss.tasktree.app

import com.chauss.tasktree.view.MainView
import javafx.stage.Stage
import tornadofx.*

class MainApp : App(MainView::class){
    override fun start(stage: Stage) {
        with(stage){
            this.width = 640.0
            this.height = 1280.0
        }
        super.start(stage)
    }
}