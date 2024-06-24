package com.example.seaBattle.repos;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.objects.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepo extends JpaRepository<Ship, Long> {
    Ship findShipByCellListContainsAndPlayer(Cell cell, Player player);
}
