package com.secretmenu.Interfaces

/*
 * Created by dell on 2/22/2018.
 */

interface TextWatcherInterface {
    fun beforeTextChanged()

    fun onTextChanged(count: Int, mKey: String)

    fun afterTextChanged()
}
