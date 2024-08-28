package com.isaquesoft.petcollection.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.isaquesoft.petcollection.data.model.Collection
import com.isaquesoft.petcollection.databinding.CollectionFragmentBinding
import com.isaquesoft.petcollection.presentation.adapter.CollectionAdapter
import com.isaquesoft.petcollection.presentation.state.CollectionState
import com.isaquesoft.petcollection.presentation.viewmodel.CollectionFragmentViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
class CollectionFragment : Fragment() {
    private lateinit var binding: CollectionFragmentBinding

    private val viewModel: CollectionFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = CollectionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListCollection()
        setupObserver()
        setupListener()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        is CollectionState.ShowListCollection -> {
                            setupListCollection(it.listCollection)
                        }

                        else -> return@collect
                    }
                }
            }
        }
    }

    private fun setupListCollection(listCollection: List<Collection>) {
        with(binding) {
            textTotalCollection.text =
                "${listCollection.filter { it.isCollected }.size}/${listCollection.size}"

            context?.let {
                recyclerviewCollection.layoutManager = GridLayoutManager(it, 3)
                recyclerviewCollection.adapter = CollectionAdapter(listCollection)
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            imageButtonBackCollection.setOnClickListener {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}
