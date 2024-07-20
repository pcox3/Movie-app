package com.movies.utility

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson

/**
 * @param value API response
 * @param baseClass Data Class to convert response into
 * This function converts API response into Data Class
 */
fun <T>genericClassCast(value: Any?, baseClass: Class<T>): T?{
    return if(value != null){
        try{
            val gson = Gson()
            gson.fromJson(gson.toJson(value),baseClass)
        }catch (ex: Exception){
            //Invalid Json response
            ex.printStackTrace()
            null
        }

    }else
        null
}


/**
 * @param seconds Time in milliseconds
 * @param action Action to execute every x seconds
 */
private var debounceHandler: Handler = Handler(Looper.getMainLooper())
private var debounceRunnable: Runnable? = null
fun debounce(seconds:Long = 500, action: () -> Unit){
    debounceRunnable?.let { debounceHandler.removeCallbacks(it) }
    debounceRunnable = Runnable {
        action()
    }
    debounceRunnable?.let { debounceHandler.postDelayed(it, seconds) }
}
