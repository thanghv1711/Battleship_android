package fu.prm391.example.event;

import android.app.Dialog;
import android.view.View;

public class DRegisterBtnCancelOnClickListener implements View.OnClickListener {

    private Dialog dialog;      // Register dialog

    public DRegisterBtnCancelOnClickListener(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
}
