package fr.isen.attia.androidrestaurant.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        populateActivity()
    }

    private fun populateActivity(){
        validateBtn = binding.regFormValidate
        validateBtn.setOnClickListener{
            user = UserAccount(binding.regFormEmail.text.toString(),
                binding.regFormPassword.text.toString(),
                binding.regFormFirstname.text.toString(),
                binding.regFormLastname.text.toString())
            user.registerRequest(this)
        }
    }
}