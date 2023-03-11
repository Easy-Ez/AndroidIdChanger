package cc.wecando.idchanger.entity

import android.graphics.drawable.Drawable

data class AppEnhanceInfoEntity(
    val appName: String,
    val packageName: String,
    val appVersionName: String,
    val appIcon: Drawable,
    val originSSAID: String,
    val modifySSAID: String?
) {
    /**
     * 是否修改过
     */
    fun isModify() = modifySSAID != null && originSSAID == modifySSAID
}
