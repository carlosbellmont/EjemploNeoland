package com.cbellmont.neoland

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.name
        const val EXTRA_1 : String = "Extra1"
        const val EXTRA_2 : String = "Extra2"

    }
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, AndroidViewModelFactory(application)).get(
            MainActivityViewModel::class.java
        )

        intent.extras?.let {
            Log.d(TAG, "He recibido del intent que contiene: ${it.getString(EXTRA_1)} ${it.getString(EXTRA_2)}")
        }

        ivLogo.setImageResource(R.mipmap.logo_neoland)
        ivTexto.setImageResource(R.mipmap.texto_neoland)

        viewModel.cargarPreferencias()?.let {
            editTextTextEmailAddress.setText(it)
            if (it.isNotEmpty())
                cbRecordar.isChecked = true
        }

        viewModel.mainActivityStatus.observe(this, {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Main) {
                    viewModel.mainActivityStatus.value?.let {
                        when (it) {
                            MainActivityViewModel.MainActivityStatus.FINISHED -> {
                                hideLoading()
                                startActivity(MenuActivity.getIntent(this@MainActivity))
                            }
                            MainActivityViewModel.MainActivityStatus.WAITING -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Ha habido un error en la descarga",
                                    Toast.LENGTH_LONG
                                ).show()
                                hideLoading()
                            }
                            MainActivityViewModel.MainActivityStatus.LOADING -> {
                                showLoading()
                            }
                        }
                    }
                }
            }
        })

        cbRecordar.setOnClickListener {
            if (!cbRecordar.isChecked){
                viewModel.guardarPreferencias("")
            }
        }

        bLogin.setOnClickListener {
            if (viewModel.isValidEmail(editTextTextEmailAddress.text.toString())) {
                CoroutineScope(Dispatchers.IO).launch{
                    viewModel.downloadData()
                    viewModel.guardarPreferencias(editTextTextEmailAddress.text.toString())
                }
            } else {
                Toast.makeText(
                    this,
                    viewModel.getErrorFromEmail(editTextTextEmailAddress.text.toString())
                        ?.let { it1 ->
                            getString(it1)
                        }, Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showLoading(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        progressBar.visibility = View.GONE
    }
}