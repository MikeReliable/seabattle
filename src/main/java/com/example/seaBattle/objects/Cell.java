package com.example.seaBattle.objects;

import javax.persistence.*;
import java.util.Objects;

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
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

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

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(cellId, cell.cellId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellId);
    }
}
