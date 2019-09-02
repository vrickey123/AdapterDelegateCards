package com.leapsoftware.adapterdelegatecards.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

class AssetUtils {
    companion object {
        val FILENAME_SAMPLE_FEED_ITEMS = "feed_items.json"

        fun getJsonFromAsset(context: Context, filename: String) : String {
            var jsonString: String = ""
            try {
                val inputStream: InputStream = context.resources.assets.open(filename)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                jsonString = String(buffer)
            } catch (ex: IOException) {
                ex.printStackTrace();
            }
            return jsonString
        }
    }
}