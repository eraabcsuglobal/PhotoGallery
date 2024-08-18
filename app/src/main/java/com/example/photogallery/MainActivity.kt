package com.example.photogallery

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {

    private lateinit var button: Button
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btn_pick_img)
        gridLayout = findViewById(R.id.gridLayout)

        val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            if (uris.isNotEmpty()) {
                addImagesToGrid(uris)
            }
        }

        button.setOnClickListener {
            // Launch the photo picker
            pickMultipleMedia.launch("image/*")
        }
    }
    private fun addImagesToGrid(uris: List<Uri>) {
        for (uri in uris) {
            val imageView = ImageView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 250 // Thumbnail width
                    height = 250 // Thumbnail height
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(8, 8, 8, 8)
                }
                setImageURI(uri)
                scaleType = ImageView.ScaleType.CENTER_CROP
                setOnClickListener {
                    showFullImage(uri)
                }
            }
            gridLayout.addView(imageView)
        }
    }

    private fun showFullImage(uri: Uri) {
        val dialog = android.app.Dialog(this)
        dialog.setContentView(R.layout.dialog_image)
        val fullImageView = dialog.findViewById<ImageView>(R.id.dialogImageView)
        fullImageView.setImageURI(uri)
        fullImageView.scaleType = ImageView.ScaleType.FIT_CENTER
        dialog.show()
    }
}
