package assignment3_dnd;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private static final int MAX_LEVELS = 4;
    private static final String LEVEL_FILE_PREFIX = "level";
    private static final String LEVEL_FILE_SUFFIX = ".txt";

    private GameBoard board;
    private Hero player;
    private List<Enemy> enemies;
    private final Scanner scanner;
    private final MessageCallback out;
    private int currentLevel;

    /** Uses System.out by default. */
    public GameManager() {
        this(System.out::println);
    }

    /** Inject a custom output sink (e.g. a GUI logger or test spy). */
    public GameManager(MessageCallback out) {
        this.out = out;
        this.scanner = new Scanner(System.in);
        this.enemies = new ArrayList<>();
        this.currentLevel = 1;
    }

    // -------------------------------------------------------------------------
    // Public entry point
    // -------------------------------------------------------------------------

    public void startGame() {
        out.send("Welcome to D&D!");
        selectPlayer();

        while (currentLevel <= MAX_LEVELS && isPlayerAlive()) {
            String levelFile = LEVEL_FILE_PREFIX + currentLevel + LEVEL_FILE_SUFFIX;
            if (!loadLevel(levelFile)) {
                break;
            }
            out.send("--- Loading Level " + currentLevel + " ---");
            playLevel();

            if (isPlayerAlive()) {
                currentLevel++;
            }
        }

        out.send(isPlayerAlive() ? "You won!" : "Game Over.");
    }

    // -------------------------------------------------------------------------
    // Player selection
    // -------------------------------------------------------------------------

    private void selectPlayer() {
        out.send("Select player:");
        out.send("1. Jon Snow     (Warrior)");
        out.send("2. The Hound    (Warrior)");
        out.send("3. Melisandre   (Mage)");
        out.send("4. Thoros of Myr (Mage)");
        out.send("5. Arya Stark   (Rogue)");
        out.send("6. Bronn        (Rogue)");
        out.send("7. Ygritte      (Hunter)");

        int choice = scanner.nextInt();
        scanner.nextLine();

        player = createPlayer(choice);
        player.setMessageCallback(out::send);
    }

    private Hero createPlayer(int choice) {
        return switch (choice) {
            case 1 -> new Warrior("Jon Snow", 300, 30, 4, 3);
            case 2 -> new Warrior("The Hound", 400, 20, 6, 5);
            case 3 -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
            case 4 -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
            case 5 -> new Rogue("Arya Stark", 150, 40, 2, 20);
            case 6 -> new Rogue("Bronn", 250, 35, 3, 50);
            case 7 -> new Hunter("Ygritte", 220, 30, 2, 6);
            default -> new Warrior("Jon Snow", 300, 30, 4, 3);
        };
    }

    // -------------------------------------------------------------------------
    // Game loop
    // -------------------------------------------------------------------------

    private void playLevel() {
        while (isPlayerAlive() && !enemies.isEmpty()) {
            out.send(board.toString());
            char input = readInput();
            processPlayerInput(input);
            player.Tick();
            tickEnemies();
            enemies.removeIf(e -> e.hp_current <= 0);
        }
    }

    /** Tick every living enemy: move monsters, trigger traps. */
    private void tickEnemies() {
        Position playerPos = board.getPositionOf(player);

        for (Enemy enemy : enemies) {
            if (enemy.hp_current <= 0) continue;

            if (enemy instanceof Monster monster) {
                Position enemyPos  = board.getPositionOf(monster);
                Position targetPos = monster.Move(enemyPos, playerPos);
                board.attemptMove(monster, targetPos);
            }

            if (enemy instanceof Trap trap) {
                Position trapPos = board.getPositionOf(trap);
                trap.tickAndTrigger(player, trapPos, playerPos);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Input handling
    // -------------------------------------------------------------------------

    private char readInput() {
        String line = scanner.nextLine();
        return line.isEmpty() ? ' ' : line.charAt(0);
    }

    private void processPlayerInput(char input) {
        switch (input) {
            case 'w' -> movePlayer(0, -1);
            case 's' -> movePlayer(0,  1);
            case 'a' -> movePlayer(-1, 0);
            case 'd' -> movePlayer(1,  0);
            case 'e' -> castAbility();
            case 'q' -> { /* pass / do nothing */ }
            default  -> { /* unrecognised input: ignore */ }
        }
    }

    private void movePlayer(int dx, int dy) {
        Position current = board.getPositionOf(player);
        Position target  = new Position(current.GetX() + dx, current.GetY() + dy);
        board.attemptMove(player, target);
    }

    private void castAbility() {
        Position playerPos = board.getPositionOf(player);

        List<Enemy> enemiesInRange = enemies.stream()
                .filter(e -> playerPos.Range(board.getPositionOf(e)) < player.ability_range)
                .sorted(Comparator.comparingDouble(e -> playerPos.Range(board.getPositionOf(e))))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        player.CastAbility(enemiesInRange, player);
    }

    // -------------------------------------------------------------------------
    // Level loading
    // -------------------------------------------------------------------------

    private boolean loadLevel(String filePath) {
        List<String> lines = readLevelFile(filePath);
        if (lines == null || lines.isEmpty()) return false;

        int rows = lines.size();
        int cols = lines.get(0).length();
        Cell[][] cells = new Cell[rows][cols];
        enemies.clear();

        for (int y = 0; y < rows; y++) {
            String line = lines.get(y);
            for (int x = 0; x < cols; x++) {
                cells[y][x] = parseCell(line.charAt(x), new Position(x, y));
            }
        }

        this.board = new GameBoard(cells);
        return true;
    }

    /** Returns the file's lines, or null on failure. */
    private List<String> readLevelFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        List<String> lines = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
        } catch (Exception e) {
            out.send("Error reading level file: " + e.getMessage());
            return null;
        }
        return lines;
    }

    /** Convert a single map character into the correct Cell. */
    private Cell parseCell(char c, Position pos) {
        if (c == '#') return new Wall(pos);

        Floor floor = new Floor(pos);

        if (c == '@') {
            placeUnit(floor, player);
        } else if (c != '.') {
            Enemy enemy = createEnemy(c);
            if (enemy != null) {
                enemy.setMessageCallback(out::send);
                if (enemy instanceof Boss boss) {
                    boss.setPlayer(player);
                }
                enemies.add(enemy);
                placeUnit(floor, enemy);
            }
        }
        return floor;
    }

    /** Helper: wrap a unit in an Occupant and set it on the floor tile. */
    private void placeUnit(Floor floor, Unit unit) {
        Occupant occ = new Occupant();
        occ.SetUnit(unit);
        floor.SetOccupant(occ);
    }

    // -------------------------------------------------------------------------
    // Enemy factory
    // -------------------------------------------------------------------------

    private Enemy createEnemy(char c) {
        return switch (c) {
            // Regular monsters
            case 's' -> new Monster('s', "Lannister Soldier", 80, 8, 3, 25, 3);
            case 'k' -> new Monster('k', "Lannister Knight", 200, 14, 8, 50, 4);
            case 'q' -> new Monster('q', "Queen's Guard", 400, 20, 15, 100, 5);
            case 'z' -> new Monster('z', "Wright", 600, 30, 15, 100, 3);
            case 'b' -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250, 4);
            case 'g' -> new Monster('g', "Giant-Wright", 1500, 100, 40, 500, 5);
            case 'w' -> new Monster('w', "White Walker", 2000, 150, 50, 1000, 6);
            // Bosses
            case 'M' -> new Boss('M', "The Mountain", 1000, 60, 25, 500, 6, 5);
            case 'C' -> new Boss('C', "Queen Cersei", 100, 10, 10, 1000, 1, 8);
            case 'K' -> new Boss('K', "Night's King", 5000, 300, 150, 5000, 8, 3);
            // Traps
            case 'B' -> new Trap('B', "Bonus Trap", 1, 1, 1, 250, 1, 5);
            case 'Q' -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 7);
            case 'D' -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10);
            default -> null;
        };
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    private boolean isPlayerAlive() {
        return player.hp_current > 0;
    }
}