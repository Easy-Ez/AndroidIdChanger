package cc.wecando.idchanger

import android.os.Build
import android.util.Log
import android.util.Xml
import androidx.annotation.RequiresApi
import cc.wecando.idchanger.entity.SettingsSecureIdEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.lsposed.hiddenapibypass.HiddenApiBypass
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.nio.charset.StandardCharsets

class ABXParseEngine : IParseEngine {
    @RequiresApi(Build.VERSION_CODES.P)
    override suspend fun parse(inputStream: InputStream): List<SettingsSecureIdEntity> {
        return withContext(Dispatchers.IO) {
            val list = mutableListOf<SettingsSecureIdEntity>()
            val resolvePullParserMethod = HiddenApiBypass.getDeclaredMethod(
                Xml::class.java, "newBinaryPullParser"
            )

            val parser = resolvePullParserMethod.invoke(
                null
            ) as XmlPullParser

            parser.setInput(inputStream, StandardCharsets.UTF_8.name())
            var parserEventType = parser.eventType
            while (parserEventType != XmlPullParser.END_DOCUMENT) {
                if (parserEventType == XmlPullParser.START_TAG && parser.name.equals("setting")) {
                    val item = parseItem(parser)
                    Log.i("ABXParseEngine", "item:${item}")
                    list.add(item)
                }
                parserEventType = parser.next()
            }
            list
        }
    }
}