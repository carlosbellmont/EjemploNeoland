package com.cbellmont.neoland

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cbellmont.neoland.datamodel.UserWithBootcamp
import com.cbellmont.neoland.datamodel.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersActivityViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun getUsersByBootcamp(bootcampId : Int) : LiveData<List<User>> {
        return withContext(Dispatchers.IO) {
            return@withContext App.getDatabase(getApplication()).UserDao().getByBootcampId(bootcampId)
        }
    }


    suspend fun getUsersByCampus(campusId : Int) : LiveData<List<UserWithBootcamp>> {
        return withContext(Dispatchers.IO) {
            return@withContext App.getDatabase(getApplication()).UserDao().getByCampusId(campusId)
        }
    }
}