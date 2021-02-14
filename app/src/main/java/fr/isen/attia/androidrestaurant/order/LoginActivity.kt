package fr.isen.attia.androidrestaurant.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.login)
        populateActivity()
    }

    private fun populateActivity(){
        binding.loginFormValidate.setOnClickListener{
            user = UserAccount()
            user.email = binding.loginFormEmail.text.toString()
            user.password = binding.loginFormPassword.text.toString()
            if(user.validate()){
                user.loginRequest(this)
                val userObserver = Observer<UserAccount.RequestResult>{user ->
                    val serUser = UserAccount.get()
                    Log.d("LOGIN", serUser?.id.toString())
                    serUser?.let{
                        finish()
                        startActivity(Intent(this, OrderActivity::class.java), null)
                    }
                }
                UserAccount.currentUser.observe(this, userObserver)
            }
        }

        binding.registerButton.setOnClickListener{
            finish()
            startActivity(Intent(this, RegisterActivity::class.java), null)
        }
    }
}