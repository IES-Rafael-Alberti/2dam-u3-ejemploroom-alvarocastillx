package com.acasloa946.ejemploroom.addtasks.ui.model

data class TaskModel(
    val id : Long = System.currentTimeMillis(),
    val task:String,
    val selected:Boolean = false
)