package com.anticariat.friendlybooks.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class MSButton(context : Context, assets:AttributeSet):AppCompatButton(context,assets) {
init {
    applyFont()
}

    private fun applyFont() {

        val type: Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(type)
    }
}