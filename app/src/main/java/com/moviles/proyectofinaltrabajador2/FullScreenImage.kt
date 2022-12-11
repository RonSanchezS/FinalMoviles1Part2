package com.moviles.proyectofinaltrabajador2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class FullScreenImage : AppCompatActivity() {
    private lateinit var img : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        val imagen = intent?.extras?.get("image")
        Toast.makeText(this, imagen.toString(), Toast.LENGTH_SHORT).show()
        img = findViewById(R.id.imgFullScreen)
        Glide.with(this).load(imagen).into(img)

    }
}