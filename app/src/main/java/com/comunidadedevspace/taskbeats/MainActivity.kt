package com.comunidadedevspace.taskbeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList = listOf<Task>(
            Task("title1", "Desc1"),
            Task("title2", "Desc2"),
            Task("title3", "Desc3"),
            Task("title4","Desc4"),
            Task("title5", "Desc5"),
            Task("title1", "Desc1"),
            Task("title2", "Desc2"),
            Task("title3", "Desc3"),
            Task("title4","Desc4"),
            Task("title5", "Desc5"),
        )

        //adapter
        val adapter: TaskListAdapter = TaskListAdapter(taskList, ::openTaskDetailView)
        //RecyclerView
        val rvTasks: RecyclerView = findViewById(R.id.rv_task_list)
        rvTasks.adapter = adapter
    }
    private fun openTaskDetailView(task: Task){
        val intent = Intent(this, TaskDetailActivity::class.java)
            .apply {
                putExtra(TaskDetailActivity.TASK_TITLE_EXTRA, task.title)
            }
        startActivity(intent)
    }
}