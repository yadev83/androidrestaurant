package fr.isen.attia.androidrestaurant

import android.content.Intent
import android.os.Bundle
import fr.isen.attia.androidrestaurant.databinding.ActivityHomeBinding
import fr.isen.attia.androidrestaurant.parents.BaseActivity

/**
 * @brief The home activity is the default activity that launches when we start the app.
 * It has a title, a banner and 3 buttons to access the different categories of the restaurant.
 */
class HomeActivity : BaseActivity() {
    /** Binding... We kind of know him now **/
    private lateinit var binding: ActivityHomeBinding

    companion object{
        const val CATEGORY_NAME = "CATEGORY"
    }

    /**
     * @brief This method is called when the activity is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Sets up the binding and content view
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateView()
    }

    /**
     * @brief Populates the view with click listeners on menu buttons.
     */
    private fun populateView(){
        binding.menuBtnStarters.setOnClickListener{
            goToCategory(CategoryActivity.ItemType.STARTER)
        }
        binding.menuBtnMainCourses.setOnClickListener{
            goToCategory(CategoryActivity.ItemType.MAIN)
        }
        binding.menuBtnDesserts.setOnClickListener{
            goToCategory(CategoryActivity.ItemType.DESSERT)
        }
    }

    /**
     * @brief A method that goes to the selected category
     * @param type The type of item we want the list from
     */
    private fun goToCategory(type: CategoryActivity.ItemType){
        val intent = Intent(this, CategoryActivity::class.java).apply{
            putExtra(CATEGORY_NAME, type)
        }
        startActivity(intent)
    }
}