package fr.isen.attia.androidrestaurant.carousel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.attia.androidrestaurant.databinding.FragmentCarouselBinding

/**
 * @brief A fragment of our Carousel (using ViewPager2 view)
 * It is a class that builds a fragment of the carousel using an image url.
 * Picasso lib will load the image from the url and bind it to the fragment's image view
 */
class CarouselFragment : Fragment() {
    /**
     * @brief the binding is necessary to access inner elements of the view
     */
    private lateinit var binding: FragmentCarouselBinding

    /**
     * @brief This override method will be called when the fragment view is created
     * @param inflater The layout inflater to use
     * @param container The parent container
     * @return Returns the view in which to build our fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarouselBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * @brief onViewCreated will be called AFTER the fragment view is created
     * @param view The freshly created view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Using picasso, we check for an empty URL and bind the frag with the image if possible
        val url = arguments?.getString(URL)
        if(!url.isNullOrEmpty()){
            Picasso.with(context).load(url).into(binding.fragImg)
        }
    }

    /**
     * @brief An inner companion object to allow for a static method
     */
    companion object{
        const val URL = "url"

        /**
         * @brief Creates a new carousel fragment from outside the CarouselFragment class
         */
        fun newInstance(url: String): CarouselFragment?{
            return CarouselFragment().apply{ arguments = Bundle().apply{ putString(URL, url) } }
        }
    }
}