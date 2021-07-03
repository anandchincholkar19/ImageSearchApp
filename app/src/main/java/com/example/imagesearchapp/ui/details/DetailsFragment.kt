package com.example.imagesearchapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.item_unsplash_photo.*

class DetailsFragment: Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        binding.apply {
            val photos = args.photo
            Glide.with(this@DetailsFragment)
                .load(photos.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        detail_progress_bar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        detail_progress_bar.isVisible = false
                        txtViewCreator.isVisible = true
                        txtViewDesc.isVisible = photos.description !=null
                        return false
                    }
                })
                .into(detail_img_view)

            txtViewDesc.text = photos.description
            if (!photos.user.portfolio_url.isNullOrEmpty()) {
                val url = Uri.parse(photos.user.portfolio_url)
                val intent = Intent(Intent.ACTION_VIEW, url)
                txtViewCreator.apply {
                    text = "Photo by ${photos.user.name} on Unsplash"
                    setOnClickListener {
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
