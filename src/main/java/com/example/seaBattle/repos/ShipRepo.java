package com.example.seaBattle.repos;

import com.example.seaBattle.objects.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepo extends JpaRepository<Game, Long> {
}
