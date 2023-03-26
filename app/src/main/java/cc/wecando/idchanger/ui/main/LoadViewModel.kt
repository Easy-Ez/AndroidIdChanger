package cc.wecando.idchanger.ui.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.wecando.idchanger.*
import cc.wecando.idchanger.entity.AppEnhanceInfoEntity
import com.topjohnwu.superuser.Shell
import com.topjohnwu.superuser.io.SuFile
import com.topjohnwu.superuser.io.SuFileInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class LoadViewModel(private val application: Application) : AndroidViewModel(application) {
    companion object {
        const val FILE_PATH = "/data/system/users/0/settings_ssaid.xml"
    }

    val liveData: MutableLiveData<LiveDataState<List<AppEnhanceInfoEntity>>> = MutableLiveData()

    // 加载 android id 文件 => ssaidEntity
    // 分为Android 12 以下和其他,ABX 和 XML
    // ParseEngine parse = > SSAIDEntity
    // id name value package defaultValue defaultSysSet tag
    // 序列化为 Entity
    // 图标,包名,当前版本号,当前 Android id, 是否修改过
    //
    @OptIn(ExperimentalTime::class)
    fun loadAndroidId() {
        viewModelScope.launch(Dispatchers.Default) {
            liveData.postValue(LiveDataState.Loading(Unit))
            val parseEngine: IParseEngine = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ABXParseEngine()
            } else {
                XMLParseEngine()
            }
            val shell = Shell.getShell()
            if (shell.isRoot) {
                val duration = measureTime {
                    val inputStream = SuFileInputStream.open(SuFile.open(FILE_PATH))
                    inputStream.use { stream ->
                        val fetcher = AppInfoFetcher(packageManager = application.packageManager)
                        val enhanceInfoEntityList =
                            parseEngine.parse(stream).filter { settingsSecureIdEntity ->
                                settingsSecureIdEntity.packageName != "android"
                            }.map { settingsSecureIdEntity ->
                                async {
                                    val appInfo =
                                        fetcher.fetchAppInfo(settingsSecureIdEntity.packageName)
                                    AppEnhanceInfoEntity(
                                        appName = appInfo.appName,
                                        packageName = settingsSecureIdEntity.packageName,
                                        appVersionName = appInfo.appVersionName,
                                        originSSAID = settingsSecureIdEntity.value,
                                        currentSSAID = "",
                                    )
                                }
                            }.awaitAll().sortedBy { appEnhanceInfoEntity ->
                                appEnhanceInfoEntity.appName
                            }
                        liveData.postValue(LiveDataState.Success(enhanceInfoEntityList))
                    }
                }
                Log.d("LoadViewModel", "duration:${duration}")
            } else {
                liveData.postValue(LiveDataState.Error("root permission"))
            }
        }

    }
}