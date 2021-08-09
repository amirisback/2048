package  com.frogobox.board.mvvm.main

import android.os.Bundle
import com.frogobox.board.R
import com.frogobox.board.core.BaseActivity

class AboutUsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        setupDetailActivity("")
    }
}
