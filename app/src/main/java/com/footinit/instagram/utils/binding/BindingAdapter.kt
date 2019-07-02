package com.footinit.instagram.utils.binding

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.footinit.instagram.R
import com.footinit.instagram.ui.main.BottomMenuNavigationListener
import com.footinit.instagram.utils.common.GlideHelper
import com.footinit.instagram.utils.common.LoadMoreListener
import com.footinit.instagram.utils.common.Resource
import com.footinit.instagram.utils.common.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("textInputLayoutValidation")
fun textInputLayoutValidation(view: TextInputLayout, resourceLiveData: LiveData<Resource<Int>>) {
    resourceLiveData.value?.run {
        when (status) {
            Status.ERROR -> view.error = data?.let { view.context.getString(it) }
            else -> view.isErrorEnabled = false
        }
    }
}

@BindingAdapter("onNavigationItemSelected")
fun onNavigationItemSelected(view: BottomNavigationView, listener: BottomMenuNavigationListener) {
    view.setOnNavigationItemSelectedListener { listener.onMenuSelected(it.itemId) }

}

@BindingAdapter("formatText")
fun formatText(view: TextView, value: String?) {
    if (value == null || value.isEmpty())
        view.text = view.context.getString(R.string.main_profile_no_bio)
    else view.text = value
}

@BindingAdapter("likeCount")
fun formatLikeCount(view: TextView, likeCount: Int) {
    view.text = when (likeCount) {
        0, 1 -> view.context.getString(R.string.main_profile_like_count_singular, likeCount)
        else -> view.context.getString(R.string.main_profile_like_count_plural, likeCount)
    }
}

@BindingAdapter("url", "headers", "circular")
fun loadProtectedImageCircular(view: ImageView, url: String?, headers: Map<String, String>, circular: Boolean) {
    url?.let {
        if (it.isNotEmpty()) {
            val requestBuilder = Glide.with(view.context).load(GlideHelper.getProtectedUrl(url, headers))
            requestBuilder.apply(RequestOptions().placeholder(R.drawable.all_placeholder_profile).error(R.drawable.all_placeholder_profile))
            if (circular) requestBuilder.apply(RequestOptions.circleCropTransform())
            requestBuilder.into(view)
        }
    }
}

@BindingAdapter("url", "headers")
fun loadProtectedImage(view: ImageView, url: String?, headers: Map<String, String>) {
    url?.let {
        Glide.with(view.context).load(GlideHelper.getProtectedUrl(url, headers)).into(view)
    }
}

@BindingAdapter("url", "headers", "placeholderWidth", "placeholderHeight")
fun loadProtectedImage(
    view: ImageView, url: String?, headers: Map<String, String>,
    placeholderWidth: Int?, placeholderHeight: Int?
) {
    url?.let {
        val requestOptions = Glide.with(view.context).load(GlideHelper.getProtectedUrl(url, headers))
        if (placeholderHeight != null && placeholderWidth != null) {
            val params = view.layoutParams as ViewGroup.LayoutParams
            params.height = placeholderHeight
            params.width = placeholderWidth
            view.layoutParams = params

            requestOptions
                .apply(RequestOptions().override(placeholderWidth, placeholderHeight))
                .apply(RequestOptions().placeholder(R.drawable.all_placeholder_generic))
        }
        requestOptions.into(view)
    }
}

@BindingAdapter("postCount")
fun formatPostCount(view: TextView, postCount: Int) {
    val text = when (postCount) {
        0, 1 -> view.context.getString(R.string.main_profile_post_count_singular, postCount)
        else -> view.context.getString(R.string.main_profile_post_count_plural, postCount)
    }

    val spannableString = SpannableStringBuilder(text)
    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(RelativeSizeSpan(1.2f), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

    view.text = spannableString
}

@BindingAdapter("app:onLoadMore")
fun onLoadMore(view: RecyclerView, listener: LoadMoreListener) {
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            view.layoutManager?.run {
                if (this is LinearLayoutManager
                    && itemCount > 0
                    && itemCount == findLastVisibleItemPosition() + 1
                ) listener.onLoadMore()
            }
        }
    })
}

@BindingAdapter("app:loadFromDisk")
fun loadFromDisk(view: ImageView, path: String?) {
    path?.let { Glide.with(view.context).load(it).into(view) }
}