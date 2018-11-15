package fu.prm391.example.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.configure.SocketImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationCustom app = (ApplicationCustom) getApplicationContext();
        app.setmSocket(new SocketImpl("http://mid9life.com:3000"));
//        app.setmSocket(new SocketImpl("http://10.22.172.233:3000"));
        SocketCommon mSocket = app.getmSocket();
        mSocket.connect();

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
