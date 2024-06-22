package com.example.seaBattle.service;

import com.example.seaBattle.objects.Cell;
import com.example.seaBattle.objects.Game;
import com.example.seaBattle.objects.Player;
import com.example.seaBattle.objects.Ship;
import com.example.seaBattle.repos.CellRepo;
import com.example.seaBattle.repos.GameRepo;
import com.example.seaBattle.repos.PlayerRepo;
import com.example.seaBattle.repos.ShipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BattleService {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private CellRepo cellRepo;

    @Autowired
    private ShipRepo shipRepo;

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
        for (int i = fieldSize; i > 0; i--) {
            for (int j = 0; j < fieldSize; j++) {
                Cell cell = new Cell();
                cell.setCoordinateY(i);
                cell.setCoordinateX(j + 1);
                cell.setStatus("*");
                cell.setPlayer(player);
                cells.add(cell);
            }
        }
        player.setCellSet(cells);
        playerRepo.save(player);
        return player;
    }

    public String putPlayerShip(Long gameId, Long playerId, int coordinateX, int coordinateY, boolean horizontal) {
        if (gameRepo.findById(gameId).isPresent()) {
            if (findPlayerByPlayerIdAndGameGameId(playerId, gameId) != null) {
                Player actualPlayer = findPlayerByPlayerIdAndGameGameId(playerId, gameId);
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
                        Set<Cell> shipSides = new HashSet<>();
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
                                return "Корабль не может выходить за пределы поля 10х10";
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
                                    ship.setCellSet(shipSides);
                                    actualPlayer.getShipSet().add(ship);
                                    playerRepo.save(actualPlayer);
                                    return "Корабль успешно установлен";
                                }
                            } else {
                                return "Корабль не может выходить за пределы поля 10х10";
                            }
                        }
                    }
                    return "Заданная координата выходит за пределы поля 10х10";
                }
                return "Все корабли игрока расставлены";
            }
            return "Игрок не найден";
        }
        return "Игра не найдена";
    }

    public Player findPlayerByPlayerIdAndGameGameId(Long playerId, Long gameId) {
        return playerRepo.findPlayerByPlayerIdAndGameGameId(playerId, gameId);
    }

    private boolean findShipsIntersections(Cell cell, Set<Cell> shipSides) {
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

    private boolean findShipsContacts(Cell cell, Set<Cell> shipSides) {
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

    public String makeShoot(Long gameId, Long playerId, int coordinateX, int coordinateY) {
        if (gameRepo.findById(gameId).isPresent()) {
            Game game = gameRepo.findByGameId(gameId);
            if (findPlayerByPlayerIdAndGameGameId(playerId, gameId) != null) {
                Player actualPlayer = playerRepo.findDistinctByPlayerIdAndGameGameId(playerId, gameId);
                Player actualPlayerEnemy = findPlayerByPlayerIdAndGameGameId(playerId, gameId);
                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayerEnemy, coordinateX, coordinateY) != null) {
                    Cell cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayerEnemy, coordinateX, coordinateY);
                    if (!cell.getStatus().equals("x")) {
                        cell.setStatus("0");
                        cellRepo.save(cell);
                        game.setWhoseTurn(actualPlayerEnemy.getPlayerId());
                        gameRepo.save(game);
                        return "Мимо!";
                    } else {
                        if (!cell.getStatus().equals("-")) {
                            cell.setStatus("0");
                            cellRepo.save(cell);
                            game.setWhoseTurn(actualPlayerEnemy.getPlayerId());
                            gameRepo.save(game);
                            return "Мимо!";
                        } else {
                            cell.setStatus("+");
                            Ship ship = shipRepo.findByCellSetContaining(cell);
                            byte length = (byte) (ship.getLength() - 1);
                            ship.setLength(length);
                            if (length == 0) {
                                ship.setAlive(false);
                            }
                            cellRepo.save(cell);
                            shipRepo.save(ship);
                            byte totalLength = 0;
                            Set<Ship> shipSet = actualPlayerEnemy.getShipSet();
                            for (Ship ship1 : shipSet) {
                                totalLength += ship1.getLength();
                            }
                            if (totalLength != 0 && ship.isAlive()) {
                                game.setWhoseTurn(actualPlayer.getPlayerId());
                                gameRepo.save(game);
                                return "Ранен!";
                            } else {
                                if (totalLength != 0 && !ship.isAlive()) {
                                    game.setWhoseTurn(actualPlayer.getPlayerId());
                                    gameRepo.save(game);
                                    return "Убит!";
                                } else {
                                    actualPlayer.setWinner(true);
                                    playerRepo.save(actualPlayer);
                                    game.setInactive(true);
                                    gameRepo.save(game);
                                    return "Победа!";
                                }
                            }
                        }
                    }
                }
                return "Заданная координата выходит за пределы поля 10х10";
            }
            return "Игрок не найден";
        }
        return "Игра не найдена";
    }
}
// Обозначения на карте
// Расстановка:
// "*" - свободная клетка
// "x" - занятая кораблем клетка
// "-" - клетка вблизи корабля
// После выстрела
// "0" - пустая клетка после выстрела
// "+" - занятая кораблем клетка после попадания
