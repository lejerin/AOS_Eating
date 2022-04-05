package desla.aos.eating.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import desla.aos.eating.ui.custom.NoFuncDialog
import desla.aos.eating.ui.main.MainActivity
import net.daum.android.map.MapActivity

fun Context.getActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }

fun Context.startGPSSettingActivityForResult() =
        Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }


fun Context.startMainActivity() =
        Intent(this, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}


fun View.showNoFuncDoialog(v: View) {

    val noFuncDialog = NoFuncDialog(v.context)
    noFuncDialog.start()
}