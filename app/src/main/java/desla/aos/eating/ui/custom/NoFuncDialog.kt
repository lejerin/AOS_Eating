package desla.aos.eating.ui.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import com.github.mmin18.widget.RealtimeBlurView
import desla.aos.eating.R
import kotlinx.android.synthetic.main.dialog_nofunc_double.*

class NoFuncDialog(context : Context) {

    interface InterfaceYesDialog {
        fun clickYes()
    }

    private val dlg = Dialog(context, R.style.DialogTheme)   //부모 액티비티의 context 가 들어감


    fun start() {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_nofunc)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함


        val btn_yes = dlg.findViewById<AppCompatButton>(R.id.btn_yes)
        btn_yes.setOnClickListener {
            dlg.dismiss()
        }

        val blur = dlg.findViewById<RealtimeBlurView>(R.id.blur)
        blur.setOnClickListener {
            dlg.dismiss()
        }


        dlg.show()
    }


    fun startDouble(isLogout: Boolean, interfaceYesDialog: InterfaceYesDialog){
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_nofunc_double)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        if(isLogout){
            dlg.img_dialog.setImageResource(R.drawable.ic_im_character_door)
            dlg.tv_expression.text = "로그아웃 하시겠어요?"
            dlg.btn_yes.text = "네"
            dlg.btn_no.text = "아니오"
        }


        val btn_yes = dlg.findViewById<AppCompatButton>(R.id.btn_yes)
        btn_yes.setOnClickListener {
            interfaceYesDialog.clickYes()
            dlg.dismiss()
        }

        val btn_no = dlg.findViewById<AppCompatButton>(R.id.btn_no)
        btn_no.setOnClickListener {
            dlg.dismiss()
        }

        val blur = dlg.findViewById<RealtimeBlurView>(R.id.blur)
        blur.setOnClickListener {
            dlg.dismiss()
        }


        dlg.show()
    }

}