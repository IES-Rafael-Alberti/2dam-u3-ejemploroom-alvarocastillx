package com.acasloa946.ejemploroom.addtasks.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("Select * from TaskEntity")
    //Básicamente nos vamos a enganchar a través de Flow, va a retornar un Flow con una lista de TaskEntity,
    //y las librerías de Flow se encargarán de avisar cuando algún dato de la Entidad se haya agregado, actualizado o eliminado
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(item:TaskEntity)
}