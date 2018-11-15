package fu.prm391.example.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.event.APlayGameEventEnemyFightListener;
import fu.prm391.example.event.APlayGameEventPlayerDisconnectListener;

public class PlayGameActivity extends AppCompatActivity implements IActivity{

    private ViewPager viewPager;
    private ViewPaperAdapter adapter;
    private int tileSize;
    private String idEnemy;
    private boolean turn;
    private SocketCommon mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        getViewById();
        configureComponent();
        loadData();
        implementEvent();
    }

    @Override
    public void configureScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    @Override
    public void getViewById() {
        viewPager = findViewById(R.id.aPlayGameViewPager);
    }

    @Override
    public void configureComponent() {
        mSocket = ((ApplicationCustom) this.getApplicationContext()).getmSocket();
    }

    @Override
    public void loadData() {
        Intent intent = getIntent();
        tileSize = intent.getIntExtra("tileSize", 0);
        idEnemy = intent.getStringExtra("idEnemy");
        turn = intent.getBooleanExtra("turn", false);
        adapter = new ViewPaperAdapter(getSupportFragmentManager(), tileSize, idEnemy, turn);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, true);
        if (turn) {
            NotifyDialog nd = new NotifyDialog(this, "Your Turn", 1, Color.BLACK);
            nd.show();
        }
    }

    @Override
    public void implementEvent() {
        mSocket.listen(EventSocket.EVENT_SERVER_FIGHT, new APlayGameEventEnemyFightListener(this, idEnemy));
        mSocket.listen(EventSocket.EVENT_SERVER_PLAYER_LEAVE, new APlayGameEventPlayerDisconnectListener(this));
    }

    @Override
    public void onBackPressed() {
    }

    public ViewPaperAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ViewPaperAdapter adapter) {
        this.adapter = adapter;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public class ViewPaperAdapter extends FragmentPagerAdapter {
        private int tileSize;
        private String idEnemy;
        private boolean turn;

        public ViewPaperAdapter(FragmentManager fm, int tileSize, String idenemy, boolean turn) {
            super(fm);
            this.tileSize = tileSize;
            this.idEnemy = idenemy;
            this.turn = turn;
        }

        @Override
        public Fragment getItem(int i) {
            PlayGameFragment pgf = new PlayGameFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i+1);
            bundle.putInt("tileSize", tileSize);
            bundle.putString("idEnemy", idEnemy);
            bundle.putBoolean("turn", turn);
            pgf.setArguments(bundle);
            return pgf;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
