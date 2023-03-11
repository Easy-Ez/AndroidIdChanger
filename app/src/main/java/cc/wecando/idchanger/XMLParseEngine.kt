package cc.wecando.idchanger

import android.util.Log
import android.util.Xml
import androidx.annotation.WorkerThread
import cc.wecando.idchanger.entity.SettingsSecureIdEntity
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

class XMLParseEngine : IParseEngine {
    @WorkerThread
    override fun parse(inputStream: InputStream): List<SettingsSecureIdEntity> {
        val list = mutableListOf<SettingsSecureIdEntity>()
        val parser = Xml.newPullParser()
        parser.setInput(inputStream, null)
        var parserEventType = parser.eventType
        while (parserEventType != XmlPullParser.END_DOCUMENT) {
            if (parserEventType == XmlPullParser.START_TAG && parser.name.equals("setting")) {
                val item = parseItem(parser)
                Log.i("XMLParseEngine", "item:${item}")
                list.add(item)
            }
            parserEventType = parser.next()
        }
        return list
    }
}