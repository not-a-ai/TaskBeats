package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private val titles:List<String>):
    RecyclerView.Adapter<TaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):TaskListViewHolder {

        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent,false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = titles[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}

class TaskListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val tvTaskTitle = view.findViewById<TextView>(R.id.tv_task_title)
    val tvTaskDescription = view.findViewById<TextView>(R.id.tv_task_description)

    fun bind(title: String) {
        tvTaskTitle.text = title
        tvTaskDescription.text = title
    }
}