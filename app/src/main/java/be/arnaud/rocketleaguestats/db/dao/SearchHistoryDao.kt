package be.arnaud.rocketleaguestats.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import be.arnaud.rocketleaguestats.db.models.SearchHistory


@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM SearchHistory ORDER BY last_seen DESC")
    fun getAll(): List<SearchHistory>

    @Query("SELECT * FROM SearchHistory WHERE lower(username) LIKE :string ORDER BY last_seen DESC")
    fun getAllLike(string: String): List<SearchHistory>

    @Insert(onConflict = IGNORE)
    fun insert(searchHistory: SearchHistory): Long

    @Update
    fun update(searchHistory: SearchHistory)

    @Transaction
    fun upsert(searchHistory: SearchHistory) {
        val id = insert(searchHistory)
        if (id == -1L) {
            update(searchHistory)
        }
    }

    @Query("DELETE FROM SearchHistory")
    fun deleteAll()


    /*@Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)*/
}