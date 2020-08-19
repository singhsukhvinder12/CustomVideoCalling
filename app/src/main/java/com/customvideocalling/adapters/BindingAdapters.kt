package com.customvideocalling.adapters

import android.app.Activity
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.customvideocalling.R
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.utils.FontStyle

object BindingAdapters {
        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView : ImageView, resource : String) {
            Glide.with(imageView.context)
                    .load(resource)
                    .placeholder(R.drawable.logo)
                    .into(imageView)
        }

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView : ImageView, drawable : Drawable) {
            Glide.with(imageView.context)
                    .load(drawable)
                    .into(imageView)
        }

        @BindingAdapter("backOnClick")
        @JvmStatic
        fun bindOnImageButtonClickListener(imageView : ImageView, text : String?) {
            imageView.setOnClickListener { view->
                val activity = getActivity(view)
                println(text)
                activity!!.finish()
            }
        }

        @BindingAdapter("hideKeyBoardOnClick")
        @JvmStatic
        fun bindOnLinearLayoutClickListener(view : View, text : String?) {
            view.setOnClickListener { view->
                UtilsFunctions.hideKeyBoard(view)
            }
        }

        @BindingAdapter("android:visibility")
        @JvmStatic
        fun setVisibility(view : View, value : Boolean) {
            view.visibility = if (value) View.VISIBLE else View.GONE

        }



        private fun getActivity(v : View) : Activity? {
            var context = v.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = (context).baseContext
            }
            return null
        }

        @BindingAdapter("bind:font")
        @JvmStatic
        fun setFont(textView : TextView, fontName : String) {
            textView.typeface = FontStyle.instance.getFont(fontName)
        }

    }

