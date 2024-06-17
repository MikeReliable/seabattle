package com.example.seaBattle.objects;

import javax.persistence.*;

@Entity
@Table
public class Cell {

    @Id
    @Column(name = "cell_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cellId;

    private int coordinateX;
    private int coordinateY;
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="player_id")
    private Player player;

    public Cell() {
    }

    public Long getCellId() {
        return cellId;
    }

    public void setCellId(Long cellId) {
        this.cellId = cellId;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
