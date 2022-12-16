package be.arnaud.rocketleaguestats.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.arnaud.rocketleaguestats.db.dao.SearchHistoryDao
import be.arnaud.rocketleaguestats.db.models.SearchHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [SearchHistory::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private var instance: AppDatabase? = null

        fun <T> get(context: Context, call: (appDatabase: AppDatabase) -> T, callback: ((response: T) -> Unit)?){
            if (instance == null) {
                instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "rocket_league_stats"
                    ).fallbackToDestructiveMigration()
                    .build()
            }
            CoroutineScope(Dispatchers.IO).launch {
                val response = call(instance!!)
                withContext(Dispatchers.Main) {
                    if (callback != null) {
                        callback(response)
                    }
                }
            }
        }

        fun <T> get(context: Context, call: (appDatabase: AppDatabase) -> T){
            get(context, call, null)
        }
    }
}