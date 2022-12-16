package be.arnaud.rocketleaguestats.db

import android.content.Context
import be.arnaud.rocketleaguestats.db.models.SearchHistory

object DbUtils {

    fun getAllSearchHistories(context: Context, callback: (response: List<SearchHistory>) -> Unit) {
        AppDatabase.get(context, call = { appDatabase ->
            appDatabase.searchHistoryDao().getAll()
        }, callback)
    }

    fun getAllSearchHistoriesLike(context: Context, string: String, callback: (response: List<SearchHistory>) -> Unit) {
        AppDatabase.get(context, call = { appDatabase ->
            appDatabase.searchHistoryDao().getAllLike("$string%")
        }, callback)
    }

    fun upsertSearchHistory(context: Context, searchHistory: SearchHistory) {
        AppDatabase.get(context, call = { appDatabase ->
            appDatabase.searchHistoryDao().upsert(searchHistory)
        })
    }
}