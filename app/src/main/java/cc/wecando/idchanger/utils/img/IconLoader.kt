package cc.wecando.idchanger.utils.img

import android.app.Application
import android.content.pm.PackageManager
import android.widget.ImageView
import cc.wecando.idchanger.R
import cc.wecando.idchanger.utils.SingletonHolder
import cc.wecando.idchanger.utils.thread.DefaultExecutorSupplier

class IconLoader private constructor(context: Application) {
    private val packageManager: PackageManager = context.packageManager
    private val executorSupplier = DefaultExecutorSupplier.getInstance()

    companion object : SingletonHolder<IconLoader, Application>({
        IconLoader(it!!)
    })

    fun load(packageName: String, imageView: ImageView) {
        executorSupplier.runOnBackground {
            if (packageName == imageView.getTag(R.id.icon_key)) {
                val drawable = packageManager.getApplicationInfo(
                    packageName, PackageManager.GET_META_DATA
                ).loadIcon(packageManager)
                executorSupplier.runOnMain {
                    if (packageName == imageView.getTag(R.id.icon_key)) {
                        imageView.setImageDrawable(
                            drawable
                        )
                    }
                }
            }
        }
    }
}
