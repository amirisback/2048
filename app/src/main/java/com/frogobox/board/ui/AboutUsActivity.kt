package  com.frogobox.board.ui

import android.os.Bundle
import com.frogobox.board.R
import com.frogobox.board.base.BaseActivity

class AboutUsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        setupDetailActivity("")
    }
}
