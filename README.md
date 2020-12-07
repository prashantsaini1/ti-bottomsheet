# Titanium Android Bottomsheet (Kotlin based)

## ti.bottomsheet

### Module Preview
[![Titanium Bottomsheet](https://img.youtube.com/vi/o8FL44QYQg4/hqdefault.jpg)](https://youtu.be/o8FL44QYQg4)

```
import TiBottomSheet from 'ti.bottomsheet';
let bottomSheetDialog;

function openBottomSheet(e) {
    const contentView = Alloy.createController('/sheet_view');
    contentView.closeView.addEventListener('click', closeBottomSheet);

    bottomSheetDialog = TiBottomSheet.createDialog({
        view: contentView.getView(),    // mandatory: make sure that this view doesn't contain top-level scroll-view/table-view/list-view || also only horizontal scroll-view/table-view/list-view are supported
        cancelable: true,               // optional: default true || use this to disable cancellations of dialog by back-press or drag-down or touch-outside
        backgroundColor: 'transparent', // optional: default `white` || for rounded-corners -> set backgroundColor to `transparent` + apply `bottomSheetDialogTheme`
        sheetState: TiBottomSheet.SHEET_STATE_HALF_EXPANDED, // optional: default possibly 2/3 of screen-height
        peekHeight: Ti.Platform.displayCaps.logicalDensityFactor * 100   // optional: default auto handled by library…… but pass pixels
    });
    bottomSheetDialog.addEventListener('open', e => {
        Ti.UI.createNotification({
            message: 'sheet opened',
            duration: Ti.UI.NOTIFICATION_DURATION_SHORT
        }).show();
    });
    bottomSheetDialog.addEventListener('close', e => {
        alert('sheet closed');
    });
    bottomSheetDialog.show();
}

function closeBottomSheet() {
    bottomSheetDialog.hide();
}
```
