package  com.frogobox.basegameboard2048.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frogobox.basegameboard2048.R
import com.frogobox.basegameboard2048.base.ui.BaseFragment
import com.frogobox.basegameboard2048.util.helper.PagerHelper
import kotlinx.android.synthetic.main.fragment_root_wallpaper.*

/**
 * A simple [Fragment] subclass.
 */
class RootWallpaperFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_root_wallpaper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupShowAdsInterstitial()
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = PagerHelper(childFragmentManager)
        pagerAdapter.setupPagerFragment(WallpaperFragment(), resources.getString(R.string.title_wallpaper))
        viewpager.adapter = pagerAdapter
        tablayout.setupWithViewPager(viewpager)
    }
}
