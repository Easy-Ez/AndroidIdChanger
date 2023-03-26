package cc.wecando.idchanger.entity

data class AppEnhanceInfoEntity(
    val appName: String,
    val packageName: String,
    val appVersionName: String,
    val originSSAID: String,
    val currentSSAID: String?
) {
    /**
     * 是否修改过
     */
    fun isModify() = currentSSAID != null && originSSAID != currentSSAID
}
