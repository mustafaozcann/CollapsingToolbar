package com.mzcn.collapsingtoolbar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.mzcn.collapsingtoolbar.databinding.LayoutCollapsingToolbarBinding

class CollapsingToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppBarLayout(context, attrs, defStyleAttr) {

    private val binding = LayoutCollapsingToolbarBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private var title: CharSequence? = null

    private var scrollableView: View? = null

    init {

        attrs?.let {
            val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.CollapsingToolbar)

            with(attrsArray) {

                title = getText(
                    R.styleable.CollapsingToolbar_title,
                )

                recycle()
            }
        }
    }

    private fun findScrollableViews(): View? {

        (parent as? ViewGroup)?.let { viewGroup ->
            val childCount = viewGroup.childCount
            for (i in 0 until childCount) {
                val child = viewGroup.getChildAt(i)
                if (child is ScrollView || child is RecyclerView || child is NestedScrollView) {
                    Log.d("TAG", child.toString())
                    return child
                }
            }
        }

        return null
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()


        findScrollableViews().let {
            scrollableView = it

            if (scrollableView is NestedScrollView) {

                (scrollableView as NestedScrollView).setOnScrollChangeListener { _,  _, y, _, _ ->

                    this.scrollY = y

                }
            }
        }

    }
}