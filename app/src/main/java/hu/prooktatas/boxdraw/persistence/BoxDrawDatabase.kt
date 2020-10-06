package hu.prooktatas.boxdraw.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.prooktatas.boxdraw.persistence.dao.CompositionDao
import hu.prooktatas.boxdraw.persistence.dao.RectangleDao
import hu.prooktatas.boxdraw.persistence.entity.Composition
import hu.prooktatas.boxdraw.persistence.entity.Rectangle

@Database(entities = [Composition::class, Rectangle::class], version = 1)
abstract class BoxDrawDatabase: RoomDatabase() {

    abstract fun rectangleDao(): RectangleDao
    abstract fun compositionDao(): CompositionDao

    companion object {
        private var dbInstance: BoxDrawDatabase? = null

        internal fun getDatabase(context: Context): BoxDrawDatabase? {
            if (dbInstance == null) {
                synchronized(BoxDrawDatabase::class.java) {
                    if (dbInstance == null) {
                        dbInstance = Room.databaseBuilder(context.applicationContext, BoxDrawDatabase::class.java, "box_draw").build()
                    }
                }
            }

            return dbInstance
        }
    }
}