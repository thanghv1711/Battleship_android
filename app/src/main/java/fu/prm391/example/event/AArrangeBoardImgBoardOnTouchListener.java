package fu.prm391.example.event;

import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import fu.prm391.example.configure.Constant;
import fu.prm391.example.entity.Ship;
import fu.prm391.example.view.ArrangeBoardActivity;

public class AArrangeBoardImgBoardOnTouchListener implements View.OnTouchListener {
    private int tileSize;
    private LinearLayout rootLayout;
    private Ship ship;

    private int _xDelta;
    private int _yDelta;

    private int leftMarginLimitation;
    private int topMarginLimitation;

    public AArrangeBoardImgBoardOnTouchListener(int tileSize, LinearLayout rootLayout, Ship ship) {
        this.tileSize = tileSize;
        this.rootLayout = rootLayout;
        this.ship = ship;

        this.leftMarginLimitation = (Constant.BOARD_COLS - ship.getWidth()) * tileSize;
        this.topMarginLimitation = (Constant.BOARD_ROWS - ship.getHeight()) * tileSize;
    }

    private void clearShipCoordinates() {
        for(int row = ship.getRow(); row < ship.getRow() + ship.getHeight(); row++) {
            for(int col = ship.getCol(); col < ship.getCol() + ship.getWidth(); col++) {
                ArrangeBoardActivity.ShipCoordinates[row][col] = false;
            }
        }

    }

    private void arrangeShipCoordinates() {
        for(int row = ship.getRow(); row < ship.getRow() + ship.getHeight(); row++) {
            for(int col = ship.getCol(); col < ship.getCol() + ship.getWidth(); col++) {
                ArrangeBoardActivity.ShipCoordinates[row][col] = true;
            }
        }

    }

    private boolean canMove(int r, int c) {
        // clear coordinate of ship to check
        clearShipCoordinates();

        for(int row = r; row < r + ship.getHeight(); row++) {
            for(int col = c; col < c + ship.getWidth(); col++) {
                if(ArrangeBoardActivity.ShipCoordinates[row][col]) {    // in case there's another ship on this coords
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        final int x_onClickPoint = (int) motionEvent.getRawX();
        final int y_onClickPoint = (int) motionEvent.getRawY();
        RelativeLayout.LayoutParams params;

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = x_onClickPoint - params.leftMargin;
                _yDelta = y_onClickPoint - params.topMargin;

                break;
            case MotionEvent.ACTION_MOVE:
                params = (RelativeLayout.LayoutParams) view.getLayoutParams();

                // move
                params.leftMargin = x_onClickPoint - _xDelta;
                params.topMargin = y_onClickPoint - _yDelta;

                if(params.leftMargin < 0) {
                    params.leftMargin = 0;
                } else if (params.leftMargin > this.leftMarginLimitation) {
                    params.leftMargin = this.leftMarginLimitation;
                }

                if(params.topMargin < 0) {
                    params.topMargin = 0;
                } else if (params.topMargin > this.topMarginLimitation) {
                    params.topMargin = this.topMarginLimitation;
                }

                view.setLayoutParams(params);

                break;
            case MotionEvent.ACTION_UP:
                params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    // toa do moi tam thoi
                    int x_onNewCol = (params.leftMargin + tileSize / 2) / tileSize;
                    int y_onNewRow = (params.topMargin + tileSize / 2) / tileSize;

                    if(x_onNewCol < 0) {
                        x_onNewCol = 0;
                    } else if (x_onNewCol > Constant.BOARD_COLS - ship.getWidth()) {
                        x_onNewCol = Constant.BOARD_COLS - ship.getWidth();
                    }

                    if(y_onNewRow < 0) {
                        y_onNewRow = 0;
                    } else if (y_onNewRow > Constant.BOARD_ROWS - ship.getHeight()) {
                        y_onNewRow = Constant.BOARD_ROWS - ship.getHeight();
                    }

                    if(canMove(y_onNewRow, x_onNewCol)) {
                        // di chuyen den toa do moi
                        ship.setRow(y_onNewRow);
                        ship.setCol(x_onNewCol);
                        // set vi tri
                        params.leftMargin = x_onNewCol * tileSize;
                        params.topMargin = y_onNewRow * tileSize;
                    } else {
                        // set vi tri
                        params.leftMargin = ship.getCol() * tileSize;
                        params.topMargin = ship.getRow() * tileSize;
                    }
                    view.setLayoutParams(params);

                    // update lai toa do
                    arrangeShipCoordinates();

                    break;

        }
        //rootLayout.invalidate();
        return true;
    }
}
