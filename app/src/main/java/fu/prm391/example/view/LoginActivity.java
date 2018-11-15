package fu.prm391.example.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import fu.prm391.example.event.ALoginBtnPlayOnClickListener;

public class LoginActivity extends AppCompatActivity implements IActivity {
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViewById();
        implementEvent();
    }

    @Override
    public void configureScreen() {
        // hide status bar and action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    @Override
    public void getViewById() {
        btnPlay = findViewById(R.id.aLoginBtnPlay);
    }

    @Override
    public void configureComponent() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void implementEvent() {
        btnPlay.setOnClickListener(new ALoginBtnPlayOnClickListener(this));
    }

    @Override
    public void onBackPressed() {
    }
}
