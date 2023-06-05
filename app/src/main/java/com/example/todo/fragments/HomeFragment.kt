package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(), TaskPopupFragment.DialogNextBtnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var databaseReference: DatabaseReference
    private lateinit var popupFragment: TaskPopupFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
    }

    private fun registerEvents() {
        binding.addBtn.setOnClickListener {
            popupFragment = TaskPopupFragment()
            popupFragment.setListener(this)
            popupFragment.show(
                childFragmentManager,
                "TaskPopupFragment"
            )
        }
    }

    override fun onSaveTask(task: String, taskEt: TextInputEditText) {
        databaseReference.push().setValue(task).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(context,"Task saved successfully!", Toast.LENGTH_SHORT).show()
                taskEt.text = null
            }else{
                Toast.makeText(context,it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popupFragment.dismiss()
        }
    }
}