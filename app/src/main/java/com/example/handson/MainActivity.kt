package com.example.handson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.handson.databinding.ActivityMainBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportActionBar!!.hide()
        binding.btnLogin.setOnClickListener {
            var email = binding.txtEmail.text.toString()
            var password = binding.txtPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                binding.txtError.text = "Preencha todos os campos"
            } else {
                AuthUser(email, password)
            }
        }
    }

    private fun AuthUser(email: String, password: String) {
        val error = binding.txtError

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_LONG).show()
                LoggedUser()
            }

        }.addOnFailureListener {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> error.setText("Email ou senha inválidos")
                is FirebaseAuthInvalidUserException -> error.text = "Email ou senha inválidos"
                is FirebaseNetworkException -> error.text = "Verifique sua conexão com a internet"
                else -> error.text = "Error ao logar"
            }
        }
    }

    private fun LoggedUser() {
        val intent: Intent = Intent(this, RootActivity::class.java )
        startActivity(intent)
        finish()
    }
}