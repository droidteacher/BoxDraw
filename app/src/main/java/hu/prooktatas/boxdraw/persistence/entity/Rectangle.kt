package hu.prooktatas.boxdraw.persistence.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.prooktatas.boxdraw.model.Box

@Entity(tableName = "rectangles")
data class Rectangle(
    @ColumnInfo(name = "start_x") val startX: Float,
    @ColumnInfo(name = "start_y") val startY: Float,
    @ColumnInfo(name = "end_x") val endX: Float,
    @ColumnInfo(name = "end_y") val endY: Float,
    @ColumnInfo(name = "composition_id") val compositionId: Long
) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0

    constructor(box: Box, compId: Long): this(box.start.x, box.start.y, box.end.x, box.end.y, compId)

}