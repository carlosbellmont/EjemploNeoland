package com.cbellmont.neoland

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cbellmont.neoland.datamodel.Preferencias
import com.cbellmont.neoland.datamodel.user.User
import com.cbellmont.neoland.sources.GetAllUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _mainActivityStatus: MutableLiveData<MainActivityStatus> by lazy { MutableLiveData<MainActivityStatus>() }
    val mainActivityStatus: LiveData<MainActivityStatus> get() = _mainActivityStatus

    enum class MainActivityStatus {
        WAITING,
        LOADING,
        FINISHED
    }

    fun isValidEmail(email: String) : Boolean {
        return email.isNotEmpty() && email.contains("@") && email.contains(".")
    }

    fun getErrorFromEmail(email: String) : Int? {
        return if (email.isEmpty()){
            R.string.main_email_fail_empty
        } else if (!email.contains("@"))  {
            R.string.main_email_fail_ar
        } else {
            R.string.main_email_fail_dot
        }
    }

    fun downloadStarted() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                _mainActivityStatus.value = MainActivityStatus.LOADING
            }
        }
    }

    fun downloadCancelled() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                _mainActivityStatus.value = MainActivityStatus.WAITING
            }
        }
    }

    fun downloadFinished(users: List<User>) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                App.getDatabase(getApplication()).UserDao().deleteAll()
                App.getDatabase(getApplication()).UserDao().insertAll(users)
            }
            withContext(Dispatchers.Main) {
                _mainActivityStatus.value = MainActivityStatus.FINISHED
            }
        }
    }

    fun downloadData() {
        viewModelScope.launch {
            GetAllUsers.send(this@MainActivityViewModel)
        }
    }

    fun cargarPreferencias() : String? {
        val sharedPref = getApplication<App>().getSharedPreferences(Preferencias.NAME, Preferencias.MODE)
        return sharedPref.getString(Preferencias.TAG_EMAIL_USUARIO, "")
    }

    fun guardarPreferencias(string : String) {
        val sharedPref = getApplication<App>().getSharedPreferences(Preferencias.NAME, Preferencias.MODE)
        with (sharedPref.edit()) {
            putString(Preferencias.TAG_EMAIL_USUARIO, string)
            commit()
        }
    }
}