package br.com.fiap25mob.mbamobile.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {

            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if ((email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                message(getString(R.string.invalid_email_message))
                return@setOnClickListener
            }

            if (password.isBlank()) {
                message(getString(R.string.invalid_password_message))
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userUID = it.result.user?.uid
                    val userIdSP = getSharedPreferences("USERID", Context.MODE_PRIVATE)

                    userIdSP.edit().putString("USERID", userUID).apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    message(getString(R.string.invalid_credentials_message))
                }
            }
        }

        binding.btSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }

    private fun message(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}