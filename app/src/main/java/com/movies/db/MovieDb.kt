package com.movies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movies.models.SearchMovieResponse

@Database(entities = [SearchMovieResponse.MovieItem::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile private var instance: MovieDb? = null

        fun getDatabase(context: Context): MovieDb =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it } }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MovieDb::class.java, "movies_database")
                .build()
    }
}