package com.lx.oneteamproject.weather

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lx.oneteamproject.R
import com.lx.popupproject.weather.ModelWeather
import java.util.Calendar

class WeatherAdapter(var items: Array<ModelWeather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {

        return ViewHolder(View(parent.context))
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun setItem(item: ModelWeather) {
            val imgWeather = itemView.findViewById<ImageView>(R.id.imgWeather)  // 날씨 이미지
            val tvTemp = itemView.findViewById<TextView>(R.id.tvTemp)           // 온도
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            imgWeather.setImageResource(getRainImage(item.rainType, item.sky, currentHour))
            tvTemp.text = item.temp + "°"
        }
    }
}

fun getTime(factTime: String): String {
    if (factTime != "지금") {
        var hourSystem: Int = factTime.toInt()
        var hourSystemString = ""

        if (hourSystem == 0) {
            return "오전 12시"
        } else if (hourSystem > 2100) {
            hourSystem -= 1200
            hourSystemString = hourSystem.toString()
            return "오후 ${hourSystemString[0]}${hourSystemString[1]}시"
        } else if (hourSystem == 1200) {
            return "오후 12시"
        } else if (hourSystem > 1200) {
            hourSystem -= 1200
            hourSystemString = hourSystem.toString()
            return "오후 ${hourSystemString[0]}시"
        } else if (hourSystem >= 1000) {
            hourSystemString = hourSystem.toString()
            return "오전 ${hourSystemString[0]}${hourSystemString[1]}시"
        } else {
            hourSystemString = hourSystem.toString()
            return "오전 ${hourSystemString[0]}시"
        }
    } else {
        return factTime
    }
}

fun getRainImage(rainType: String, sky: String, currentHour: Int): Int {
    return when (rainType) {
        "0" -> getWeatherImage(sky, currentHour)
        "1" -> if(currentHour >=18 || currentHour <6) R.drawable.weather_night_rain else R.drawable.weather_sun_rain
        "2" -> if(currentHour >= 18 || currentHour <6) R.drawable.weather_night_hail else R.drawable.weather_sun_hail
        "3" -> if(currentHour >= 18 || currentHour <6 ) R.drawable.weather_night_snow else R.drawable.weather_sun_snow
        "4" -> if(currentHour >= 18 || currentHour <6 ) R.drawable.weather_night_shower else R.drawable.weather_sun_shower
        else -> getWeatherImage(sky, currentHour)
    }
}

fun getWeatherImage(sky: String, currentHour: Int): Int {
    return when (sky) {
        "1" -> if (currentHour >= 18 || currentHour < 6) R.drawable.weather_moon else R.drawable.sun
        "3" -> if (currentHour >= 18 || currentHour < 6) R.drawable.weather_moon_cloudy else R.drawable.weather_sun_cloudy
        "4" -> R.drawable.weather_blur1
        else ->  R.drawable.weather_question
    }

}