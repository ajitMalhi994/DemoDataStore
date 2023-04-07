package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data_store.DataStore
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.extensions.hideKeyboard
import com.example.myapplication.extensions.toast
import com.example.myapplication.view_model.MainViewModel
import com.example.myapplication.view_model.MyViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var bindingObj: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingObj = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingObj.root
        setContentView(view)

        viewModel = ViewModelProvider(this, MyViewModelFactory(DataStore(this)))[MainViewModel::class.java]

        viewModel.retrieveSavedUser(this)
        observerTheResult()
        saveUser()
    }

    private fun saveUser(){

        bindingObj.apply {

            btnSave.setOnClickListener {
                when{
                    edtName.text.isNullOrEmpty() -> toast("Please enter a name.")
                    edtAge.text.isNullOrEmpty() -> toast("Please enter age.")
                    else -> {
                        viewModel.saveUser(edtName.text.toString(), edtAge.text.toString().toInt())
                        hideKeyboard()
                        edtAge.text = null
                        edtName.text = null
                    }
                }
            }
        }
    }

    private fun observerTheResult(){

        viewModel.apply {

            name.observe(this@MainActivity){
                it.let {
                    bindingObj.tvName.text = "Name: $it"
                }
            }

            age.observe(this@MainActivity){
                it.let {
                    bindingObj.tvAge.text = "Age: $it"
                }
            }
        }
    }
}
