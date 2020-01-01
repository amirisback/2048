package  com.frogobox.basegameboard2048.source

import  com.frogobox.basegameboard2048.base.data.BaseDataSource
import  com.frogobox.basegameboard2048.model.Favorite
import  com.frogobox.basegameboard2048.model.Wallpaper

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseMusicPlayer
 * Copyright (C) 18/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 *  com.frogobox.basegameboard2048.source
 *
 */
interface FrogoDataSource : BaseDataSource {

    // Room Database -------------------------------------------------------------------------------
    fun saveRoomFavorite(data: Favorite): Boolean

    // Get
    fun getRoomFavorite(callback: GetRoomDataCallBack<List<Favorite>>)

    // Delete
    fun deleteRoomFavorite(tableId: Int): Boolean
    fun deleteRoomFromWallpaperID(id: Int) : Boolean

    // Nuke
    fun nukeRoomFavorite(): Boolean

    // Get
    interface GetRoomDataCallBack<T> : BaseDataSource.ResponseCallback<T>
}