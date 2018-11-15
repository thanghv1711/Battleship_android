package fu.prm391.example.entity;

import android.widget.ImageView;

import java.io.Serializable;

public class Ship implements Serializable {
    // number of tile fill the ship
    private int weight;
    // width & height by number of tile
    private int width;
    private int height;
    // model to generate in screen
    private int imageId;
    // coordinate
    private int row;
    private int col;

    public Ship() {
    }

    public Ship(int weight, int width, int height, int imageId, int row, int col) {
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.imageId = imageId;
        this.row = row;
        this.col = col;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
