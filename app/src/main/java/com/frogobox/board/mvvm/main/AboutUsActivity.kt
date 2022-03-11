package  com.frogobox.board.mvvm.main

import android.os.Bundle
import com.frogobox.board.core.BaseBindingActivity
import com.frogobox.board.databinding.ActivityAboutUsBinding

class AboutUsActivity : BaseBindingActivity<ActivityAboutUsBinding>() {

    override fun setupViewBinding(): ActivityAboutUsBinding {
        return ActivityAboutUsBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupOnCreate(savedInstanceState: Bundle?) {
        setupDetailActivity("")
    }


}
