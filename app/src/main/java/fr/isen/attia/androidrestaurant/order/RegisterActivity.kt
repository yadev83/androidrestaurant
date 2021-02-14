package fr.isen.attia.androidrestaurant.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var user: UserAccount

    private lateinit var validateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.register)

        //Try to log in from cache

        populateActivity()
    }

    private fun populateActivity(){
        binding.regFormValidate.setOnClickListener{
            user = UserAccount()
            user.email = binding.regFormEmail.text.toString()
            user.password = binding.regFormPassword.text.toString()
            user.firstName = binding.regFormFirstname.text.toString()
            user.lastName = binding.regFormLastname.text.toString()
        }

        binding.loginButton.setOnClickListener{
            finish()
            startActivity(Intent(this, LoginActivity::class.java), null)
        }
    }
}