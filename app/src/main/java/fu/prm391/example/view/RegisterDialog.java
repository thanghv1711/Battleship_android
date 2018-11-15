package fu.prm391.example.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

import fu.prm391.example.event.DRegisterBtnCancelOnClickListener;
import fu.prm391.example.event.DRegisterBtnRegisterOnClickListener;

public class RegisterDialog extends Dialog implements IActivity {
    private LoginDialog loginDialog;    // login dialog

    private Button btnRegister;
    private Button btnCancel;

    public RegisterDialog(Activity activity, Dialog dialog) {
        super(activity);
        this.loginDialog = (LoginDialog) dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);
        getViewById();
        configureComponent();
        implementEvent();
    }

    @Override
    public void configureScreen() {
    }

    @Override
    public void getViewById() {
        btnRegister = findViewById(R.id.dRegisterBtnRegister);
        btnCancel = findViewById(R.id.dRegisterBtnCancel);
    }

    @Override
    public void configureComponent() {
        // set layout size
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void implementEvent() {
        btnRegister.setOnClickListener(new DRegisterBtnRegisterOnClickListener(loginDialog, this));
        btnCancel.setOnClickListener(new DRegisterBtnCancelOnClickListener(this));
    }
}
