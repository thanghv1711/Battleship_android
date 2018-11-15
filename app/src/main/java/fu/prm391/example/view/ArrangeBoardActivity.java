package fu.prm391.example.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.Constant;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.ArrangeBoard;
import fu.prm391.example.entity.Ship;
import fu.prm391.example.event.AArrangeBoardEventGetEnemyShipCoordinatesListener;
import fu.prm391.example.event.AArrangeBoardImgBoardOnTouchListener;
import fu.prm391.example.event.APlayGameEventPlayerDisconnectListener;
import fu.prm391.example.event.ARoomBattleEventChangeStatusPlayerListener;

public class ArrangeBoardActivity extends AppCompatActivity implements IActivity {
    public static boolean[][] ShipCoordinates;
    public static boolean[][] EnemyShipCoordinates;
    public static Ship[] ships;
    private String idEnemy;

    private LinearLayout rootLayout;
    private ArrangeBoard board;
    private TextView lbTime;

    private SocketCommon mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_board);
        getViewById();
        configureComponent();
        loadData();
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
        board = (ArrangeBoard) findViewById(R.id.aArrangeBoardBoard);
        rootLayout = (LinearLayout) findViewById(R.id.aArrangeBoardScreen);
        lbTime = (TextView) findViewById(R.id.lbTime);
    }

    @Override
    public void configureComponent() {
        mSocket = ((ApplicationCustom) getApplicationContext()).getmSocket();
    }

    @Override
    public void loadData() {
        Intent intent = getIntent();
        idEnemy = intent.getStringExtra("idEnemy");
        // run prepare dialog
        PrepareDialog pd = new PrepareDialog(this);
        pd.show();

        // arrange ship
        ShipCoordinates = new boolean[Constant.BOARD_ROWS][Constant.BOARD_COLS];
        EnemyShipCoordinates = new boolean[Constant.BOARD_ROWS][Constant.BOARD_COLS];
        Handler handler = new Handler();
        handler.postDelayed(new delayDraw(this), 4000);
    }

    @Override
    public void implementEvent() {
        mSocket.listen(EventSocket.EVENT_SERVER_SEND_SHIP_COORS, new AArrangeBoardEventGetEnemyShipCoordinatesListener(this, idEnemy));
        mSocket.listen(EventSocket.EVENT_SERVER_PLAYER_LEAVE, new APlayGameEventPlayerDisconnectListener(this));

    }

    @Override
    public void onBackPressed() {
    }

    class delayDraw implements Runnable {
        private ArrangeBoardActivity activity;
        private int countdown;

        public delayDraw(ArrangeBoardActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            ships = new Ship[4];
            final int tileSize = board.getTileSize();
            for (int i = 0; i < ships.length; i++) {
                // init
                int imageViewId = 0;
                if (i == 0) {
                    ships[i] = new Ship(2, 2, 1, R.drawable.project_ship1_patrol_boat, 0, 0);
                    imageViewId = R.id.aArrangeBoardImgPatrolBoat;
                } else if (i == 1) {
                    ships[i] = new Ship(3, 3, 1, R.drawable.project_ship2_battleship, 2, 0);
                    imageViewId = R.id.aArrangeBoardImgBattleShip;
                } else if (i == 2) {
                    ships[i] = new Ship(3, 3, 1, R.drawable.project_ship3_cruiser, 4, 0);
                    imageViewId = R.id.aArrangeBoardImgCruiser;
                } else if (i == 3) {
                    ships[i] = new Ship(4, 4, 1, R.drawable.project_ship4_aircraft_carrier, 6, 0);
                    imageViewId = R.id.aArrangeBoardImgAircraftCarrier;
                }

                for (int row = ships[i].getRow(); row < ships[i].getRow() + ships[i].getHeight(); row++) {
                    for (int col = ships[i].getCol(); col < ships[i].getCol() + ships[i].getWidth(); col++) {
                        ShipCoordinates[row][col] = true;
                    }
                }

                ImageView image = activity.findViewById(imageViewId);
                final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
                params.width = tileSize * ships[i].getWidth();
                params.height = tileSize * ships[i].getHeight();
                params.leftMargin = tileSize * ships[i].getCol();
                params.topMargin = tileSize * ships[i].getRow();
                image.setLayoutParams(params);
                image.setOnTouchListener(new AArrangeBoardImgBoardOnTouchListener(tileSize, rootLayout, ships[i]));
            }

            Thread thread = new Thread() {
                @Override
                public void run() {
                    countdown = 30;
                    while (true) {
                        try{
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lbTime.setText(String.valueOf(countdown));
                                }
                            });
                            Thread.sleep(1000);
                            countdown--;
                            if (countdown < 0) {
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mSocket.sendEvent(EventSocket.EVENT_CLIENT_SEND_SHIP_COORS, new Gson().toJson(ArrangeBoardActivity.ShipCoordinates), idEnemy);
                }
            };
            thread.start();
        }
    }
}
