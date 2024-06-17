package com.example.seaBattle.service;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Game;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.repos.GameRepo;
import com.example.seaBattle.repos.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BattleService {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PlayerRepo playerRepo;

    public Game gameActivate() {
        Game game = new Game();
        Player player1 = playerActivate();
        Player player2 = playerActivate();
        Set<Player> playerSet = new HashSet<>();
        playerSet.add(player1);
        playerSet.add(player2);
        game.setPlayerSet(playerSet);
        gameRepo.save(game);
        return game;
    }

    public Player playerActivate() {
        Player player = new Player();
        playerRepo.save(player);
        Set<Cell> cells = new HashSet<>();
        int fieldSize = 10;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Cell cell = new Cell();
                cell.setCoordinateX(i + 1);
                cell.setCoordinateY(j + 1);
                cell.setStatus("*");
                cell.setPlayer(player);
                cells.add(cell);
            }
        }
        player.setCellSet(cells);
        playerRepo.save(player);
        return player;
    }

    public Player findPlayerById(Long playerId){
        return playerRepo.findByPlayerId(playerId);
    }
}
