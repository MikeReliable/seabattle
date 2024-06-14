package com.example.seaBattle.objects;

public class square {

    private final byte [][] square;
    private byte coordinateX;
    private byte coordinateY;
    private byte status;

    public square(byte[][] square) {
        this.square = new byte[10][10];
    }

    public byte getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(byte coordinateX) {
        this.coordinateX = coordinateX;
    }

    public byte getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(byte coordinateY) {
        this.coordinateY = coordinateY;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte[][] getSquare() {
        return square;
    }
}
