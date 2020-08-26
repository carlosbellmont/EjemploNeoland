package com.cbellmont.neoland

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.campus.Campus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BootcampFragmentViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun getBootcamp() : LiveData<List<Bootcamp>> {
        return withContext(Dispatchers.IO) {
            return@withContext App.getDatabase(getApplication()).BootcampDao().getAllLive()
        }
    }
}