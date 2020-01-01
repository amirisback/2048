package  com.frogobox.basegameboard2048.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frogobox.basegameboard2048.R
import com.frogobox.basegameboard2048.base.ui.BaseFragment
import com.frogobox.basegameboard2048.base.view.BaseViewListener
import com.frogobox.basegameboard2048.model.Favorite
import com.frogobox.basegameboard2048.ui.activity.WallpaperMainActivity
import com.frogobox.basegameboard2048.view.adapter.FavoriteViewAdapter
import com.frogobox.basegameboard2048.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.custom_view_empty.*
import kotlinx.android.synthetic.main.fragment_wallpaper.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : BaseFragment(), BaseViewListener<Favorite> {

    private lateinit var mViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavorite()
    }

    override fun onResume() {
        super.onResume()
        getFavorite()
    }

    private fun getFavorite() {
        mViewModel.getFavorite()
    }

    private fun setupViewModel() {
        mViewModel = (activity as WallpaperMainActivity).obtainFavoriteViewModel().apply {

            favListLive.observe(viewLifecycleOwner, Observer {
                setupRecyclerView(it)
            })

            eventIsEmpty.observe(viewLifecycleOwner, Observer {
                setupEventEmptyView(empty_view, it)
            })

        }
    }

    private fun setupRecyclerView(data: List<Favorite>) {
        val adapter = FavoriteViewAdapter()
        adapter.setupRequirement(this, data, R.layout.item_grid_wallpaper_fav)
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.adapter = adapter
    }

    override fun onItemClicked(data: Favorite) {
//        baseStartActivity<FanartDetailActivity, Favorite>(EXTRA_FAV_FANART, data)
    }

    override fun onItemLongClicked(data: Favorite) {

    }


}