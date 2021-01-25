package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.attia.androidrestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.attia.androidrestaurant.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val TAG = "Category"

    enum class ItemType{
        STARTER, MAIN, DESSERT
    }

    companion object{
        lateinit var TYPE: ItemType
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TYPE = intent.getSerializableExtra(CATEGORY_NAME) as ItemType
        binding.categoryActivityPlacehoolder.text = TYPE.toString()

        when(TYPE){
            ItemType.STARTER -> buildStartersPage()
            ItemType.MAIN -> buildMainCoursesPage()
            ItemType.DESSERT -> buildDessertsPage()
        }
    }

    private fun buildStartersPage(){
        title = getString(R.string.menu_btn_starters)
    }

    private fun buildMainCoursesPage(){
        title = getString(R.string.menu_btn_main_courses)
    }

    private fun buildDessertsPage(){
        title = getString(R.string.menu_btn_desserts)
    }
}