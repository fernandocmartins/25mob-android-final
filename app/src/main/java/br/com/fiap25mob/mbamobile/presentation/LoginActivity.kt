package br.com.fiap25mob.mbamobile.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.databinding.ActivityLoginBinding
import br.com.fiap25mob.mbamobile.utils.USER_ID_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(binding.root)

        verifyUserLogged()
        setupSignIn()
        setupSignUp()
    }

    private fun fullScreen() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }

    private fun verifyUserLogged() {
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "User already logged")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupSignIn() {
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

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userUID = it.result.user?.uid
                        val userIdSP = getSharedPreferences(USER_ID_KEY, MODE_PRIVATE)

                        userIdSP.edit().putString(USER_ID_KEY, userUID).apply()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        message(getString(R.string.invalid_credentials_message))
                    }
                }
        }
    }

    private fun setupSignUp() {
        binding.btSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun message(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}