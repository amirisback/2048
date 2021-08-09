package  com.frogobox.board.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import com.frogobox.board.BuildConfig
import com.frogobox.board.R
import com.frogobox.board.util.SingleConst.Dir.DIR_NAME
import com.frogobox.board.util.SingleConst.Dir.VIDEO_FILE_NAME
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


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
object SingleFunc {

    fun createFolderPictureVideo() {
        val videoFolder = Environment.getExternalStoragePublicDirectory(DIR_NAME)
        if (!videoFolder.exists()) {
            videoFolder.mkdirs()
        }
    }

    fun getVideoFilePath(): String {
        val dir = Environment.getExternalStoragePublicDirectory(DIR_NAME)
        return if (dir == null) {
            VIDEO_FILE_NAME
        } else {
            "${dir.absoluteFile}/$VIDEO_FILE_NAME"
        }
    }

    fun createDialogDefault(
        context: Context,
        title: String,
        message: String,
        listenerYes: () -> Unit,
        listenerNo: () -> Unit
    ) {
        val dialogBuilder = AlertDialog.Builder(context)
        // set message of alert dialog
        dialogBuilder.setMessage(message)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton(
                context.getText(R.string.dialog_button_yes),
                DialogInterface.OnClickListener { dialog, id ->
                    listenerYes()
                })
            // negative button text and action
            .setNegativeButton(
                context.getText(R.string.dialog_button_no),
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    listenerNo()
                })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(title)
        // show alert dialog
        alert.show()
    }

    fun noAction(): Boolean {
        return true
    }

    fun isNetworkAvailable(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) isConnected = true
        return isConnected
    }

    fun showVersion(): String {
        return "Version " + BuildConfig.VERSION_NAME
    }

    private fun getBitmapFromURL(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}