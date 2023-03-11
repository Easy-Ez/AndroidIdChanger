package cc.wecando.idchanger.entity

data class SettingsSecureIdEntity(
    val id: String,
    val name: String,
    val packageName: String,
    val value: String,
    val defaultValue: String,
    val defaultSysSet: String,
    val tag: String
)