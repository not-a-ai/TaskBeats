package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.app.ActivityManager.TaskDescription
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.io.FileDescriptor
import java.io.Serializable

class TaskDetailActivity : AppCompatActivity() {

    private var task: Task? = null
    private lateinit var btnDone: Button
    companion object{
        const val TASK_DETAIL_EXTRA = "task.extra.detail"

        fun start(context: Context, task: Task?): Intent{
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(TaskDetailActivity.TASK_DETAIL_EXTRA, task)
                }
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        // Recuperar a task
        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA)  as Task?

        val edtTitle = findViewById<EditText>(R.id.edt_task_title)
        val edtDsc = findViewById<EditText>(R.id.edt_task_description)
        btnDone = findViewById<Button>(R.id.btn_done)

        if(task != null){
            edtTitle.setText(task!!.title)
            edtDsc.setText(task!!.description)
        }
        btnDone.setOnClickListener {
            val title = edtTitle.text.toString()
            val dsc = edtDsc.text.toString()

            if(title.isNotEmpty() && dsc.isNotEmpty()){
                addNewTask(title, dsc)
            }else{
                showMessage(it, "Fields are required")
            }
        }
        // Recuperar campo do XML
        //tvTitle = findViewById<TextView>(R.id.tv_task_title_detail)
        //setar um novo texto na tela
        //tvTitle.text = task?.title ?: "Adicione umma tarefa"
    }

    private fun addNewTask (title: String, description: String) {
        val newTask = Task( 0, title, description)
        returnAction(newTask, ActionType.CREATE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {

                if(task != null) {
                   returnAction(task!!, ActionType.DELETE)

                } else {
                    showMessage(btnDone, "Item not found")
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun returnAction(task: Task, actionType: ActionType) {
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    private fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}