package fr.isen.attia.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
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

        val foodObserver = Observer<ArrayList<FoodModel>>{ observed ->
            observed?.let {
                buildFoodList(it)
            } ?: run {
                buildFoodList(ArrayList<FoodModel>())
            }
        }
        FoodModel.currentFoods.observe(this, foodObserver)

        when(TYPE){
            ItemType.STARTER -> buildStartersPage()
            ItemType.MAIN -> buildMainCoursesPage()
            ItemType.DESSERT -> buildDessertsPage()
        }
        binding.categoryTitleText.text = title
        FoodModel.gatherFoodFromApi(this, title as String)
    }

    private fun buildFoodList(foods : ArrayList<FoodModel>?){
        val adapter = FoodsAdapter(foods as List<FoodModel>) { food ->
            Log.d("BUTTON", "Clicked food : "+ food.id)
            val intent = Intent(this, DetailActivity::class.java).apply{
                putExtra("DISH_ID", food.id)
                putExtra("TITLE", food.name)
            }
            startActivity(this, intent, null)
        }
        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(this)
    }

    private fun buildStartersPage(){
        title = getString(R.string.menu_btn_starters)
        binding.banner.setImageResource(R.drawable.starters)
    }

    private fun buildMainCoursesPage(){
        title = getString(R.string.menu_btn_main_courses)
        binding.banner.setImageResource(R.drawable.main_courses)
    }

    private fun buildDessertsPage(){
        title = getString(R.string.menu_btn_desserts)
        binding.banner.setImageResource(R.drawable.desserts)
    }
}