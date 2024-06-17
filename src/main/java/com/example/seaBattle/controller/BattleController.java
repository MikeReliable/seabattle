package com.example.seaBattle.controller;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Game;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.objects.Ship;
import com.example.seaBattle.repos.CellRepo;
import com.example.seaBattle.repos.ShipRepo;
import com.example.seaBattle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    private CellRepo cellRepo;

    @Autowired
    private ShipRepo shipRepo;

    @PostMapping(path = "/startGame")
    public Long startingGame() {
        Game game = battleService.gameActivate();
        System.out.println("Начало новой игры");
        Long id = game.getGameId();
        System.out.println("Идентификатор игры - " + id);
        return id;
    }

    @PostMapping(path = "/putShip")
    public String putShip(@ModelAttribute Player player,
                          @ModelAttribute int coordinateX,
                          @ModelAttribute int coordinateY,
                          @ModelAttribute boolean horizontal) {
        Player actualPlayer = battleService.findPlayerById(player.getPlayerId());
        if (actualPlayer.getShipSet().isEmpty()) {
            byte length = 4;
            Ship ship = new Ship();
            ship.setAlive(true);
            ship.setLength(length);
            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY) != null) {
                if (horizontal) {
                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + length - 1, coordinateY) != null) {
                        for (int i = 0; i < length; i++) {
                            Cell cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY);
                            cell.setStatus("x");
                            cellRepo.save(cell);
                        }
                    } else {
                        System.out.println("Корабль не может выходить за пределы поля 10х10");
                    }
                } else {
                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + length - 1) != null) {
                        for (int i = 0; i < length; i++) {
                            Cell cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i);
                            cell.setStatus("x");
                            cellRepo.save(cell);
                        }
                    } else {
                        System.out.println("Корабль не может выходить за пределы поля 10х10");
                    }
                }
            }
        }
        return "ok";
    }
}
