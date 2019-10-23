package uk.co.bhojak.movielist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.utils.IntegerListConverter
import uk.co.bhojak.movielist.utils.KeywordListConverter
import uk.co.bhojak.movielist.utils.StringListConverter

@Database(entities = [(Movie::class)],
    version = 3, exportSchema = false)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class),
    (KeywordListConverter::class)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}