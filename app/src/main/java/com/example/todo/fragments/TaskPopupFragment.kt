package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todo.R
import com.example.todo.databinding.FragmentTaskPopupBinding
import com.example.todo.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText

class TaskPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentTaskPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "TaskPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = TaskPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )

            binding.taskEt.setText(toDoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.nextBtn.setOnClickListener {
            val task = binding.taskEt.text.toString()

            if (task.isNotEmpty()) {
                if(toDoData == null) {
                    listener.onSaveTask(task, binding.taskEt)
                }else{
                    toDoData?.task = task
                    listener.onUpdateTask(toDoData!!, binding.taskEt)
                }
            } else {
                Toast.makeText(context, "Please enter a task!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(task: String, taskEt: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, taskEt: TextInputEditText)
    }
}