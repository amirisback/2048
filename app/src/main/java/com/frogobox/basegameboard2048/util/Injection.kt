package  com.frogobox.basegameboard2048.util

import android.content.Context
import androidx.preference.PreferenceManager
import  com.frogobox.basegameboard2048.source.FrogoDataRepository
import  com.frogobox.basegameboard2048.source.dao.FavoriteDao
import  com.frogobox.basegameboard2048.source.local.FrogoAppDatabase
import  com.frogobox.basegameboard2048.source.local.FrogoLocalDataSource
import  com.frogobox.basegameboard2048.source.remote.FrogoRemoteDataSource

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseMusicPlayer
 * Copyright (C) 26/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 *  com.frogobox.basegameboard2048.util
 *
 */
object Injection {

    fun provideFrogoRepository(context: Context): FrogoDataRepository {

        val favoriteDao: FavoriteDao by lazy {
            FrogoAppDatabase.getInstance(context).favoriteScriptDao()
        }

        val appExecutors = AppExecutors()

        return FrogoDataRepository.getInstance(
            FrogoRemoteDataSource(context),
            FrogoLocalDataSource.getInstance(
                appExecutors,
                PreferenceManager.getDefaultSharedPreferences(context),
                favoriteDao
            )
        )
    }

}