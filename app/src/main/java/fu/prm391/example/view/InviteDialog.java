package fu.prm391.example.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.event.DInviteBtnCancelOnClickListener;
import fu.prm391.example.event.DInviteEventInviteAcceptedListener;
import fu.prm391.example.event.DInviteEventInviteRefusedListener;

public class InviteDialog extends Dialog implements IActivity {
    private Activity activity;

    private ImageView imgLoad;
    private Button btnCancel;

    private SocketCommon mSocket;

    private List<Player> players;
    private int countdown;
    private String idEnemy;

    public InviteDialog(Activity activity, List<Player> players, String idEnemy) {
        super(activity);
        this.activity = activity;
        this.players = players;
        this.idEnemy = idEnemy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_invite);
        getViewById();
        configureComponent();
        implementEvent();
        loadData();
    }

    @Override
    public void configureScreen() {
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void getViewById() {
        imgLoad = findViewById(R.id.dInviteImgLoad);
        btnCancel = findViewById(R.id.dInviteBtnCancel);
    }

    @Override
    public void configureComponent() {
        // set layout size
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // get socket
        mSocket = ((ApplicationCustom)activity.getApplicationContext()).getmSocket();
    }

    @Override
    public void loadData() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                countdown = 30;
                final Drawable d = imgLoad.getDrawable();
                while (true) {
                    try {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (d instanceof AnimatedVectorDrawableCompat) {
                                    AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat)d;
                                    avd.start();
                                } else if (d instanceof AnimatedVectorDrawable) {
                                    AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
                                    avd.start();
                                }
                                btnCancel.setText("CANCEL (" + countdown + "s)");
                            }
                        });
                        Thread.sleep(1000);
                        countdown--;
                        if (countdown < 0) {
                            dismiss();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public void implementEvent() {
        btnCancel.setOnClickListener(new DInviteBtnCancelOnClickListener(this, idEnemy));
        mSocket.listen(EventSocket.EVENT_SERVER_ACCEPT_INVITE, new DInviteEventInviteAcceptedListener(activity, this, players));
        mSocket.listen(EventSocket.EVENT_SERVER_REFUSE_INVITE, new DInviteEventInviteRefusedListener(activity, this));
    }
}
