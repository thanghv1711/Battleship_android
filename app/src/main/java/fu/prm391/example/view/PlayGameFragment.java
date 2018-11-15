package fu.prm391.example.view;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.Common;
import fu.prm391.example.configure.Constant;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.GameBoard;
import fu.prm391.example.entity.Player;
import fu.prm391.example.entity.Ship;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayGameFragment extends Fragment {
    public static GameBoard myBoard;
    public static GameBoard opponentBoard;
    public static TextView lbYourPoint;
    public static TextView lbOpponentPoint;
    public static int opponentPoint;
    public static int yourPoint;

    private boolean[][] check;
    private int tileSize;
    private int position;
    private String idEnemy;
    public static boolean turn;
    private int countdown;

    private TextView lbTimer;
    private TextView lbYourPointTitle;
    private TextView lbOpponentPointTitle;

    private SocketCommon mSocket;

    public PlayGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_play_game, container, false);
        getViewById(view);
        position = getArguments().getInt("position");
        tileSize = getArguments().getInt("tileSize");
        idEnemy = getArguments().getString("idEnemy");
        turn = getArguments().getBoolean("turn");
        configureComponent();
        loadData();
        return view;
    }

    public void getViewById(View view) {
        myBoard = (GameBoard) view.findViewById(R.id.fPlayGameBoard);
        opponentBoard = (GameBoard) view.findViewById(R.id.fPlayGameBoard);
        lbYourPoint = (TextView) view.findViewById(R.id.fPlayGameLbYourPoint);
        lbOpponentPoint = (TextView) view.findViewById(R.id.fPlayGameLbOpponentPoint);

        lbTimer = (TextView) view.findViewById(R.id.fPlayGameLbTimer);
        lbYourPointTitle = (TextView) view.findViewById(R.id.fPlayGameLbYourPointTitle);
        lbOpponentPointTitle = (TextView) view.findViewById(R.id.fPlayGameLbOpponentPointTitle);
        mSocket = ((ApplicationCustom) getActivity().getApplicationContext()).getmSocket();
    }

    public void configureComponent() {
        if (position == 1) {
            lbTimer.setVisibility(View.GONE);
        } else if (position == 2){
            lbOpponentPoint.setVisibility(View.GONE);
            lbOpponentPointTitle.setVisibility(View.GONE);
            lbYourPoint.setVisibility(View.GONE);
            lbYourPointTitle.setVisibility(View.GONE);
            lbTimer.setVisibility(View.GONE);
        }
    }

    public void loadData() {
        check = new boolean[Constant.BOARD_ROWS][Constant.BOARD_COLS];
        Thread thread = new Thread() {
            @Override
            public void run() {
                countdown = 30;
                while (true){
                    try {
                        Thread.sleep(1000);
                        if (countdown < 0) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        yourPoint = 12;
        opponentPoint = 12;
        lbYourPoint.setText(yourPoint+"");
        lbOpponentPoint.setText(opponentPoint+"");

        if (position == 1) {
            final int[] myBoardImages = new int[Constant.BOARD_ROWS * Constant.BOARD_COLS];
            for(int i = 0; i < myBoardImages.length; i++) {
                int row = i / Constant.BOARD_ROWS;
                int col = i % Constant.BOARD_COLS;
                if(ArrangeBoardActivity.ShipCoordinates[row][col]) {
                    myBoardImages[i] = R.drawable.project_wave;
                } else {
                    myBoardImages[i] = R.drawable.project_tile;
                }
            }
            final GridImageAdapter myAdapter = new GridImageAdapter((PlayGameActivity) this.getActivity(),
                    myBoardImages, tileSize);
            myBoard.setAdapter(myAdapter);
        } else {
            int[] opponentBoardImages = new int[Constant.BOARD_ROWS * Constant.BOARD_COLS];
            for(int i = 0; i < opponentBoardImages.length; i++) {
                opponentBoardImages[i] = R.drawable.project_tile;
            }
            GridImageAdapter opponentAdapter = new GridImageAdapter((PlayGameActivity) this.getActivity(),
                    opponentBoardImages, tileSize);
            opponentBoard.setAdapter(opponentAdapter);
            opponentBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!turn) {
                        return;
                    }
                    int row = position / Constant.BOARD_ROWS;
                    int col = position % Constant.BOARD_COLS;
                    if (check[row][col]) {
                        return;
                    }
                    ImageView imageView = (ImageView) view;
                    imageView.setBackgroundColor(Color.rgb(102, 178, 255));
                    int point[] = {row, col};
                    check[row][col] = true;
                    mSocket.sendEvent(EventSocket.EVENT_CLIENT_FIGHT,
                            new Gson().toJson(point), idEnemy);
                    if(ArrangeBoardActivity.EnemyShipCoordinates[row][col]) {
                        imageView.setImageResource(R.drawable.project_fire);
                        opponentPoint--;
                        lbOpponentPoint.setText(opponentPoint+"");

                    } else {
                        imageView.setImageResource(R.drawable.project_wave);
                    }
                    if (opponentPoint == 0 ) {
                        NotifyDialog nd = new NotifyDialog(getActivity(), "VICTORY", 5, Color.YELLOW);
                        nd.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadDialog ld = new LoadDialog(getActivity(), "Back to Room Battle", 2);
                                ld.show();
                            }
                        }, 5000);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSocket.sendEvent(EventSocket.EVENT_CLIENT_END_GAME);
                                getActivity().finish();
                            }
                        }, 7000);
                    }
                    ((PlayGameActivity) getActivity()).getViewPager().setCurrentItem(0, true);
                    turn = !turn;
                }
            });
        }
    }

    public class GridImageAdapter extends BaseAdapter {
        private PlayGameActivity activity;
        // references to our images
        private int[] images;
        private int tileSize;

        public GridImageAdapter(PlayGameActivity activity, int[] images, int tileSize) {
            this.activity = activity;
            this.images = images;
            this.tileSize = tileSize;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int i) {
            return images[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView;
            if (view == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(activity);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(tileSize, tileSize));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                if(images[i] == R.drawable.project_tile) {
                    imageView.setBackgroundColor(Color.BLACK);
                    imageView.setPadding(1, 1, 1, 1);
                    imageView.setImageResource(images[i]);
                } else {
                    // set board image
                    OUTER:
                    for(Ship s: ArrangeBoardActivity.ships) {
                        //((BitmapDrawable)s.getImage().getDrawable()).getBitmap();
                        for(int j = 0; j < s.getWeight(); j++) {
                            if(i == Common.getPositionOnOneDimensionArray(s.getRow(), s.getCol(), Constant.BOARD_ROWS, j)) {

                                Bitmap sourceBitmap = BitmapFactory.decodeResource(activity.getResources(), s.getImageId());
                                imageView.setImageBitmap(Bitmap.createBitmap(sourceBitmap,
                                        j * sourceBitmap.getWidth() / s.getWeight(),
                                        0, sourceBitmap.getWidth() / s.getWeight(), sourceBitmap.getHeight()));
                                break OUTER;
                            }
                        }
                    }
                }
            } else {
                imageView = (ImageView) view;
            }
            return imageView;
        }
    }
}
