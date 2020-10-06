package hu.prooktatas.boxdraw.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.prooktatas.boxdraw.persistence.entity.Composition

@Dao
interface CompositionDao {

    @Insert
    fun insertComposition(composition: Composition): Long

    @Query("select * from compositions")
    fun fetchAll(): List<Composition>

    @Query("select count(*) from compositions where name = :refName")
    fun findByName(refName: String): Int

    @Query("select * from compositions where id = :refId")
    fun fetchById(refId: Long): Composition?

    @Query("select * from compositions where name = :refName")
    fun fetchByName(refName: String): Composition?


}