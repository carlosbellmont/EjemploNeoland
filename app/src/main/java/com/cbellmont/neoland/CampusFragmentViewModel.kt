package com.cbellmont.neoland

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cbellmont.neoland.datamodel.campus.Campus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CampusFragmentViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun getCampus() : LiveData<List<Campus>> {
        return withContext(Dispatchers.IO) {
            return@withContext App.getDatabase(getApplication()).CampusDao().getAllLive()
        }
    }
}