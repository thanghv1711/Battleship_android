package fu.prm391.example.event;

import android.app.Activity;
import android.view.View;

import fu.prm391.example.view.LoginDialog;

public class ALoginBtnPlayOnClickListener implements View.OnClickListener {

    private Activity activity;  // login activity

    public ALoginBtnPlayOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        // => login dialog
        LoginDialog ld = new LoginDialog(activity);
        ld.show();
    }
}
