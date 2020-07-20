package com.secretmenu.utils

import android.graphics.Typeface
import android.util.Log
import com.secretmenu.application.MyApplication
import java.util.*

//* Created by Saira on 03/07/2019.
class FontStyle {
    internal var fontMap = HashMap<String, String>()

    fun addFont(alias: String, fontName: String) {
        fontMap.put(alias, fontName)
    }

    fun getFont(alias: String): Typeface? {
        val fontFilename = fontMap.get(alias)
        if (fontFilename == null) {
            Log.e("", "Font not available with name " + alias)
            return null
        } else {
            return Typeface.createFromAsset(MyApplication.instance.getAssets(), "fonts/" + fontFilename)
        }
    }

    companion object {
        internal var customFontFamily: FontStyle? = null
        val instance: FontStyle
            get() {
                if (customFontFamily == null)
                    customFontFamily = FontStyle()
                return customFontFamily!!
            }
    }
}


