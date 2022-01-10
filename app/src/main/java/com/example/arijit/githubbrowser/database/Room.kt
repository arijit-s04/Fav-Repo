package com.example.arijit.githubbrowser.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface GithubRepoDao {
    @Query("select * from Fav_Repo")
    fun getAllFavRepo(): LiveData<List<DatabaseRepoObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoFavRepo(item: DatabaseRepoObject)

    @Delete
    fun deleteFavRepoBye(item: DatabaseRepoObject)

}

@Database(entities = [DatabaseRepoObject::class], version = 1)
abstract class GithubDatabase: RoomDatabase() {
    abstract val databaseDao: GithubRepoDao
}

private lateinit var INSTANCE: GithubDatabase

fun getDatabase(context: Context): GithubDatabase {
    synchronized(GithubDatabase::class.java) {
        if( !::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                GithubDatabase::class.java,
                "repos").build()
        }
    }
    return INSTANCE
}
