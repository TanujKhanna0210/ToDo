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
import com.google.android.material.textfield.TextInputEditText

class TaskPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentTaskPopupBinding
    private lateinit var listener: DialogNextBtnClickListener

    fun setListener(listener: DialogNextBtnClickListener){
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents()
    }

    private fun registerEvents() {
        binding.nextBtn.setOnClickListener {
            val task = binding.taskEt.text.toString()

            if (task.isNotEmpty()) {
                listener.onSaveTask(task, binding.taskEt)
            } else {
                Toast.makeText(context, "Please enter a task!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }
    interface DialogNextBtnClickListener{
        fun onSaveTask(task: String, taskEt: TextInputEditText)
    }
}