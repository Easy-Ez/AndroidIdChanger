package cc.wecando.idchanger

import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.WorkerThread
import cc.wecando.idchanger.entity.AppInfoEntity

/**
 * AppInfo 获取器
 */
class AppInfoFetcher(private val packageManager: PackageManager) {
    /**
     * 根据包名,获取 App 名称,版本名称,图标
     */
    @Throws(PackageManager.NameNotFoundException::class)
    @WorkerThread
    fun fetchAppInfo(packageName: String): AppInfoEntity {
        val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName, PackageManager.ApplicationInfoFlags.of(0)
            )
        } else {
            packageManager.getApplicationInfo(
                packageName, 0
            )
        }
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(
                packageName, PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            packageManager.getPackageInfo(
                packageName, 0
            )
        }
        val versionName = packageInfo.versionName
        val appName = packageManager.getApplicationLabel(applicationInfo)
//        val appIcon = packageManager.getApplicationIcon(applicationInfo)

        return AppInfoEntity(
            appName = appName.toString(), appVersionName = versionName
        )
    }
}