package com.cbellmont.neoland

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_campus_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentBootcampList : Fragment() {

    companion object {
        fun getFragment(): FragmentBootcampList {
            return FragmentBootcampList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bootcamp_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            activity?.application?.let { application ->
                val lista = App.getDatabase(application).BootcampDao().getAll()
                withContext(Dispatchers.Main){
                    lista.forEach {bootcamp ->
                        textView.append(bootcamp.name)
                    }
                }
            }

        }
    }
}