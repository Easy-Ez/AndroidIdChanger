package cc.wecando.idchanger

import android.util.Xml
import org.junit.Assert.*
import org.junit.Test
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream("settings_ssaid.xml")
        inputStream?.use { it ->
            val byteArray = ByteArray(4)
            it.read(byteArray, 0, 4)
            println(byteArray.joinToString { it.toString(radix = 16).padStart(2, '0') })
        }
    }
}