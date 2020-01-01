package  com.frogobox.basegameboard2048.ui.activity

import android.os.Bundle
import  com.frogobox.basegameboard2048.R
import  com.frogobox.basegameboard2048.base.admob.BaseAdmobActivity
import  com.frogobox.basegameboard2048.util.helper.ConstHelper
import kotlinx.android.synthetic.main.activity_fanart_source.*
import kotlinx.android.synthetic.main.ads_phone_tab_special_smart_banner.*

class FanartSourceActivity : BaseAdmobActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fanart_source)
        setupDetailActivity("")
        setupInfoCopyright()
        setupShowAdsBanner(ads_phone_tab_special_smart_banner)
    }

    private fun setupInfoCopyright() {
        val image = intent.getStringExtra(ConstHelper.Extra.EXTRA_FANART)
        val link = image.split("/")
        tv_base_url.text = link.get(0) + "//" + link.get(2)
        tv_source_link.text = image
    }
}
