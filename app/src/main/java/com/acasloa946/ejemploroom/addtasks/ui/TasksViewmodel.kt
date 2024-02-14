package com.acasloa946.ejemploroom.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acasloa946.ejemploroom.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewmodel @Inject constructor() : ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean> = _showDialog

    private val _taskText = MutableLiveData<String>()
    val taskText : LiveData<String> = _taskText

    fun dialogClose() {
        _showDialog.value = false
    }
    fun dialogOpen() {
        _showDialog.value = true
    }

    fun onTaskCreated() {
        dialogClose()
        Log.i("dam2",_taskText.value ?: "")
        _tasks.add(TaskModel(task = _taskText.value ?: ""))
        _taskText.value = ""
    }
    fun onChangeTextTask(text:String) {
        _taskText.value = text
    }
    //Los LiveData no van bien con los listados que se tienen que ir actualizando...
//Para solucionarlo, podemos utilizar un mutableStateListOf porque se lleva mejor con LazyColumn a la hora de refrescar la información en la vista...
    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun onItemRemove(taskModel: TaskModel) {
        //No podemos usar directamente _tasks.remove(taskModel) porque no es posible por el uso de let con copy para modificar el checkbox...
        //Para hacerlo correctamente, debemos previamente buscar la tarea en la lista por el id y después eliminarla
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }
    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)

        //Si se modifica directamente _tasks[index].selected = true no se recompone el item en el LazyColumn
        //Esto nos ha pasado ya en el proyecto BlackJack... ¿¿os acordáis?? :-(
        //Y es que la vista no se entera que debe recomponerse, aunque realmente si se ha modificado el valor en el item
        //Para solucionarlo y que se recomponga sin problemas en la vista, lo hacemos con un let...

        //El método let toma como parámetro el objeto y devuelve el resultado de la expresión lambda
        //En nuestro caso, el objeto que recibe let es de tipo TaskModel, que está en _tasks[index]
        //(sería el it de la exprexión lambda)
        //El método copy realiza una copia del objeto, pero modificando la propiedad selected a lo contrario
        //El truco está en que no se modifica solo la propiedad selected de tasks[index],
        //sino que se vuelve a reasignar para que la vista vea que se ha actualizado un item y se recomponga.
        _tasks[index] = _tasks[index].let { it.copy(selected = !it.selected) }
    }
}