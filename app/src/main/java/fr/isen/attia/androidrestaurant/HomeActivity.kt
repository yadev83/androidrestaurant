package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var menuBtnStarters: Button
    lateinit var menuBtnMainCourses: Button
    lateinit var menuBtnDesserts: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`activity_home`)

        menuBtnStarters = findViewById(R.id.menu_btn_starters)
        menuBtnMainCourses = findViewById(R.id.menu_btn_main_courses)
        menuBtnDesserts = findViewById(R.id.menu_btn_desserts)

        menuBtnStarters.setOnClickListener(this)
        menuBtnMainCourses.setOnClickListener(this)
        menuBtnDesserts.setOnClickListener(this)
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.menu_btn_starters -> Toast.makeText(applicationContext, "Starters button clicked !", Toast.LENGTH_SHORT)
            R.id.menu_btn_main_courses -> Toast.makeText(applicationContext, "Main Courses button clicked !", Toast.LENGTH_SHORT)
            R.id.menu_btn_desserts -> Toast.makeText(applicationContext, "Desserts button clicked !", Toast.LENGTH_SHORT)
            else -> {
                Toast.makeText(applicationContext, "Clicked an unhandled thingy", Toast.LENGTH_SHORT)
            }
        }?.show()
    }
}