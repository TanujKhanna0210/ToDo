package com.example.todo.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.EachTodoItemBinding
import com.example.todo.utils.model.ToDoData

class ToDoAdapter(private val list: MutableList<ToDoData>) :
RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>(){

    private var listener: ToDoAdapterClicksInterface?= null
    fun setListener(listener: ToDoAdapterClicksInterface){
        this.listener = listener
    }
    inner class ToDoViewHolder(val binding: EachTodoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.todoTask.text = this.task

                binding.deleteBtn.setOnClickListener {
                    listener?.onDeleteBtnClicked(this)
                }

                binding.editBtn.setOnClickListener {
                    listener?.onEditBtnClicked(this)
                }
            }
        }
    }

    interface ToDoAdapterClicksInterface{
        fun onDeleteBtnClicked(toDoData: ToDoData)
        fun onEditBtnClicked(toDoData: ToDoData)
    }
}