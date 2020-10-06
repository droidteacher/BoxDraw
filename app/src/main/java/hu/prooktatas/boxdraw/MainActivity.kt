package hu.prooktatas.boxdraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import hu.prooktatas.boxdraw.persistence.repository.BoxDrawRepository
import hu.prooktatas.boxdraw.persistence.repository.SaveCompositionResult
import hu.prooktatas.boxdraw.view.DrawingBoard

class MainActivity : AppCompatActivity() {

    private lateinit var drawingBoard: DrawingBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingBoard = findViewById(R.id.drawingBoard)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item_save -> {
                displaySaveDialog()
            }

            R.id.menu_item_load -> {
                displayLoadDialog()
            }
        }

        return true
    }

    private fun displaySaveDialog() {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("SAVE composition")
        val rootView = LayoutInflater.from(this).inflate(R.layout.load_or_save_dialog, null)
        val button = rootView.findViewById<Button>(R.id.btnDialogAction)
        button.setText(R.string.save)
        val etCompositionName = rootView.findViewById<EditText>(R.id.etFileName)

        button.setOnClickListener {
            val compName = etCompositionName.text.toString()
            if (compName.isNotEmpty()) {
                Thread {
                    val repo = BoxDrawRepository(this)
                    val message = when(repo.saveComposition(compName, drawingBoard.boxes)) {
                        SaveCompositionResult.SUCCESS -> {
                            "Your composition successfully saved"
                        }

                        SaveCompositionResult.EXISTING_NAME_ERROR -> {
                            "There is a composition already existing with this name"
                        }
                    }

                    runOnUiThread {
                        dialog.dismiss()
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }

                }.start()
            }

        }

        dialog.setView(rootView)
        dialog.show()
    }

    private fun displayLoadDialog() {

        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("LOAD composition")
        val rootView = LayoutInflater.from(this).inflate(R.layout.load_or_save_dialog, null)
        val button = rootView.findViewById<Button>(R.id.btnDialogAction)
        button.setText(R.string.load)
        val etCompositionName = rootView.findViewById<EditText>(R.id.etFileName)

        button.setOnClickListener {
            val compName = etCompositionName.text.toString()
            if (compName.isNotEmpty()) {
                Thread {
                    val repo = BoxDrawRepository(this)
                    val otherBoxes = repo.loadCompositionWithName(compName)

                    runOnUiThread {
                        drawingBoard.boxes.clear()
                        drawingBoard.boxes.addAll(otherBoxes)
                        drawingBoard.invalidate()

                        dialog.dismiss()
                    }

                }.start()
            }

        }

        dialog.setView(rootView)
        dialog.show()


    }
}

const val TAG = "KZs"