package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

        //FOOD OBSERVER SETUP
        val foodObserver = Observer<ArrayList<FoodModel>> {
            binding.rvFood.adapter = FoodsAdapter(it)
            binding.rvFood.layoutManager = LinearLayoutManager(this)
        }

        FoodModel.currentFoods.observe(this, foodObserver)

        when(TYPE){
            ItemType.STARTER -> buildStartersPage()
            ItemType.MAIN -> buildMainCoursesPage()
            ItemType.DESSERT -> buildDessertsPage()
        }

        FoodModel.gatherFoodFromApi(this, title as String)
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