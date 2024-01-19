package com.acasloa946.ejemploroom.addtasks.ui.model

data class TaskModel(
    val id : Int = System.currentTimeMillis().hashCode(),
    val task:String,
    val selected:Boolean = false
)