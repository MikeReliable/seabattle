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

import java.util.*;

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
        List<Cell> cells = new ArrayList<>();
        int fieldSize = 10;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Cell cell = new Cell();
                cell.setCoordinateY(i);
                cell.setCoordinateX(j + 1);
                cell.setStatus("*");
                cell.setPlayer(player);
                cells.add(cell);
            }
        }
        player.setCellList(cells);
        playerRepo.save(player);
        return player;
    }

    public String putPlayerShip(Long gameId, Long playerId, int coordinateX, int coordinateY, boolean horizontal) {
        if (gameRepo.findByGameId(gameId) != null) {
            if (!gameRepo.findByGameId(gameId).isInactive()) {
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
                    if (1 <= coordinateX && coordinateX <= 10 && 1 <= coordinateY && coordinateY <= 10) {
                        if (actualPlayer.getShipSet().size() < 10) {
                            Ship ship = new Ship();
                            ship.setAlive(true);
                            ship.setLength(length);
                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY) != null) {
                                Cell cell;
                                Set<Cell> shipSides = new HashSet<>();
                                Set<Cell> shipDecks = new HashSet<>();
                                if (horizontal) {
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + length - 1, coordinateY) != null) {
                                        boolean setShip = true;
                                        for (int i = 0; i < length; i++) {
                                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY) != null) {
                                                cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i, coordinateY);
                                                if (findShipsIntersections(cell, shipDecks)) {
                                                    setShip = false;
                                                    break;
                                                }
                                            }
                                            if (i == 0) {
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY + 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY + 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY - 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i - 1, coordinateY - 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
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
                                            if (i == length - 1) {
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY + 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY + 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY - 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + i + 1, coordinateY - 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (setShip) {
                                            ship.setPlayer(actualPlayer);
                                            shipRepo.save(ship);
                                            for (Cell shipSide : shipDecks) {
                                                shipSide.setShip(ship);
                                            }
                                            playerRepo.save(actualPlayer);
                                            return "Корабль успешно установлен";
                                        } else {
                                            return "Корабли не могуг соприкасаться";
                                        }
                                    } else {
                                        return "Корабль не может выходить за пределы поля 10х10";
                                    }
                                } else {
                                    if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + length - 1) != null) {
                                        boolean setShip = true;
                                        for (int i = 0; i < length; i++) {
                                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i) != null) {
                                                cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i);
                                                if (findShipsIntersections(cell, shipDecks)) {
                                                    setShip = false;
                                                    break;
                                                }
                                            }
                                            if (i == 0) {
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i - 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i - 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY + i - 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY + i - 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY + i - 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY + i - 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY + i) != null) {
                                                cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY + i);
                                                if (findShipsContacts(cell, shipSides)) {
                                                    setShip = false;
                                                    break;
                                                }
                                            }
                                            if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY + i) != null) {
                                                cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY + i);
                                                if (findShipsContacts(cell, shipSides)) {
                                                    setShip = false;
                                                    break;
                                                }
                                            }
                                            if (i == length - 1) {
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i + 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX, coordinateY + i + 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY + i + 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX + 1, coordinateY + i + 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY + i + 1) != null) {
                                                    cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayer, coordinateX - 1, coordinateY + i + 1);
                                                    if (findShipsContacts(cell, shipSides)) {
                                                        setShip = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (setShip) {
                                            ship.setPlayer(actualPlayer);
                                            shipRepo.save(ship);
                                            for (Cell shipSide : shipDecks) {
                                                shipSide.setShip(ship);
                                            }
                                            playerRepo.save(actualPlayer);
                                            return "Корабль успешно установлен";
                                        } else {
                                            return "Корабли не могуг соприкасаться";
                                        }
                                    } else {
                                        return "Корабль не может выходить за пределы поля 10х10";
                                    }
                                }
                            }
                        }
                        return "Все корабли игрока расставлены";
                    }
                    return "Заданная координата выходит за пределы поля 10х10";
                }
                return "Игрок не найден";
            }
            return "Игра окончена";
        }
        return "Игра не найдена";
    }

    public Player findPlayerByPlayerIdAndGameGameId(Long playerId, Long gameId) {
        return playerRepo.findPlayerByPlayerIdAndGameGameId(playerId, gameId);
    }

    private boolean findShipsIntersections(Cell cell, Set<Cell> shipDecks) {
        boolean reaction = false;
        if (cell.getStatus().equals("*")) {
            cell.setStatus("x");
            shipDecks.add(cell);
        } else {
            reaction = true;
        }
        return reaction;
    }

    private boolean findShipsContacts(Cell cell, Set<Cell> shipSides) {
        boolean reaction = false;
        if (cell.getStatus().equals("*") || cell.getStatus().equals("-")) {
            cell.setStatus("-");
            shipSides.add(cell);
        } else {
            reaction = true;
        }
        return reaction;
    }

    public String makeShoot(Long gameId, Long playerId, Long enemyId, int coordinateX, int coordinateY) {
        if (gameRepo.findById(gameId).isPresent()) {
            Game game = gameRepo.findByGameId(gameId);
            if (findPlayerByPlayerIdAndGameGameId(playerId, gameId) != null && findPlayerByPlayerIdAndGameGameId(enemyId, gameId) != null
                    && !findPlayerByPlayerIdAndGameGameId(playerId, gameId).equals(findPlayerByPlayerIdAndGameGameId(enemyId, gameId))) {
                Player actualPlayer = findPlayerByPlayerIdAndGameGameId(playerId, gameId);
                Player actualPlayerEnemy = findPlayerByPlayerIdAndGameGameId(enemyId, gameId);
                if (cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayerEnemy, coordinateX, coordinateY) != null) {
                    Cell cell = cellRepo.findCellByPlayerAndCoordinateXAndCoordinateY(actualPlayerEnemy, coordinateX, coordinateY);
                    if (!cell.getStatus().equals("x")) {
                        cell.setStatus("0");
                        cellRepo.save(cell);
                        game.setWhoseTurn(actualPlayerEnemy.getPlayerId());
                        gameRepo.save(game);
                        return "Мимо!";
                    } else {
                        Ship ship = shipRepo.findShipByCellListContainsAndPlayer(cell, actualPlayerEnemy);
                        byte length = (byte) (ship.getLength() - 1);
                        ship.setLength(length);
                        if (length == 0) {
                            ship.setAlive(false);
                        }
                        cell.setStatus("+");
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
                return "Заданная координата выходит за пределы поля 10х10";
            }
            return "Игрок не найден";
        }
        return "Игра не найдена";
    }


    public String getPlayerField(Long gameId, Long playerId) {
        if (gameRepo.findById(gameId).isPresent()) {
            if (findPlayerByPlayerIdAndGameGameId(playerId, gameId) != null) {
                List<Cell> cellList = findPlayerByPlayerIdAndGameGameId(playerId, gameId).getCellList();
                cellList.sort(Comparator.comparing(Cell::getCellId));
                int count = 0;
                System.out.println();
                for (Cell cell : cellList) {
                    if (count < 9) {
                        System.out.print(cell.getStatus());
                        count++;
                    } else {
                        System.out.println(cell.getStatus());
                        count = 0;
                    }
                }
                return "ok";
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
