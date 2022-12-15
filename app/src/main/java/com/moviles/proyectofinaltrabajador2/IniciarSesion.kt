package com.moviles.proyectofinaltrabajador2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.moviles.proyectofinaltrabajador2.models.LoginResponse
import com.moviles.proyectofinaltrabajador2.models.WorkerLogin
import com.moviles.proyectofinaltrabajador2.repository.LoginRepository

class IniciarSesion : AppCompatActivity(), LoginRepository.onLoginListener {

    private lateinit var btnMail: EditText
    private lateinit var btnPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegistrarseActivity: Button

    private lateinit var tokenDeFirebase : String

    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
//delete token from shared preferences


        setUpSharedPref()
        if (getSharedPreferences("MyPref", MODE_PRIVATE).contains("token")) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        Toast.makeText(this, "Bienvenido $token", Toast.LENGTH_SHORT).show()
        setUpListView();
        setUpListeners()
        setUpFirebase()
        //if there is a token in shared prefferences, go to homeActivity

    }

    private fun setUpFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TOKEN", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            tokenDeFirebase = task.result as String

            // Log and toast
            Log.d("TOKEN", tokenDeFirebase)
            Toast.makeText(baseContext, tokenDeFirebase, Toast.LENGTH_SHORT).show()
            println("TOKEN DE FIREBASE ES: $tokenDeFirebase")
        })

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
            var worker = WorkerLogin(mail, pass, "")
            if(tokenDeFirebase.isEmpty()){
                Toast.makeText(this, "No se pudo obtener el token de firebase", Toast.LENGTH_SHORT).show()
                 worker = WorkerLogin(mail, pass, "test_id")
            }else{
                 worker = WorkerLogin(mail, pass, tokenDeFirebase)
            }
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
        editor.putString("token", body.access_token)
        editor.commit()
        editor.apply()
        println("TOKEN NUEVOOOOOOO ${body.access_token}")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    override fun onFailure(t: Throwable) {
        Toast.makeText(this, "Login fallido $t", Toast.LENGTH_SHORT).show()
    }
}