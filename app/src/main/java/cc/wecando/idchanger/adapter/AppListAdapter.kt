package cc.wecando.idchanger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cc.wecando.idchanger.databinding.ItemAppEnhanceInfoBinding
import cc.wecando.idchanger.entity.AppEnhanceInfoEntity

class AppListAdapter  :
    ListAdapter<AppEnhanceInfoEntity, AppListAdapter.AppListVH>(ItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AppListVH(
        ItemAppEnhanceInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: AppListVH, position: Int) {
        holder.bind(getItem(position))
    }

    class AppListVH(private val binding: ItemAppEnhanceInfoBinding) : ViewHolder(binding.root) {
        fun bind(item: AppEnhanceInfoEntity) {
            binding.imgAppIcon.setImageDrawable(item.appIcon)
            binding.tvAppName.text = item.appName
            binding.tvPackageName.text = item.packageName
            binding.tvOriginSsaid.text = item.originSSAID
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<AppEnhanceInfoEntity>() {
        override fun areItemsTheSame(
            oldItem: AppEnhanceInfoEntity,
            newItem: AppEnhanceInfoEntity
        ) = oldItem.packageName == newItem.packageName

        override fun areContentsTheSame(
            oldItem: AppEnhanceInfoEntity,
            newItem: AppEnhanceInfoEntity
        ) = oldItem == newItem

    }
}