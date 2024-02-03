package com.comunidadedevspace.taskbeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList: RecyclerView = findViewById(R.id.rv_task_list)
        val list = listOf<String>("t1", "t2", "t3")
        val adapter = TaskListAdapter(list)

        taskList.adapter = adapter
    }
}