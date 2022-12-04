package com.moviles.proyectofinaltrabajador2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.moviles.proyectofinaltrabajador2.models.Response
import com.moviles.proyectofinaltrabajador2.models.Worker
import com.moviles.proyectofinaltrabajador2.repository.LoginRepository

class MainActivity : AppCompatActivity(), LoginRepository.onRegisterListener {

    private lateinit var txtMail : EditText
    private lateinit var txtPass : EditText
    private lateinit var txtName : EditText
    private lateinit var txtPhone : EditText

    private lateinit var btnRegister : Button
    private lateinit var btnVolver : Button
    private lateinit var btnProfilePic : Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListView()
        setUpListeners()
    }

    private fun setUpListeners() {
        btnRegister.setOnClickListener {
            val mail = txtMail.text.toString()
            val pass = txtPass.text.toString()
            val name = txtName.text.toString()
            val phone = txtPhone.text.toString()

            //if any of the fields is empty, return
            if (mail.isEmpty() || pass.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = Worker(name, mail, pass, phone)
            Toast.makeText(this, "Registrando usuario $user", Toast.LENGTH_SHORT).show()
           LoginRepository.register(user,this,this)

        }
    }

    private fun setUpListView() {
        txtMail = findViewById(R.id.txtMail)
        txtPass = findViewById(R.id.txtPass)
        txtName = findViewById(R.id.txtName)
        txtPhone = findViewById(R.id.txtPhone)

        btnRegister = findViewById(R.id.btnRegistrarse)
        btnVolver = findViewById(R.id.btnVolver)
        btnProfilePic = findViewById(R.id.btnProfilePic)
    }

    override fun onSuccess(body: Response) {
        Toast.makeText(this, body.res, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onFailure(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }
}