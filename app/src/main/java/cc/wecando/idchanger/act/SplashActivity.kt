package cc.wecando.idchanger.act

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cc.wecando.idchanger.BuildConfig
import cc.wecando.idchanger.MainActivity
import cc.wecando.idchanger.databinding.ActivitySplashBinding
import com.topjohnwu.superuser.Shell


/**
 * splash screen, get root access
 */
class SplashActivity : AppCompatActivity() {
    companion object {
        init {
            // Set settings before the main shell can be created
            Shell.enableVerboseLogging = BuildConfig.DEBUG
            Shell.setDefaultBuilder(
                Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR).setTimeout(10)
            )
        }
    }
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set the cutout mode either programmatically or by setting a style
        // see values-27 <item name="android:windowLayoutInDisplayCutoutMode">always</item>
//        val lp = window.attributes
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                lp.layoutInDisplayCutoutMode =
//                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
//            } else {
//                lp.layoutInDisplayCutoutMode =
//                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//            }
//            window.attributes = lp
//        }
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Shell.getShell { _ ->
            // The main shell is now constructed and cached
            // Exit splash screen and enter main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}