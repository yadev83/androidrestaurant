package fr.isen.attia.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import fr.isen.attia.androidrestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.attia.androidrestaurant.databinding.ActivityCategoryBinding
import java.io.Serializable

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val TAG = "Category"
    private lateinit var loadingView: View

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

        val inflater = LayoutInflater.from(this)
        loadingView = inflater.inflate(R.layout.loading, binding.root, false)
        enableLoadingAnimation()

        TYPE = intent.getSerializableExtra(CATEGORY_NAME) as ItemType

        val foodObserver = Observer<ArrayList<FoodModel>>{ observed ->
            buildFoodList(observed)
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

    private fun buildFoodList(foods : ArrayList<FoodModel>){
        var dishes: ArrayList<FoodModel> = ArrayList<FoodModel>()

        if(foods.isNotEmpty()){
            dishes = foods
        }

        val adapter = FoodsAdapter(dishes as List<FoodModel>) { food ->
            Log.d("BUTTON", "Clicked food : "+ food.id)
            val intent = Intent(this, DetailActivity::class.java).apply{
                putExtra("DISH", food.Serialize())
            }
            startActivity(this, intent, null)
        }

        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(this)
    }

    fun enableLoadingAnimation(){
        Log.d("LOADING", "Displaying load animation")
        binding.root.addView(loadingView)
    }

    fun disableLoadingAnimation(){
        Log.d("LOADING", "Loading finished, removing animation view")
        binding.root.removeView(loadingView)
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