package com.example.cats.presentation.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats.CatsApp
import com.example.cats.R
import com.example.cats.databinding.ActivityCatsBinding
import com.example.cats.presentation.details.CatDetailsActivity
import com.example.cats.presentation.filter.FiltersDialog

class CatsActivity : AppCompatActivity() {
    companion object {
        fun buildIntent(context: Context) =
            Intent(context, CatsActivity::class.java)
    }

    private val container by lazy { (applicationContext as CatsApp).appContainer }
    private val viewModel: CatsViewModel by viewModels { container.catsViewModelFactory }

    private lateinit var binding: ActivityCatsBinding
    private val adapter by lazy { CatsAdapter(viewModel::catSelected) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        subscribeOnViewModel()
        viewModel.start()
    }

    private fun setupView() {
        binding = ActivityCatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.catsList.adapter = adapter
        binding.catsList.layoutManager = LinearLayoutManager(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun subscribeOnViewModel() {
        viewModel.cats.observe(this) {
            binding.emptyState.root.isVisible = it.isEmpty()
            binding.catsList.isVisible = it.isNotEmpty()
            adapter.submitList(it)
        }
        viewModel.error.observe(this) { errorMessage ->
            binding.apply {
                errorState.root.isVisible = true
                errorState.errorMessage.text = errorMessage
                errorState.retryButton.setOnClickListener {
                    viewModel.start()
                }
            }
        }
        viewModel.loading.observe(this) {
            binding.loadingState.root.isVisible = it
        }
        viewModel.filters.observe(this) {
            FiltersDialog().show(supportFragmentManager, it) {
                viewModel.onFiltered()
            }
        }
        viewModel.selectedCat.observe(this) {
            val intent = CatDetailsActivity.buildIntent(this, it)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.menuItemFilter -> {
                viewModel.onFilter()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu ?: return false
        menuInflater.inflate(R.menu.menu_cats, menu)
        return super.onCreateOptionsMenu(menu)
    }
}