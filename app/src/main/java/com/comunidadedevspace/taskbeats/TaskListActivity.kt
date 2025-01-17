package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {

     private var taskList = arrayListOf(
         Task(0,"Correr", "Fazer o treino A."),
         Task(1,"Academia", "Malhar 1hora."),
         Task(2,"Mercado", "Comprar frutas."),
         Task(3,"Code", "Estudar Kotlin com o Roque."),
    )

    private lateinit var ctnContent: LinearLayout

    //adapter
    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter(::onListItemClicked)
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            if(taskAction.actionType == ActionType.DELETE.name){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }

                newList.remove(task)

                showMessage(ctnContent, "Item deleted ${task.title}")
                if(newList.size == 0){
                    ctnContent.visibility = View.VISIBLE
                }
                //atualiza a adapter
                adapter.submitList(newList)
                taskList = newList
            } else if(taskAction.actionType == ActionType.CREATE.name){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }

                newList.add(task)
                //atualiza a adapter
                adapter.submitList(newList)
                taskList = newList
            } else if(taskAction.actionType == ActionType.UPDATE.name){
                val tempEmptyList = arrayListOf<Task>()
                taskList.forEach{
                    if(it.id == task.id){
                        val newItem = Task(it.id, task.title, task.description)
                        tempEmptyList.add(newItem)
                    } else {
                        tempEmptyList.add(it)
                    }
                }
                showMessage(ctnContent, "Item updated ${task.title}")
                //atualiza a adapter
                adapter.submitList(tempEmptyList)
                taskList = tempEmptyList
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        ctnContent = findViewById(R.id.ctn_content)

        adapter.submitList(taskList)

        //RecyclerView
        val rvTasks: RecyclerView = findViewById(R.id.rv_task_list)
        rvTasks.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabadd)
        fab.setOnClickListener{
            openTaskListDetail(null)
        }
    }
    private fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
    private fun onListItemClicked(task: Task){
        openTaskListDetail(task)
    }
    private fun openTaskListDetail(task: Task? = null){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }
}



enum class ActionType {
    DELETE ,
    UPDATE ,
    CREATE ,

}

data class TaskAction(
    val task: Task,
    val actionType: String
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"