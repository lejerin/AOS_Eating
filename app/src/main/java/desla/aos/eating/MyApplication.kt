package desla.aos.eating

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import desla.aos.eating.R

class MyApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }


    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)

        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    fun getString(key: String, defValue: String): String
    {
        return prefs.getString(key, defValue).toString()
    }
    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getInt(key: String, defValue: Int): Int
    {
        return prefs.getInt(key, defValue)
    }
    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }


    fun setUserAuth(id: String, nickname: String){
        prefs.edit().putString("id", id).apply()
        prefs.edit().putString("name", nickname).apply()
    }

    fun setUserLocation(address: String, latitude: String, longitude: String){
        prefs.edit().putString("address", address).apply()
        prefs.edit().putString("latitude", latitude).apply()
        prefs.edit().putString("longitude", longitude).apply()
    }

}