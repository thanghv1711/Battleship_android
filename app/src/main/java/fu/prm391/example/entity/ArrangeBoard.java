package fu.prm391.example.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fu.prm391.example.configure.Constant;

public class ArrangeBoard extends View {
    private int tileSize = 0;
    public Tile[][] tiles;

    public ArrangeBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.tiles = new Tile[Constant.BOARD_ROWS][Constant.BOARD_COLS];
        setFocusable(true);
        buildTiles();
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getLeftMargin(int col) {
        return tileSize * col;
    }

    public int getTopMargin(int row) {
        return tileSize * row;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    public void buildTiles() {
        for (int c = 0; c < Constant.BOARD_COLS; c++) {
            for (int r = 0; r < Constant.BOARD_ROWS; r++) {
                tiles[c][r] = new Tile(c, r);
            }
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int boardSize = Math.min(getHeight(), getWidth());

        this.tileSize = boardSize / Constant.BOARD_COLS;

        for (int c = 0; c < Constant.BOARD_COLS; c++) {
            for (int r = 0; r < Constant.BOARD_ROWS; r++) {
                int leftMargin = getLeftMargin(c);
                int topMargin = getTopMargin(r);

                tiles[c][r].draw(leftMargin, topMargin, tileSize, canvas);
            }
        }
    }
}
