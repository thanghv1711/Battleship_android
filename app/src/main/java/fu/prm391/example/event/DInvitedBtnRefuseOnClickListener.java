package fu.prm391.example.event;

import android.app.Dialog;
import android.view.View;

import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;

public class DInvitedBtnRefuseOnClickListener implements View.OnClickListener {

    private Dialog dialog;
    private SocketCommon mSocket;
    private String idEnemy;

    public DInvitedBtnRefuseOnClickListener(Dialog dialog, SocketCommon mSocket, String idEnemy) {
        this.dialog = dialog;
        this.mSocket = mSocket;
        this.idEnemy = idEnemy;
    }

    @Override
    public void onClick(View v) {
        mSocket.sendEvent(EventSocket.EVENT_CLIENT_REFUSE_INVITED, idEnemy);
        dialog.dismiss();
    }
}
