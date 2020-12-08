package ti.bottomsheet

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.appcelerator.kroll.KrollDict
import org.appcelerator.kroll.KrollPropertyChange
import org.appcelerator.kroll.KrollProxy
import org.appcelerator.kroll.KrollProxyListener
import org.appcelerator.kroll.annotations.Kroll
import org.appcelerator.titanium.TiApplication
import org.appcelerator.titanium.proxy.TiViewProxy
import org.appcelerator.titanium.util.TiConvert
import org.appcelerator.titanium.view.TiUIView

@Kroll.proxy(creatableInModule = TiBottomsheetModule::class, propertyAccessors = [
    Properties.CANCELABLE,
    Properties.BACKGROUND_COLOR
])
class DialogProxy: KrollProxy(), KrollProxyListener {
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var nestedScrollView: NestedScrollView? = null
    private var titaniumView: TiUIView? = null

    init {
        defaultValues[Properties.CANCELABLE] = true

        nestedScrollView = NestedScrollView(Utils.getContext()).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundColor(Utils.getColor(this@DialogProxy.properties, "backgroundColor"))
        }

        bottomSheetDialog = BottomSheetDialog(TiApplication.getAppCurrentActivity()).apply {
            dismissWithAnimation = true
            setCancelable(Utils.getBoolean(this@DialogProxy.properties, "cancelable", true))
            setCanceledOnTouchOutside(Utils.getBoolean(this@DialogProxy.properties, "canceledOnTouchOutside", true))
            setOnShowListener {
                this@DialogProxy.fireEvent("open", null)
            }
            setOnDismissListener {
                this@DialogProxy.fireEvent("close", null)
                nestedScrollView?.removeAllViews()
                bottomSheetDialog = null
                nestedScrollView = null
                titaniumView = null
            }
        }

        setModelListener(this)
    }

    override fun propertiesChanged(krollPropertyChangeList: MutableList<KrollPropertyChange>?, krollProxy: KrollProxy?) {}
    override fun listenerAdded(p0: String?, p1: Int, p2: KrollProxy?) {}
    override fun listenerRemoved(p0: String?, p1: Int, p2: KrollProxy?) {}

    override fun processProperties(krollDict: KrollDict?) {
        if (krollDict == null || krollDict.keys.isEmpty()) {
            return
        }

        for (key in krollDict.keys) {
            handleProperty(key, krollDict[key])
        }
    }

    override fun propertyChanged(name: String?, oldValue: Any?, newValue: Any?, krollProxy: KrollProxy?) {
        if (name != null && newValue != null) {
            handleProperty(name, newValue)
        }
    }

    private fun handleProperty(key: String, value: Any?) {
        when(key) {
            Properties.VIEW -> {
                if (nestedScrollView != null && bottomSheetDialog != null) {
                    titaniumView = (value as TiViewProxy).orCreateView
                    nestedScrollView!!.removeAllViews()
                    nestedScrollView!!.addView(titaniumView?.outerView)
                    nestedScrollView!!.requestLayout()
                    nestedScrollView!!.invalidate()
                    bottomSheetDialog!!.setContentView(nestedScrollView!!)
                }
            }
            Properties.PEEK_HEIGHT -> bottomSheetDialog?.behavior?.peekHeight = TiConvert.toInt(value)
            Properties.SHEET_STATE -> bottomSheetDialog?.behavior?.state = TiConvert.toInt(value)
            Properties.BACKGROUND_COLOR -> nestedScrollView?.setBackgroundColor(TiConvert.toColor(value as String))
            Properties.CANCELABLE -> TiConvert.toBoolean(value).also {
                bottomSheetDialog?.setCancelable(it)
                bottomSheetDialog?.setCanceledOnTouchOutside(it)
            }
        }
    }

    @Kroll.method
    fun show() {
        bottomSheetDialog?.show()
    }

    @Kroll.method
    fun hide() {
        if (this.properties.getBoolean(Properties.CANCELABLE)) bottomSheetDialog?.cancel() else bottomSheetDialog?.dismiss()
    }
}