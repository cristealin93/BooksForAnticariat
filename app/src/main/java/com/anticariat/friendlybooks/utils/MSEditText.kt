package com.anticariat.friendlybooks.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MSEditText(context : Context, assets:AttributeSet):AppCompatTextView(context,assets) {
init {
    applyFont()
}

    private fun applyFont() {

        val type: Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(type)
    }
}