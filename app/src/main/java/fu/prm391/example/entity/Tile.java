package fu.prm391.example.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private int col;
    private int row;

    private Rect tileRect;


    public Tile(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setTileRect(Rect tileRect) {
        this.tileRect = tileRect;
    }

    public Rect getTileRect() {
        return tileRect;
    }

    public void draw(int leftMargin, int topMargin, int tileSize, Canvas canvas) {
        Rect tileRect = new Rect(
                leftMargin,               // left
                topMargin,               // top
                leftMargin + tileSize,  // right
                topMargin + tileSize   // bottom
        );
        setTileRect(tileRect);

        Paint squareColor = new Paint();
        squareColor.setStyle(Paint.Style.FILL);
        squareColor.setColor(Color.rgb(102, 178, 255));
        canvas.drawRect(tileRect, squareColor);

        Paint border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setColor(Color.BLACK);
        canvas.drawRect(tileRect, border);
    }
}
