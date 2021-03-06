package desla.aos.eating.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object MyTimeUtils {

     fun twoDatesBetweenTime(oldtime: String): String {

        val transFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val oldDate = transFormat.parse(oldtime)


      return getTime(oldDate)
    }

    fun twoDatesBetweenTimeInView(oldtime: String): String {

        val transFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val oldDate = transFormat.parse(oldtime)


        return getTime(oldDate)
    }

    fun isBefore(oldtime: String): Boolean {
        val transFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val oldDate = transFormat.parse(oldtime)
        var diff: Long = Date().time - oldDate.time

        Log.i("eunjin", "day  ${Date()}    ${oldDate}  ${diff}")


        return (diff > 0)
    }


    fun getTime(oldDate: Date): String{

        var diff: Long = Date().time - oldDate.time

        if(diff >= 0){
            return "00:00"
        }


        diff *= (-1)

        val days = (diff / (24 * 60 * 60 * 1000)).toInt()



        val getHour: Long = diff / (60 * 60 * 1000)
        var hour = getHour.toString()

        val getMin: Long = diff - getHour * (60 * 60 * 1000)
        var min = (getMin / (60 * 1000)).toString() // 몫

        var second = (getMin % (60 * 1000) / 1000).toString() // 나머지

        // 시간이 한자리면 0을 붙인다
        if (hour.length == 1) {
            hour = "0$hour"
        }

        // 분이 한자리면 0을 붙인다
        if (min.length == 1) {
            min = "0$min"
        }

       /* // 초가 한자리면 0을 붙인다
        if (second.length == 1) {
            second = "0$second"
        }*/

        return when {
            days > 0 -> {
                "${days}day"
            }
            else -> {
                "$hour:$min"
            }
        }
    }
}