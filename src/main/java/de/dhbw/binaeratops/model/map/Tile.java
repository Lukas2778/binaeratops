package de.dhbw.binaeratops.model.map;

public class Tile {
    int x;
    int y;
    String path;

    public Tile(int x, int y, String path) {
        this.x = x;
        this.y = y;
        this.path = path;
    }

    public Tile(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
