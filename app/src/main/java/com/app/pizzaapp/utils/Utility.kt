package com.app.pizzaapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object Utility {
    inline fun <reified T> readJsonFromAsset(context: Context, fileName: String): T? {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}