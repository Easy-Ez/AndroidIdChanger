package cc.wecando.idchanger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import cc.wecando.idchanger.App
import cc.wecando.idchanger.utils.img.IconLoader
import cc.wecando.idchanger.R
import cc.wecando.idchanger.databinding.ItemAppEnhanceInfoBinding
import cc.wecando.idchanger.entity.AppEnhanceInfoEntity

class AppListAdapter : ListAdapter<AppEnhanceInfoEntity, AppListAdapter.AppListVH>(ItemCallback()) {
    private val iconLoader = IconLoader.getInstance(App.instance)
    private val scrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            // todo 滚动时不loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AppListVH(
        ItemAppEnhanceInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(scrollListener)
    }

    override fun onBindViewHolder(holder: AppListVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppListVH(private val binding: ItemAppEnhanceInfoBinding) :
        ViewHolder(binding.root) {
        fun bind(item: AppEnhanceInfoEntity) {
            val res = itemView.resources
            binding.imgAppIcon.setTag(R.id.icon_key, item.packageName)
            binding.imgAppIcon.setImageResource(R.drawable.loading)
            // async load
            iconLoader.load(item.packageName, binding.imgAppIcon)

            binding.tvAppName.text = res.getString(
                R.string.placeholder_appName_version, item.appName, item.appVersionName
            )
            binding.tvPackageName.text =
                res.getString(R.string.placeholder_packageName, item.packageName)
            binding.tvCurrentSsaid.text = item.originSSAID
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<AppEnhanceInfoEntity>() {
        override fun areItemsTheSame(
            oldItem: AppEnhanceInfoEntity, newItem: AppEnhanceInfoEntity
        ) = oldItem.packageName == newItem.packageName

        override fun areContentsTheSame(
            oldItem: AppEnhanceInfoEntity, newItem: AppEnhanceInfoEntity
        ) = oldItem == newItem

    }
}