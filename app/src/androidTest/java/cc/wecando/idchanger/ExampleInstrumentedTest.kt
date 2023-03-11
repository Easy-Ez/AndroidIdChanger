package cc.wecando.idchanger

import android.os.Build
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext(): Unit = runBlocking {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val parseEngine = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            ABXParseEngine()
        } else {
            XMLParseEngine()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            appContext.assets.open("settings_ssaid.xml")
        } else {
            appContext.assets.open("settings_ssaid_legacy.xml")
        }.use {
            val settings = parseEngine.parse(it)
            Log.i("debug", settings.joinToString())
            val fetcher = AppInfoFetcher(packageManager = appContext.packageManager)
            settings.forEach { setting ->
                val appInfo = fetcher.fetchAppInfo(setting.packageName)
                Log.i("debug", "applicationInfo#${appInfo}")
            }
        }


    }
}