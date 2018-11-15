package fu.prm391.example.event;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import fu.prm391.example.view.LoginDialog;
import fu.prm391.example.view.RegisterDialog;

public class DLoginBtnRegisterOnClickListener implements View.OnClickListener {

    private Activity activity;  // login activity
    private Dialog dialog;      // login dialog

    public DLoginBtnRegisterOnClickListener(Activity activity, Dialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        // => register dialog
        RegisterDialog rd = new RegisterDialog(activity, dialog);
        rd.show();
    }
}
