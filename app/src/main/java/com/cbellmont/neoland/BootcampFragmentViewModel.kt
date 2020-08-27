package com.cbellmont.neoland

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cbellmont.neoland.datamodel.BootcampWithCampus
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.campus.Campus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BootcampFragmentViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun getBootcamp() : LiveData<List<BootcampWithCampus>> {
        return withContext(Dispatchers.IO) {
            return@withContext App.getDatabase(getApplication()).BootcampDao().getBootcampWithCampusLive()
        }
    }
}