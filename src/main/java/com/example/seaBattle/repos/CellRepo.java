package com.example.seaBattle.repos;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepo extends JpaRepository<Cell, Long> {
    Cell findCellByPlayerAndCoordinateXAndCoordinateY(Player player, int coordinateX, int coordinateY);
}
