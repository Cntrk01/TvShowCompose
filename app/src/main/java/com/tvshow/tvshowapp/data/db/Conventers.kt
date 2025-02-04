package com.tvshow.tvshowapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tvshow.tvshowapp.domain.model.response.Episode

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromEpisodeList(value: List<Episode>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toEpisodeList(value: String): List<Episode>? {
        val type = object : TypeToken<List<Episode>>() {}.type
        return gson.fromJson(value, type)
    }
}