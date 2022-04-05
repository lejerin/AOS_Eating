package desla.aos.eating.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import desla.aos.eating.R
import kotlinx.android.synthetic.main.view_tv_custom.view.*

open class CustomTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    var view: View = View.inflate(context, R.layout.view_tv_custom, this)

    init {


        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTextView)
        typedArray.getString(R.styleable.CustomTextView_text).let {
            text.text = it
        }
        typedArray.recycle()
    }


    fun setTextIn(str: String){
        val text = view.findViewById<TextView>(R.id.text)
        text.text = str
    }


}