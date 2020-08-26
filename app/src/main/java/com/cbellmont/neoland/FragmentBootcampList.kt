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

class FragmentBootcampList : Fragment() {

    companion object {
        fun getFragment(): FragmentBootcampList {
            return FragmentBootcampList()
        }
    }

    private lateinit var viewModel :BootcampFragmentViewModel
    private val adapter = BootcampAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bootcamp_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()

        activity?.let {
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it.application)).get(BootcampFragmentViewModel::class.java)
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getBootcamp().value?.let { bootcamps -> adapter.updateBootcamps(bootcamps) }
                viewModel.getBootcamp().observe(this@FragmentBootcampList, { bootcamps -> adapter.updateBootcamps(bootcamps) })
            }
        }
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}