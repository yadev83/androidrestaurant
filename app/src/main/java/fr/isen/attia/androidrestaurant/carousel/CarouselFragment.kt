package fr.isen.attia.androidrestaurant.carousel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.attia.androidrestaurant.databinding.FragmentCarouselBinding

class CarouselFragment : Fragment() {
    private lateinit var binding: FragmentCarouselBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarouselBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(URL)
        if(!url.isNullOrEmpty()){
            Picasso.with(context).load(url).into(binding.fragImg)
        }
    }

    companion object{
        const val URL = "url"
        fun newInstance(url: String): CarouselFragment?{
            return CarouselFragment().apply{ arguments = Bundle().apply{ putString(URL, url) } }
        }
    }
}