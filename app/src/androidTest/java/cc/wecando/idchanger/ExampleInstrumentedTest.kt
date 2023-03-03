package cc.wecando.idchanger

import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log
import android.util.Xml
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.lsposed.hiddenapibypass.HiddenApiBypass
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("cc.wecando.idchanger", appContext.packageName)
        val resolvePullParserMethod = HiddenApiBypass.getDeclaredMethod(
            Xml::class.java, "resolvePullParser",
            /*, args */
            InputStream::class.java
        )
        Log.i("version", "version:${Build.VERSION.SDK_INT}")
        val parser = resolvePullParserMethod.invoke(
            null, appContext.assets.open("settings_ssaid.xml")
        ) as XmlPullParser
        var parserEventType = parser.eventType
        while (parserEventType != XmlPullParser.END_DOCUMENT) {
            if (parserEventType == XmlPullParser.START_TAG && parser.name.equals("setting")) {
                val id = parser.getAttributeValue(null, "id")
                val name = parser.getAttributeValue(null, "name")
                val packageName = parser.getAttributeValue(null, "package")
                Log.i("parser", "id:${id},name:${name},packageName:${packageName}")
            }
            parserEventType = parser.next()
        }

    }
}