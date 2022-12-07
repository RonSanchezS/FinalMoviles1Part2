package com.moviles.proyectofinaltrabajador2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moviles.proyectofinaltrabajador2.models.LoginResponse
import com.moviles.proyectofinaltrabajador2.models.WorkerLogin
import com.moviles.proyectofinaltrabajador2.repository.LoginRepository

class IniciarSesion : AppCompatActivity(), LoginRepository.onLoginListener {

    private lateinit var btnMail: EditText
    private lateinit var btnPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegistrarseActivity: Button

    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        setUpSharedPref()
        if (getSharedPreferences("MyPref", MODE_PRIVATE).contains("token")) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        Toast.makeText(this, "Bienvenido $token", Toast.LENGTH_SHORT).show()
        setUpListView();
        setUpListeners()
        //if there is a token in shared prefferences, go to homeActivity

    }

    private fun setUpSharedPref() {
        val pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode
        editor = pref.edit()
       
    }

    private fun setUpListeners() {
        btnRegistrarseActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val mail = btnMail.text.toString()
            val pass = btnPass.text.toString()
            if (mail.isEmpty() || pass.isEmpty()) {
                return@setOnClickListener
            }
            val worker = WorkerLogin(mail, pass, "test_notification_id")
            //   Toast.makeText(this, "Login $worker", Toast.LENGTH_SHORT).show()
            LoginRepository.login(worker, this, this);
        }
    }

    private fun setUpListView() {
        btnMail = findViewById(R.id.btnMail)
        btnPass = findViewById(R.id.btnPass)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegistrarseActivity = findViewById(R.id.btnRegistrarseActivity)
    }

    override fun onSuccess(body: LoginResponse) {
        //save body in shared preferences
        editor.putString("token", body.access_token)
        editor.commit()
        Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Token: ${body.access_token}", Toast.LENGTH_SHORT).show()
        //  val intent = Intent(this, HomeActivity::class.java)
        //startActivity(intent)
    }

    override fun onFailure(t: Throwable) {
        Toast.makeText(this, "Login fallido $t", Toast.LENGTH_SHORT).show()
    }
}