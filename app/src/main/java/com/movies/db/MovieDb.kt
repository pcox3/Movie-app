package com.movies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movies.models.MovieDetailsResponse
import com.movies.models.MovieItem

@Database(entities = [MovieItem::class,
    MovieDetailsResponse::class], version = 1)
abstract class MovieDb : RoomDatabase() {

    abstract fun daoClass(): MovieDao

    companion object {
        @Volatile private var instance: MovieDb? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieDb? {
            if (instance == null) {
                synchronized(Database::class.java) {
                    if (instance == null) {
                        instance = buildDatabase(context)
                    }
                }
            }
            return instance
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context,
                MovieDb::class.java, "movies_database")
                .build()
    }


}