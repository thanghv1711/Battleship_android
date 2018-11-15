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
import android.widget.ListView;
import android.widget.TextView;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.event.ARoomBattleEventChangeStatusPlayerListener;
import fu.prm391.example.event.DInvitedBtnAcceptOnClickListener;
import fu.prm391.example.event.DInvitedBtnRefuseOnClickListener;
import fu.prm391.example.event.DInvitedEventCancelInvitedListener;

public class InvitedDialog extends Dialog implements IActivity {
    private Activity activity;

    private ImageView imgLoad;
    private Button btnAccept;
    private Button btnRefuse;
    private TextView lbTitle;
    private ListView lvPlayer;

    private SocketCommon mSocket;

    private Player player;
    private int countdown;

    public InvitedDialog(Activity activity, Player player, ListView lvPlayer) {
        super(activity);
        this.activity = activity;
        this.player = player;
        this.lvPlayer = lvPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_invited);
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
        imgLoad = findViewById(R.id.dInvitedImgLoad);
        btnAccept = findViewById(R.id.dInvitedBtnAccept);
        btnRefuse = findViewById(R.id.dInvitedBtnRefuse);
        lbTitle = findViewById(R.id.dInvitedLbTitle);
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
        lbTitle.setText(player.getNickname() + " want to battle!!!");
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
                                btnRefuse.setText("Refuse (" + countdown + "s)");
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
        btnRefuse.setOnClickListener(new DInvitedBtnRefuseOnClickListener(this, mSocket, player.getId()));
        btnAccept.setOnClickListener(new DInvitedBtnAcceptOnClickListener(activity, this, player.getId(), lvPlayer));
        mSocket.listen(EventSocket.EVENT_SERVER_CANCEL_INVITED, new DInvitedEventCancelInvitedListener(activity, this));
    }


}
