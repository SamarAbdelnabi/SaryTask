package com.android.sary.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.sary.R
import com.android.sary.databinding.LightToolbarBinding

class Toolbar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var title_tv: TextView? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Toolbar)
            val title = typedArray.getString(R.styleable.Toolbar_title)
                val binding = LightToolbarBinding.inflate(LayoutInflater.from(context), this, true)
                title_tv = binding.title

                binding.title.text = title


            typedArray.recycle()
        }
    }

    fun setTitle(title: String) {
        this.title_tv?.text = title
    }

}