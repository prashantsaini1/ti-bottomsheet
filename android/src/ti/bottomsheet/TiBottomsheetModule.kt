package ti.bottomsheet

import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.appcelerator.kroll.KrollModule
import org.appcelerator.kroll.annotations.Kroll.constant
import org.appcelerator.kroll.annotations.Kroll.module

@module(name = "TiBottomsheet", id = "ti.bottomsheet")
class TiBottomsheetModule: KrollModule() {
    companion object {
        @constant const val SHEET_STATE_EXPANDED = BottomSheetBehavior.STATE_EXPANDED
        @constant const val SHEET_STATE_HALF_EXPANDED = BottomSheetBehavior.STATE_HALF_EXPANDED
    }
}