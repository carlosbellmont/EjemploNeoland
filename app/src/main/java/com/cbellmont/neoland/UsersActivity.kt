package com.cbellmont.neoland

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_campus_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UsersActivity : AppCompatActivity(){

    enum class UsersActivityType{
        CAMPUS,
        BOOTCAMP
    }

    private lateinit var viewModel : UsersActivityViewModel
    private val adapterLinear = UserAdapterLinear()
    private val adapterCompacted = UserAdapterCompacted()

    private var currentAdapter : Updatable = adapterLinear

    companion object {
        private const val CLAVE_ID = "Clave1"
        private const val CLAVE_TYPE = "Clave2"

        fun getIntent(context: Context, relatedId: Int, type: UsersActivityType) : Intent {
            return Intent(context, UsersActivity::class.java).apply {
                putExtra(CLAVE_ID, relatedId)
                putExtra(CLAVE_TYPE, type)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            UsersActivityViewModel::class.java
        )

        intent.extras?.let {
            val id = it.getInt(CLAVE_ID)
            when(it.getSerializable(CLAVE_TYPE) as UsersActivityType) {
                UsersActivityType.BOOTCAMP -> {
                    createLinearRecyclerView()
                    loadUsersByBootcamp(id)
                }
                UsersActivityType.CAMPUS -> {
                    createGirdRecyclerView()
                    loadUsersByCampus(id)
                }
            }
        }
    }

    private fun loadUsersByBootcamp(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
             viewModel.getUsersByBootcamp(id).observe(this@UsersActivity, { users ->
                 Log.w("CARLOS", "observe Size = ${users.size}")
                 // Si no quisieras usar el currentAdapter junto con la interfaz "Updatable", podrías eliminar las referencias a Updatable y quitar los comentarios de "adapterCompacted.updateData(users)"
                 // adapterCompacted.updateData(users)
                 currentAdapter.updateData(users)
             })
        }
    }

    private fun loadUsersByCampus(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUsersByCampus(id).observe(this@UsersActivity, { users ->
                Log.w("CARLOS", "observe Size = ${users.size}")
                // Si no quisieras usar el currentAdapter junto con la interfaz "Updatable", podrías eliminar las referencias a Updatable y quitar los comentarios de "adapterCompacted.updateData(users)"
                // adapterCompacted.updateData(users)
                currentAdapter.updateData(users)
            })
        }

    }

    private fun createGirdRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapterCompacted
        currentAdapter = adapterCompacted
    }

    private fun createLinearRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterLinear
        currentAdapter = adapterLinear

    }
}