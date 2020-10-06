package hu.prooktatas.boxdraw.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compositions")
class Composition(@NonNull val name: String) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0
}