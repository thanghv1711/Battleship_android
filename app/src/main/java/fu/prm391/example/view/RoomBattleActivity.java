package fu.prm391.example.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.event.ARoomBattleEventChangeStatusPlayerListener;
import fu.prm391.example.event.ARoomBattleFabChatOnClickListener;
import fu.prm391.example.event.ARoomBattleEventGetListPlayersListener;
import fu.prm391.example.event.ARoomBattleEventAddNewPlayerListener;
import fu.prm391.example.event.ARoomBattleEventInvitedBattleListener;
import fu.prm391.example.event.ARoomBattleEventPlayerLeaveListener;

public class RoomBattleActivity extends AppCompatActivity implements IActivity, NavigationView.OnNavigationItemSelectedListener {
    public static ListView lvPlayer;
    private DrawerLayout dlScreen;
    private NavigationView nvScreen;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton fabChat;
    private TextView lbName;

    private SocketCommon mSocket;

    public static List<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_battle);
        getViewById();
        configureComponent();
        loadData();
        implementEvent();
    }

    @Override
    public void configureScreen() {
        // hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // show action bar
        getSupportActionBar().show();

        // set title Action bar
        getSupportActionBar().setTitle(R.string.titleBarRoomBattleActivity);
    }

    @Override
    public void getViewById() {
        lvPlayer = findViewById(R.id.aRoomBattleLvPlayer);
        dlScreen = findViewById(R.id.aRoomBattleScreen);
        nvScreen = findViewById(R.id.aRoomBattleNvScreen);
        fabChat = findViewById(R.id.aRoomBattleFabChat);
        lbName = findViewById(R.id.dmMenuLbName);
    }

    @Override
    public void configureComponent() {
        // drawer menu toggle
        toggle = new ActionBarDrawerToggle(this, dlScreen, R.string.toggleOpenRoom, R.string.toggleCloseRoom);
        dlScreen.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // get socket
        mSocket = ((ApplicationCustom)this.getApplicationContext()).getmSocket();
        // test
        fabChat.setVisibility(View.GONE);
    }

    @Override
    public void loadData() {
        mSocket.sendEvent(EventSocket.EVENT_CLIENT_GET_PLAYER_IN_ROOM);
    }

    @Override
    public void implementEvent() {
        //nvScreen.setNavigationItemSelectedListener(new ActivityRoomNavigationItemSelectedListener(this));
        fabChat.setOnClickListener(new ARoomBattleFabChatOnClickListener(this));
        nvScreen.setNavigationItemSelectedListener(this);
        mSocket.listen(EventSocket.EVENT_SERVER_GET_PLAYER_IN_ROOM, new ARoomBattleEventGetListPlayersListener(this, lvPlayer));
        mSocket.listen(EventSocket.EVENT_SERVER_NEW_PLAYER_ONLINE, new ARoomBattleEventAddNewPlayerListener(this, lvPlayer));
        mSocket.listen(EventSocket.EVENT_SERVER_INVITED_PLAYER, new ARoomBattleEventInvitedBattleListener(this, lvPlayer));
        mSocket.listen(EventSocket.EVENT_SERVER_PLAYER_LEAVE, new ARoomBattleEventPlayerLeaveListener(this,lvPlayer));
        mSocket.listen(EventSocket.EVENT_SERVER_CHANGE_STATUS_PLAYER, new ARoomBattleEventChangeStatusPlayerListener(this, lvPlayer));
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {       // check toggle click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileItem:                      // click profile item menu
                Toast.makeText(this, "Click profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.logoutItem:                       // click log out item menu
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
