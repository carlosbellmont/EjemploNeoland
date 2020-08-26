package com.cbellmont.neoland

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_campus_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentCampusList : Fragment() {

    companion object {
        fun getFragment(): FragmentCampusList {
            return FragmentCampusList()
        }
    }

    private lateinit var viewModel : CampusFragmentViewModel
    private val adapter = CampusAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_campus_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()

        activity?.let {
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it.application)).get(CampusFragmentViewModel::class.java)
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getCampus().value?.let { campus -> adapter.updateCampus(campus) }
                viewModel.getCampus().observe(this@FragmentCampusList, { campus -> adapter.updateCampus(campus) })
            }
        }
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}