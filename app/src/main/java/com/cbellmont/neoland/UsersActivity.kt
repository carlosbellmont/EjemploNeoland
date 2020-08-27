package com.cbellmont.neoland

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
    private val adapter = UserAdapter()


    companion object {
        private const val CLAVE_ID = "Clave1"
        private const val CLAVE_TYPE = "Clave2"

        fun getIntent(context: Context, relatedId : Int, type : UsersActivityType) : Intent {
            return Intent(context, UsersActivity::class.java).apply {
                putExtra(CLAVE_ID, relatedId)
                putExtra(CLAVE_TYPE, type)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(UsersActivityViewModel::class.java)
        createRecyclerView()

        intent.extras?.let {
            Log.w("CARLOS", "intent.extras")

            val id = it.getInt(CLAVE_ID)
            when(it.getSerializable(CLAVE_TYPE) as UsersActivityType) {
                UsersActivityType.BOOTCAMP -> loadUsersByBootcamp(id)
                UsersActivityType.CAMPUS -> loadUsersByCampus(id)
            }
        }
    }

    private fun loadUsersByBootcamp(id: Int) {
        Log.w("CARLOS", "loadUsersByBootcamp")

        CoroutineScope(Dispatchers.Main).launch {
             viewModel.getUsersByBootcamp(id).observe(this@UsersActivity, { users ->
                Log.w("CARLOS", "observe Size = ${users.size}")
                adapter.updateData(users)
            })
        }
    }

    private fun loadUsersByCampus(id: Int) {
        Log.w("CARLOS", "loadUsersByCampus")
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUsersByCampus(id).observe(this@UsersActivity, { users ->
                Log.w("CARLOS", "observe Size = ${users.size}")
                adapter.updateData(users)
            })
        }

    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}