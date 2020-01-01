package  com.frogobox.basegameboard2048.base.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import  com.frogobox.basegameboard2048.util.SingleLiveEvent

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PregnantFashsion
 * Copyright (C) 18/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 *  com.frogobox.basegameboard2048.base
 *
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var eventShowProgress = SingleLiveEvent<Boolean>()
    var eventIsEmpty = SingleLiveEvent<Boolean>()
}