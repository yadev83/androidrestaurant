package fr.isen.attia.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.attia.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.attia.androidrestaurant.food.FoodDetailActivity
import fr.isen.attia.androidrestaurant.food.FoodModel
import fr.isen.attia.androidrestaurant.food.FoodsAdapter

/**
 * @brief This class defines a category list.
 * It shows a list of dishes that are part of a same category.
 */
class CategoryActivity : BaseActivity() {
    /** Binding and view, we are starting to get use to it **/
    lateinit var binding: ActivityCategoryBinding
    private lateinit var loadingView: View

    /**
     * @brief This enum allows for a better management of categories instead of just using strings
     */
    enum class ItemType{ STARTER, MAIN, DESSERT }
    companion object{ lateinit var TYPE: ItemType }

    /**
     * @brief This method is called when the activity is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setting up the binding and content view
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creates the inflater to add the loading View
        //TODO Change this to a loader class
        val inflater = LayoutInflater.from(this)
        loadingView = inflater.inflate(R.layout.loading, binding.root, false)

        //Getting the category name from the intent
        TYPE = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as ItemType

        populateView()
        buildFoodList()
    }

    /**
     * @brief This function sets up an observer on the Food mutableLiveData and calls the API
     */
    private fun buildFoodList(){
        val foodObserver = Observer<ArrayList<FoodModel>>{ foods ->
            var dishes: ArrayList<FoodModel> = ArrayList()

            if(foods.isNotEmpty()){
                dishes = foods
            }
            val adapter = FoodsAdapter(dishes as List<FoodModel>) { food ->
                Log.d("BUTTON", "Clicked food : " + food.id)
                val intent = Intent(this, FoodDetailActivity::class.java).apply {
                    putExtra("DISH", food.serialize())
                }
                startActivity(this, intent, null)
            }

            binding.rvFood.adapter = adapter
            binding.rvFood.layoutManager = LinearLayoutManager(this)
        }
        FoodModel.currentFoods.observe(this, foodObserver)
        FoodModel.gatherFoodFromApi(this, title as String)
    }

    /**
     * @brief This populates the view with text and listeners
     */
    private fun populateView(){
        binding.refreshLayout.setOnRefreshListener(){
            FoodModel.clearCache(this)
            FoodModel.gatherFoodFromApi(this, title as String)
        }

        when(TYPE){
            ItemType.STARTER -> {
                title = getString(R.string.menu_btn_starters)
                binding.banner.setImageResource(R.drawable.starters)
            }
            ItemType.MAIN -> {
                title = getString(R.string.menu_btn_main_courses)
                binding.banner.setImageResource(R.drawable.main_courses)
            }
            ItemType.DESSERT -> {
                title = getString(R.string.menu_btn_desserts)
                binding.banner.setImageResource(R.drawable.desserts)
            }
        }
        binding.categoryTitleText.text = title
    }

    /**
     * @brief This method is enabling the animation for the loading state
     * Shall be put in a loader class at some point
     */
    fun enableLoadingAnimation(){
        if(loadingView.parent == null){
            Log.d("LOADING", "Displaying load animation")
            binding.root.addView(loadingView)
        }
    }

    /**
     * @brief This method disables the animation for the loading state
     * Shall be put in a loader class at some point
     */
    fun disableLoadingAnimation(){
        if(loadingView.parent != null){
            Log.d("LOADING", "Loading finished, removing animation view")
            binding.root.removeView(loadingView)
        }
        binding.refreshLayout.isRefreshing = false
    }
}