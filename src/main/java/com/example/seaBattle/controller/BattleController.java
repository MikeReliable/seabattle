package com.example.seaBattle.controller;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Game;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.objects.Ship;
import com.example.seaBattle.repos.CellRepo;
import com.example.seaBattle.repos.GameRepo;
import com.example.seaBattle.repos.PlayerRepo;
import com.example.seaBattle.repos.ShipRepo;
import com.example.seaBattle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    GameRepo gameRepo;

    @Autowired
    private CellRepo cellRepo;

    @Autowired
    private ShipRepo shipRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @PostMapping(path = "/startGame")
    public Long startingGame() {
        Game game = battleService.gameActivate();
        System.out.println("Начало новой игры");
        Long id = game.getGameId();
        System.out.println("Идентификатор игры - " + id);
        return id;
    }

    @PostMapping(path = "/putShip")
    public String putShip(@RequestParam Long gameId,
                          @RequestParam Long playerId,
                          @RequestParam int coordinateX,
                          @RequestParam int coordinateY,
                          @RequestParam boolean horizontal) {
        if (gameRepo.findById(gameId).isPresent()) {
            if (battleService.findPlayerByPlayerIdAndGameGameId(playerId, gameId) != null) {
                Player actualPlayer = battleService.findPlayerByPlayerIdAndGameGameId(playerId, gameId);
                byte length = 0;
                switch (actualPlayer.getShipSet().size()) {
                    case 0:
                        length = 4;
                        break;
                    case 1:
                    case 2:
                        length = 3;
                        break;
                    case 3:
                    case 4:
                    case 5:
                        length = 2;
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        length = 1;
                        break;
                }
                if (actualPlayer.getShipSet().size() < 10) {
                    Ship ship = new Ship();
                    ship.setAlive(true);
                    ship.setLength(length);
                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY) != null) {
                        Cell cell;
                        List<Cell> shipSides = new ArrayList<>();
                        if (horizontal) {
                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + length - 1, coordinateY) != null) {
                                boolean setShip = true;
                                for (int i = 0; i < length; i++) {
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY);
                                        if (findShipsIntersections(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }

                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY - 1) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY - 1);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY + 1) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY + 1);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                }
                                if (setShip) {
                                    actualPlayer.getShipSet().add(ship);
                                    playerRepo.save(actualPlayer);
                                    return "Корабль успешно установлен";
                                }
                            } else {
                                System.out.println("Корабль не может выходить за пределы поля 10х10");
                            }
                        } else {
                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + length - 1) != null) {
                                boolean setShip = true;
                                for (int i = 0; i < length; i++) {
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY - i) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY - i);
                                        if (findShipsIntersections(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY - i + 1) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY - i + 1);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY - i) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY - i);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY - i) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY - i);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY - i - 1) != null) {
                                        cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY - i - 1);
                                        if (findShipsContacts(cell, shipSides)) {
                                            setShip = false;
                                            break;
                                        }
                                    }
                                }
                                if (setShip) {
                                    actualPlayer.getShipSet().add(ship);
                                    playerRepo.save(actualPlayer);
                                    return "Корабль успешно установлен";
                                }
                            } else {
                                System.out.println("Корабль не может выходить за пределы поля 10х10");
                            }
                        }
                    }
                }
                return "Все корабли игрока расставлены";
            }
            return "Игрок не найден";
        }
        return "Игра не найдена";
    }

    private boolean findShipsIntersections(Cell cell, List<Cell> shipSides) {
        boolean reaction = false;
        if (cell.getStatus().equals("*")) {
            cell.setStatus("x");
            cellRepo.save(cell);
            shipSides.add(cell);
        } else {
            for (Cell shipSide : shipSides) {
                shipSide.setStatus("*");
                cellRepo.save(shipSide);
            }
            System.out.println("Корабли не могуг соприкасаться");
            reaction = true;
        }
        return reaction;
    }

    private boolean findShipsContacts(Cell cell, List<Cell> shipSides) {
        boolean reaction = false;
        if (cell.getStatus().equals("*")) {
            cell.setStatus("-");
            cellRepo.save(cell);
            shipSides.add(cell);
        } else {
            for (Cell shipSide : shipSides) {
                shipSide.setStatus("*");
            }
            System.out.println("Корабли не могуг соприкасаться");
            reaction = true;
        }
        return reaction;
    }
}
