package com.cbellmont.neoland


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cbellmont.neoland.datamodel.AppDatabase
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.campus.Campus
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class App : Application() {

    companion object {
        val TAG: String = App::class.java.name


        private var db : AppDatabase? = null

        fun getDatabase(application: Application): AppDatabase {
            db?.let { return it }

            db = Room.databaseBuilder(application, AppDatabase::class.java, "main.db")
                .addCallback(getCallback())
                .build()
            return db as AppDatabase
        }

        private fun getCallback(): RoomDatabase.Callback {
            return object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.IO) {
                            val campusList = listOf(
                                Campus("Madrid", R.mipmap.neoland_campus_madrid),
                                Campus("Barcelona", R.mipmap.neoland_campus_barcelona),
                                Campus("Online", R.mipmap.neoland_campus_online)
                            )
                            App.db?.CampusDao()?.insertAll(campusList)
                            val campusListDb = App.db?.CampusDao()?.getAll()


                            val bootcampList = mutableListOf(
                                Bootcamp("Mobile", "Todo sobre Android e iOs"),
                                Bootcamp("Java", "Todo sobre Java"),
                                Bootcamp("FullStack", "Todo sobre todo"),
                                Bootcamp("Java", "Todo sobre Java"),
                                Bootcamp("Mobile", "Todo sobre Android e iOs"),
                                Bootcamp("Java", "Todo sobre Java"),
                                Bootcamp("FullStack", "Todo sobre todo")
                            )
                            campusListDb?.let {
                                bootcampList.forEach{ bootcamp ->
                                    bootcamp.fkCampusId = it[Random.nextInt(it.size)].campusId
                                }
                            }

                            App.db?.BootcampDao()?.insertAll(bootcampList)

                        }
                    }
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                }
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
        getDatabase(this)

        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(
            Stetho.defaultInspectorModulesProvider(this)
        )
        Stetho.initialize(initializerBuilder.build())

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                Log.d(TAG, "El token es =  $token")
            })

    }

}