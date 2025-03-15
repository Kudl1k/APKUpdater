package cz.kudladev.app_update

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

suspend fun downloadAndInstallApk(context: Context, apkUrl: String) {
    try {
        val apkFile = File(context.getExternalFilesDir(null), "update.apk")

        // Download APK
        withContext(Dispatchers.IO) {
            val url = URL(apkUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(apkFile)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
            connection.disconnect()
        }

        // Install APK
        installApk(context, apkFile)
    } catch (e: Exception) {
        Log.e("APK Installer", "Error downloading or installing APK", e)
    }
}

private fun installApk(context: Context, apkFile: File) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val apkUri: Uri =
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apkFile)
        setDataAndType(apkUri, "application/vnd.android.package-archive")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(intent)
}