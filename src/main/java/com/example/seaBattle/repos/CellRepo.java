package com.example.seaBattle.repos;

import com.example.seaBattle.objects.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepo extends JpaRepository<Cell, Long> {
}
