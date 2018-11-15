package fu.prm391.example.entity;

import android.content.Context;

import android.util.AttributeSet;

import android.widget.GridView;

import fu.prm391.example.configure.Constant;

public class GameBoard extends GridView {


    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        setNumColumns(Constant.BOARD_COLS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
