package  com.frogobox.basegameboard2048.util.helper

import android.Manifest
import android.os.Environment
import com.frogobox.basegameboard2048.BuildConfig
import com.frogobox.basegameboard2048.R

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.helper
 *
 */
class ConstHelper {

    object SQLiteDatabase {

        const val SQLITE_ATTRIBUTE = "_sqlite"
        val SQLITE_DATABASE_NAME = BuildConfig.APPLICATION_ID.replace(".","_") + SQLITE_ATTRIBUTE

        const val SQLITE_DATABASE_VERSION = 1
        // Names of table in the database
        const val TABLE_SAMPLEDATA = "SAMPLE_DATA"

        // Names of columns in the databases in this example we only use one table
        const val KEY_ID = "id"
        const val KEY_DOMAIN = "domain"
        const val KEY_USERNAME = "username"
        const val KEY_LENGTH = "length"
    }

    object Pref {
        // shared pref mode
        const val PRIVATE_MODE = 0

        // Shared preferences file name
        const val PREF_NAME = "privacy_friendly_apps"
        const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"

        const val PREF_CURRENT_PAGE = "currentPage"
        const val PREF_COLOR = "pref_color"
        const val PREF_MY = "myPref"

        const val PREF_ANIMATION_ACTIVATED = "pref_animationActivated"
        const val PREF_SETTINGS_DISPLAY = "settings_display"
    }

    object Extra {
        const val BASE_EXTRA = BuildConfig.APPLICATION_ID
        const val EXTRA_OPTION = "$BASE_EXTRA.EXTRA_OPTION"

        const val EXTRA_N = "$BASE_EXTRA.EXTRA_N"
        const val EXTRA_NEW = "$BASE_EXTRA.EXTRA_NEW"
        const val EXTRA_FILENAME = "$BASE_EXTRA.EXTRA_FILENAME"
        const val EXTRA_UNDO = "$BASE_EXTRA.EXTRA_UNDO"
        const val EXTRA_POINTS = "$BASE_EXTRA.EXTRA_POINTS"


    }

    object Ext {

        const val DEF_DRAWABLE = "drawable"
        const val DEF_RAW = "raw"
        const val MP4 = ".mp4"
        const val PNG = ".png"
        const val TXT = ".txt"

    }

    object Dir {
        const val BASE_FILE_NAME = "SPEECH_"
        const val BASE_DIR_NAME = "BaseMusicPlayer"
        val DIR_NAME = "${Environment.DIRECTORY_PICTURES}/$BASE_DIR_NAME"

        val VIDEO_FILE_NAME = "$BASE_FILE_NAME${System.currentTimeMillis()}${Ext.MP4}"

    }

    object Const {

        const val OPTION_GET = "OPTION_GET"
        const val OPTION_DELETE = "OPTION_DELETE"

        const val DEFAULT_NULL = "null"
        const val DEFAULT_ERROR_MESSAGE = "Failed"
        const val FRAGMENT_DIALOG = "dialog"

        const val SPLASH_INTERVAL = 1000

        const val EXTENSION_CSV = ".csv"
        const val BASE_PATH_RAW = "src/com/frogobox/raw"
        const val PATH_DATA_CSV = "/influencers$EXTENSION_CSV"
        const val PATH_RAW_CSV_DATA = BASE_PATH_RAW + PATH_DATA_CSV

        const val TYPE_MAIN_WALLPAPER = 100

        const val FILE_STATISTIC = "statistic"
        const val FILE_STATE = "state"

    }

    object Games {
        const val INIT_ADDINGSPEED: Long = 100
        const val INIT_MOVINGSPEED: Long = 80
        const val INIT_SCALINGSPEED: Long = 100
        const val INIT_SCALINGFACTOR = 1.1f

        const val WINTHRESHOLD = 2048
        const val PROPABILITYFORTWO = 0.9
    }

}