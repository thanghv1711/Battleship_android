package fu.prm391.example.event;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fu.prm391.example.view.LoginDialog;
import fu.prm391.example.view.R;

public class DRegisterBtnRegisterOnClickListener implements View.OnClickListener {

    private LoginDialog loginDialog;            // login dialog
    private Dialog dialog;                      // register dialog

    public DRegisterBtnRegisterOnClickListener(Dialog loginDialog, Dialog dialog) {
        this.dialog = dialog;
        this.loginDialog = (LoginDialog) loginDialog;
    }

    @Override
    public void onClick(View v) {
        EditText txtFirstname = dialog.findViewById(R.id.dRegisterTxtFirstname);
        EditText txtLastname = dialog.findViewById(R.id.dRegisterTxtLastname);
        EditText txtNickname = dialog.findViewById(R.id.dRegisterTxtNickname);
        EditText txtUsername = dialog.findViewById(R.id.dRegisterTxtUsername);
        EditText txtPassword = dialog.findViewById(R.id.dRegisterTxtPassword);

        String firstname = txtFirstname.getText().toString();
        if (firstname.isEmpty()) {
            Toast.makeText(dialog.getContext(),"Please input your first name!", Toast.LENGTH_LONG).show();
            return;
        }
        String lastname = txtLastname.getText().toString();
        if (lastname.isEmpty()) {
            Toast.makeText(dialog.getContext(),"Please input your last name!", Toast.LENGTH_LONG).show();
            return;
        }
        String nickname = txtNickname.getText().toString();
        if (nickname.isEmpty()) {
            Toast.makeText(dialog.getContext(),"Please input your nickname!", Toast.LENGTH_LONG).show();
            return;
        }
        String username = txtUsername.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(dialog.getContext(),"Please input your username!", Toast.LENGTH_LONG).show();
            return;
        }
        String password = txtPassword.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(dialog.getContext(),"Please input your password!", Toast.LENGTH_LONG).show();
            return;
        }
        // check username in database

        // register successfully
        loginDialog.setText(username, password);
        Toast.makeText(dialog.getContext(), "Register successfully!", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
}
