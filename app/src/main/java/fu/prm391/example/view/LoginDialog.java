package fu.prm391.example.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.event.DLoginEventLoginListener;
import fu.prm391.example.event.DLoginBtnLoginOnClickListener;
import fu.prm391.example.event.DLoginBtnRegisterOnClickListener;

public class LoginDialog extends Dialog implements IActivity {
    private Activity activity;

    private Button btnLogin;
    private Button btnRegister;
    private EditText txtUsername;
    private EditText txtPassword;
    private CheckBox cbxRemember;

    private SharedPreferences pref;
    private SocketCommon mSocket;

    public LoginDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        getViewById();
        configureComponent();
        loadData();
        implementEvent();
    }

    @Override
    public void configureScreen() {
    }

    @Override
    public void getViewById() {
        btnLogin = findViewById(R.id.dLoginBtnLogin);
        btnRegister = findViewById(R.id.dLoginBtnRegister);
        txtUsername = findViewById(R.id.dLoginTxtUsername);
        txtPassword = findViewById(R.id.dLoginTxtPassword);
        cbxRemember = findViewById(R.id.dLoginCbxRemember);
    }

    @Override
    public void configureComponent() {
        // set layout size
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // get socket
        mSocket = ((ApplicationCustom)activity.getApplicationContext()).getmSocket();
        // test
        TextView lbPassword = findViewById(R.id.dLoginLbPassword);
        lbPassword.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        txtPassword.setVisibility(View.GONE);
        cbxRemember.setVisibility(View.GONE);
    }

    @Override
    public void loadData() {
        // set username and password if remember
        pref = activity.getSharedPreferences("account", Context.MODE_PRIVATE);
        txtUsername.setText(pref.getString("username",""));
//        txtPassword.setText(pref.getString("password",""));
        cbxRemember.setChecked(pref.getBoolean("remember", false));
    }

    @Override
    public void implementEvent() {
        btnLogin.setOnClickListener(new DLoginBtnLoginOnClickListener(this, mSocket));
        btnRegister.setOnClickListener(new DLoginBtnRegisterOnClickListener(activity, this));
        mSocket.listen(EventSocket.EVENT_SERVER_CHECK_LOGIN, new DLoginEventLoginListener(activity));
    }

    // => set user, pass for login dialog after register successfully
    public void setText(String user, String pass) {
        txtUsername.setText(user);
        txtPassword.setText(pass);
    }
}
