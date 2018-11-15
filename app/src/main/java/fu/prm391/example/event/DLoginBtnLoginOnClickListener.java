package fu.prm391.example.event;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.view.R;

public class DLoginBtnLoginOnClickListener implements View.OnClickListener {
    private Dialog dialog;          // login dialog

    private EditText txtUsername;
    private EditText txtPassword;
    private CheckBox cbxRemember;

    private SharedPreferences pref;
    private SocketCommon mSocket;

    public DLoginBtnLoginOnClickListener(Dialog dialog, SocketCommon msocket) {
        this.dialog = dialog;
        this.mSocket = msocket;
    }

    @Override
    public void onClick(View v) {
        txtUsername = dialog.findViewById(R.id.dLoginTxtUsername);
        txtPassword = dialog.findViewById(R.id.dLoginTxtPassword);
        cbxRemember = dialog.findViewById(R.id.dLoginCbxRemember);

        String username = txtUsername.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(dialog.getContext(), "Please input username", Toast.LENGTH_LONG).show();
            return;
        }
//        String password = txtPassword.getText().toString();
//        if (password.isEmpty()) {
//            Toast.makeText(dialog.getContext(), "Please input password", Toast.LENGTH_LONG).show();
//            return;
//        }
        // check username,password in database

        // login successfully
        pref = dialog.getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (cbxRemember.isChecked()) {
            editor.putString("username", username);
//            editor.putString("password", password);
            editor.putBoolean("remember", true);
        } else {
            editor.putString("username", "");
//            editor.putString("password", "");
            editor.putBoolean("remember", false);
        }
        editor.apply();

        mSocket.sendEvent(EventSocket.EVENT_CLIENT_LOGIN, username);
    }
}
