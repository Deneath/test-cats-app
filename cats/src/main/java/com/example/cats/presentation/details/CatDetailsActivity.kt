package com.example.cats.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.cats.CatsApp
import com.example.cats.R
import com.example.cats.databinding.ActivityCatDetailsBinding

class CatDetailsActivity : AppCompatActivity() {

    companion object {
        private const val catNameKey = "cat_name"
        fun buildIntent(context: Context, catName: String) =
            Intent(context, CatDetailsActivity::class.java).apply {
                putExtra(catNameKey, catName)
            }
    }

    private val container by lazy { (applicationContext as CatsApp).appContainer }
    private val viewModel by viewModels<CatDetailsViewModel> { container.catsViewModelFactory }
    private lateinit var binding: ActivityCatDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val catName = intent.getStringExtra(catNameKey)
        viewModel.start(catName)
        observeViewModel()
        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() {
        viewModel.details.observe(this) { cat ->
            binding.apply {
                binding.errorState.root.isVisible = false
                supportActionBar?.title = cat.name
                breed.text = cat.breed
                description.text = cat.description
                Glide.with(this@CatDetailsActivity)
                    .load(cat.picture)
                    .centerCrop()
                    .into(catPicture)
            }
        }
        viewModel.loading.observe(this) {
            binding.loadingState.root.isVisible = it
        }
        viewModel.noSuchCatError.observe(this) {
            binding.errorState.root.isVisible = true
            binding.errorState.errorMessage.text = it
            binding.errorState.retryButton.text = resources.getString(R.string.close_button)
            binding.errorState.retryButton.setOnClickListener { onBackPressed() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}