package com.moviles.proyectofinaltrabajador2.EditarCapacidad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.identity.AccessControlProfileId
import android.util.Log
import android.widget.*
import com.moviles.proyectofinaltrabajador2.Items.Capacidad
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Categories
import com.moviles.proyectofinaltrabajador2.models.CategoriesPostResponse
import com.moviles.proyectofinaltrabajador2.models.Category
import com.moviles.proyectofinaltrabajador2.models.CategoryPost
import com.moviles.proyectofinaltrabajador2.repository.CategoriesRepository

class ActivityEditarCapacidades : AppCompatActivity(), CategoriesRepository.onCategoriesListener,
    CategoriesRepository.onCategoriesPostListener {

    private lateinit var spinner: Spinner
    private lateinit var txtDescrpicionCapacidad: EditText
    private lateinit var btnGuardar: Button

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_capacidades2)
        setUpListView()
        getValuesFromExtras()
    }

    private fun setUpListView() {
        spinner = findViewById(R.id.spinner)
        txtDescrpicionCapacidad = findViewById(R.id.txtDescripcionCapacidad)

        CategoriesRepository.getCategories(this)
        btnGuardar = findViewById(R.id.button)
        btnGuardar.setOnClickListener {
            val categoria = spinner.selectedItem.toString()
            val descripcion = txtDescrpicionCapacidad.text.toString()
            if(id!=0){
                //NO se define la opcion de actualizar en el documento
                //val capacidad = Capacidad(categoria, descripcion)
                //CategoriesRepository.addCapacidad(capacidad)
            }else{
                //add capacidad to a preexisting worker
                CategoriesRepository.postCategoryForWorker(CategoryPost(categoria, descripcion), this)
            }
            finish()


        }
    }

    private fun getValuesFromExtras() {
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
         //check if intent extra id is not null
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0)
            id = intent.extras?.get("id") as Int
        }


        Toast.makeText(
            this,
            "Nombre: $nombre, Descripcion: $descripcion, Id: $id",
            Toast.LENGTH_SHORT
        ).show()
        txtDescrpicionCapacidad.setText(descripcion)
    }

    override fun onSuccess(categories: List<Category>?) {
        Toast.makeText(this, "Categorias: ${categories?.size}", Toast.LENGTH_SHORT).show()
        val adapter = ArrayAdapter<Category>(
            this,
            android.R.layout.simple_spinner_item,
            categories as List<Category>
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }

    override fun onSuccess(body: CategoriesPostResponse?) {
        finish()
    }

    override fun onError(t: Throwable) {
        Log.e("Error", t.message.toString())
    }
}