package hu.prooktatas.boxdraw.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.prooktatas.boxdraw.persistence.entity.Rectangle

@Dao
interface RectangleDao {

    @Insert
    fun insertRectangle(rectangle: Rectangle): Long

    @Query("select * from rectangles where composition_id = :refId order by id")
    fun fetchAllForComposition(refId: Long): List<Rectangle>

}