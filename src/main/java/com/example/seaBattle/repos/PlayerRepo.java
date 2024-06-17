package com.example.seaBattle.repos;

import com.example.seaBattle.objects.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepo extends JpaRepository<Player, Long> {
    Player findByPlayerId(Long playerI);
}
