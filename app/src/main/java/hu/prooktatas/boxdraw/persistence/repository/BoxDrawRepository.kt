package hu.prooktatas.boxdraw.persistence.repository

import android.content.Context
import android.graphics.PointF
import android.util.Log
import hu.prooktatas.boxdraw.TAG
import hu.prooktatas.boxdraw.model.Box
import hu.prooktatas.boxdraw.persistence.BoxDrawDatabase
import hu.prooktatas.boxdraw.persistence.entity.Composition
import hu.prooktatas.boxdraw.persistence.entity.Rectangle

class BoxDrawRepository(context: Context) {

    private val database = BoxDrawDatabase.getDatabase(context)
    private val rectDao = database?.rectangleDao()
    private val compDao = database?.compositionDao()

    fun saveComposition(name: String, boxes: List<Box>): SaveCompositionResult {
        Log.d(TAG, "saveComposition() called...")

        if (compDao!!.findByName(name) > 0) {
            return SaveCompositionResult.EXISTING_NAME_ERROR
        }

        val compositionId = compDao.insertComposition(Composition(name))

        boxes.forEach {
            val rect = Rectangle(it, compositionId)
            val rectangleId = rectDao!!.insertRectangle(rect)
            Log.d(TAG, "New rectangle: $rectangleId")
        }

        return SaveCompositionResult.SUCCESS
    }

    fun loadCompositionWithName(name: String): List<Box> {
        Log.d(TAG, "loadCompositionWithName() called...")

        val boxList = mutableListOf<Box>()
        compDao!!.fetchByName(name)?.let { aComposition ->

            rectDao!!.fetchAllForComposition(aComposition.id).forEach { aRect ->
                boxList.add(Box(PointF(aRect.startX, aRect.startY), PointF(aRect.endX, aRect.endY)))
            }
        }

        return boxList
    }
}

enum class SaveCompositionResult {
    SUCCESS, EXISTING_NAME_ERROR
}