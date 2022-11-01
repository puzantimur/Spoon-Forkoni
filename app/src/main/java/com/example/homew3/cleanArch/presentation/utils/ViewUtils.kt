package com.example.homew3.cleanArch.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.R
import com.google.android.material.textfield.TextInputLayout

object ViewUtils {
    fun View.insets() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            this.updatePadding(
                left = systemBarInsets.left,
                right = systemBarInsets.right,
            )
            insets
        }
    }

    fun TextInputLayout.getTextOrSetError(): String? {
        return editText?.text?.toString()
            ?.ifBlank {
                error = "Enter something"
                null
            }
    }

    fun TextInputLayout.getPasswordOrSetError():String?{
        return if (editText?.text.toString().matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}\$"))) {
            editText?.text?.toString()
        } else {
            error = "At least 1 uppercase letter and 1 number"
            null
        }
    }

    fun RecyclerView.addItemDecorationBottom(lengthDivider: Int) {
        this.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = lengthDivider
            }
        })

    }

    fun RecyclerView.addItemDecorationLeftRight(leftDivider: Int, rightDivider: Int) {
        this.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = leftDivider
                outRect.right = rightDivider
            }
        })

    }

    fun Int.secondsToTime(): String {
        val hours = this / 3600
        val minutes = (this % 3600) / 60
        val seconds = this % 60

        return if (hours == 0) {
            String.format("%02d:%02d", minutes, seconds)
        } else {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}