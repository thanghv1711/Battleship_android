package fu.prm391.example.event;

import android.app.Dialog;
import android.util.Log;
import android.view.View;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;

public class DInviteBtnCancelOnClickListener implements View.OnClickListener {

    private Dialog dialog;  // invite dialog
    private String idEnemy;

    public DInviteBtnCancelOnClickListener(Dialog dialog, String idEnemy) {
        this.dialog = dialog;
        this.idEnemy = idEnemy;
    }

    @Override
    public void onClick(View v) {
        SocketCommon mSocket = ((ApplicationCustom) dialog.getContext().getApplicationContext()).getmSocket();
        mSocket.sendEvent(EventSocket.EVENT_CLIENT_CANCEL_INVITE, idEnemy);
        dialog.dismiss();
    }
}
