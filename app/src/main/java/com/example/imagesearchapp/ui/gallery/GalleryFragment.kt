package com.example.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentGalleryBinding
import com.example.imagesearchapp.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.btn_retry
import kotlinx.android.synthetic.main.fragment_gallery.progress_bar

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    UnsplashPhotoAdapter.OnItemClickListener {

    private val galleryViewModel by viewModels<GallleryViewModel>()
    private var binding: FragmentGalleryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryViewModel.searchPhotos("cats")
        val adapter = UnsplashPhotoAdapter(this)
        binding.apply {
            recyler_view.setHasFixedSize(true)
            recyler_view.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() },
            )
            btn_retry.setOnClickListener {
                adapter.retry()
            }
        }
        galleryViewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadstate ->
            binding.apply {
                progress_bar.isVisible = loadstate.source.refresh is LoadState.Loading
                recyler_view.isVisible = loadstate.source.refresh is LoadState.NotLoading
                btn_retry.isVisible = loadstate.source.refresh is LoadState.Error
                txt_error.isVisible = loadstate.source.refresh is LoadState.Error

                if (loadstate.source.refresh is LoadState.NotLoading &&
                        loadstate.append.endOfPaginationReached &&
                        adapter.itemCount<1) {
                    recyler_view.isVisible = false
                    txt_error.isVisible = true
                } else {
                    txt_error.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query.let {
                    binding?.recylerView?.scrollToPosition(0)
                    galleryViewModel.searchPhotos(query!!)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemClick(photo: Result) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment2(photo)
        findNavController().navigate(action)
    }
}
