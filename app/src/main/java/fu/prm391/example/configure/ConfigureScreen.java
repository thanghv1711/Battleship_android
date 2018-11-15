package fu.prm391.example.configure;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

public class ConfigureScreen {

    private static final int SCREEN_ACTIVITY = 1;
    private static final int SCREEN_DIALOG = 2;

    private Activity activity;
    private Dialog dialog;
    private int screenId;

    public ConfigureScreen(Object screen) {
        if (screen instanceof Activity) {
            this.activity = (Activity) screen;
            screenId = SCREEN_ACTIVITY;
        } else if (screen instanceof Dialog){
            this.dialog = (Dialog) screen;
            screenId = SCREEN_DIALOG;
        }
    }

    public void configure() {
        // => hide status bar
        View decorView;
        if (screenId == SCREEN_ACTIVITY) {
            decorView = activity.getWindow().getDecorView();
        } else {
            decorView = dialog.getWindow().getDecorView();
        }
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
