package com.movies.utility

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
