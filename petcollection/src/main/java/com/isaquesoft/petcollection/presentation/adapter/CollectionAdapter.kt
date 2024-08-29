package com.isaquesoft.petcollection.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isaquesoft.petcollection.data.model.Collection
import com.isaquesoft.petcollection.databinding.ItemCollectionBinding
import com.isaquesoft.petcollection.presentation.util.setRawFromString

class CollectionAdapter(
    private val listCollection: List<Collection>,
) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ItemCollectionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: Collection) {
            with(binding) {
//                if (collection.isCollected) {
                animationViewItemCollection.setRawFromString(collection.rawName)
                animationViewItemCollection.repeatCount = 20
//                } else {
//                    animationViewItemCollection.setRawFromString("paw")
//                    animationViewItemCollection.repeatCount = 0
//                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CollectionAdapter.ViewHolder {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CollectionAdapter.ViewHolder,
        position: Int,
    ) {
        holder.bind(listCollection[position])
    }

    override fun getItemCount() = listCollection.size
}
