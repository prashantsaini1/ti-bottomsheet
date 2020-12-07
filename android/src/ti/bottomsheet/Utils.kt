package ti.bottomsheet

import android.content.Context
import org.appcelerator.kroll.KrollDict
import org.appcelerator.titanium.TiApplication
import org.appcelerator.titanium.util.TiConvert
import org.appcelerator.titanium.util.TiRHelper

object Utils {
    @JvmStatic fun getContext(): Context {
        return TiApplication.getAppCurrentActivity()
    }

    @JvmStatic fun getR(path: String?): Int {
        return try {
            TiRHelper.getResource(path)
        } catch (exc: Exception) {
            -1
        }
    }

    @JvmStatic fun getString(krollDict: KrollDict, key: String, defaultValue: String): String {
        return if (krollDict.containsKeyAndNotNull(key)) {
            TiConvert.toString(krollDict[key])
        } else {
            TiConvert.toString(defaultValue)
        }
    }

    @JvmStatic fun getBoolean(krollDict: KrollDict, key: String, defaultValue: Any): Boolean {
        return if (krollDict.containsKeyAndNotNull(key)) {
            TiConvert.toBoolean(krollDict[key])
        } else {
            TiConvert.toBoolean(defaultValue)
        }
    }

    @JvmStatic fun getInt(krollDict: KrollDict, key: String, defaultValue: Any): Int {
        return if (krollDict.containsKeyAndNotNull(key)) {
            TiConvert.toInt(krollDict[key])
        } else {
            TiConvert.toInt(defaultValue)
        }
    }

    @JvmStatic fun getColor(krollDict: KrollDict, key: String): Int {
        return if (krollDict.containsKeyAndNotNull(key)) {
            TiConvert.toColor(krollDict[key] as String?)
        } else {
            TiConvert.toColor("white")
        }
    }
}