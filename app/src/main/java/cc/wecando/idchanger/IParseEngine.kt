package cc.wecando.idchanger

import androidx.annotation.WorkerThread
import cc.wecando.idchanger.entity.SettingsSecureIdEntity
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

interface IParseEngine {
    /**
     * parse content, whatever abx or xml file
     */
    @WorkerThread
    fun parse(inputStream: InputStream): List<SettingsSecureIdEntity>

    /**
     * default method
     * convert element to SettingsSecureIdEntity
     */
    fun parseItem(parser: XmlPullParser) = SettingsSecureIdEntity(
        id = parser.getAttributeValue(null, "id"),
        name = parser.getAttributeValue(null, "name"),
        packageName = parser.getAttributeValue(null, "package"),
        value = parser.getAttributeValue(null, "value"),
        defaultValue = parser.getAttributeValue(null, "defaultValue"),
        defaultSysSet = parser.getAttributeValue(null, "defaultSysSet"),
        tag = parser.getAttributeValue(null, "tag"),
    )
}