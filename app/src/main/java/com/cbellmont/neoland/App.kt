package com.cbellmont.neoland


import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cbellmont.neoland.datamodel.AppDatabase
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.campus.Campus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class App : Application() {

    companion object {
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
                            bootcampList.forEach{ bootcamp ->
                                campusListDb?.forEach { campus ->
                                    if(bootcamp.id != campus.id)
                                        bootcamp.campusId = campus.id
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
    }

}