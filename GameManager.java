package assignment3_dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private GameBoard board;
    private Hero player;
    private List<Enemy> enemies;
    private Scanner scanner;
    private int currentLevel;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.enemies = new ArrayList<>();
        this.currentLevel = 1;
    }

    public void StartGame() {
        System.out.println("Welcome to D&D!");
        selectPlayer();
        while (currentLevel <= 4 && player.hp_current > 0) {
            boolean levelLoaded = loadLevel("level" + currentLevel + ".txt");
            if (!levelLoaded) {
                break;
            }

            System.out.println("--- Loading Level " + currentLevel + " ---");
            playLevel();

            if (player.hp_current > 0) {
                currentLevel++;
            }
        }

        if (player.hp_current <= 0) {
            System.out.println("Game Over.");
        } else {
            System.out.println("You won!");
        }
    }

    private void selectPlayer() {
        System.out.println("Select player:");
        System.out.println("1. Jon Snow (Warrior)");
        System.out.println("2. The Hound (Warrior)");
        System.out.println("3. Melisandre (Mage)");
        System.out.println("4. Thoros of Myr (Mage)");
        System.out.println("5. Arya Stark (Rogue)");
        System.out.println("6. Bronn (Rogue)");
        System.out.println("7. Ygritte (Hunter)");
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> player = new Warrior("Jon Snow", 300, 30, 4, 3);
            case 2 -> player = new Warrior("The Hound", 400, 20, 6, 5);
            case 3 -> player = new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
            case 4 -> player = new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
            case 5 -> player = new Rogue("Arya Stark", 150, 40, 2, 20);
            case 6 -> player = new Rogue("Bronn", 250, 35, 3, 50);
            case 7 -> player = new Hunter("Ygritte", 220, 30, 2, 6);
            default -> player = new Warrior("Jon Snow", 300, 30, 4, 3); 
        }
    }

    private void playLevel() {
        while (player.hp_current > 0 && !enemies.isEmpty()) {
     
            System.out.println(board.toString());
            char input = scanner.nextLine().charAt(0);
            processPlayerInput(input);
            player.Tick();
            for (Enemy enemy : enemies) {
                if (enemy.hp_current > 0) {
                    Position targetPos;
                    if (enemy instanceof Monster) {
                        Position enemyPos = board.getPositionOf(enemy);
                        Position playerPos = board.getPositionOf(player);
                        targetPos = ((Monster)enemy).Move(enemyPos, playerPos);
                        board.attemptMove(enemy, targetPos);
                    }
                }
            }
            enemies.removeIf(e -> e.hp_current <= 0);
        }
    }

    private void processPlayerInput(char input) {
        Position currentPos = board.getPositionOf(player);
        Position targetPos = currentPos;

        switch (input) {
            case 'w' -> targetPos = new Position(currentPos.GetX(), currentPos.GetY() - 1);
            case 's' -> targetPos = new Position(currentPos.GetX(), currentPos.GetY() + 1);
            case 'a' -> targetPos = new Position(currentPos.GetX() - 1, currentPos.GetY());
            case 'd' -> targetPos = new Position(currentPos.GetX() + 1, currentPos.GetY());
            case 'q' -> {
                return;
            }
            case 'e' -> {
                List<Enemy> enemiesInRange = new ArrayList<>();
                Position playerPos = board.getPositionOf(player);
                for (Enemy enemy : enemies) {
                    Position enemyPos = board.getPositionOf(enemy);
                    if (playerPos.Range(enemyPos) < player.ability_range) {
                        enemiesInRange.add(enemy);
                    }
                }
                enemiesInRange.sort((e1, e2) -> {
                    double d1 = currentPos.Range(board.getPositionOf(e1));
                    double d2 = currentPos.Range(board.getPositionOf(e2));
                    return Double.compare(d1, d2);
                });
                player.CastAbility(enemiesInRange, player);
                return;
            }
            default -> {
                return;
            }
        }

        board.attemptMove(player, targetPos);
    }


    private boolean loadLevel(String filePath) {
        return false; //temporary
    }
}