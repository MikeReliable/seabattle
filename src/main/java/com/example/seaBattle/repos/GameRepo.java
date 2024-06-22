package com.example.seaBattle.repos;

import com.example.seaBattle.objects.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<Game, Long> {
    Game findByGameId(Long gameId);
}
