import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the width of the maze:");
        int width = scanner.nextInt();
        System.out.println("Enter the height of the maze:");
        int height = scanner.nextInt();

        MazeGenerator generator = new MazeGenerator(width, height);
        int[][][] maze = generator.maze;
        boolean[][] path = Pathfinder.findPath(maze);
        MazePrinter.printMaze(maze, path);
        scanner.close();
    }
}

// MazeGenerator class
class MazeGenerator {
    private final int width, height;
    public final int[][][] maze;
    private final boolean[][] visited;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width][4];
        this.visited = new boolean[height][width];
        generateMaze(0, 0);
    }

    private void generateMaze(int x, int y) {
        visited[y][x] = true;
        Integer[] directions = {0, 1, 2, 3};
        Collections.shuffle(Arrays.asList(directions));

        for (int direction : directions) {
            int nx = x, ny = y;

            switch (direction) {
                case 0: nx = x - 1; break;
                case 1: ny = y - 1; break;
                case 2: nx = x + 1; break;
                case 3: ny = y + 1; break;
            }

            if (nx >= 0 && ny >= 0 && nx < width && ny < height && !visited[ny][nx]) {
                maze[y][x][direction] = 1;
                maze[ny][nx][(direction + 2) % 4] = 1;
                generateMaze(nx, ny);
            }
        }
    }
}

// Pathfinder class
class Pathfinder {
    public static boolean[][] findPath(int[][][] maze) {
        int height = maze.length;
        int width = maze[0].length;
        boolean[][] visited = new boolean[height][width];
        boolean[][] path = new boolean[height][width];

        dfs(maze, 0, 0, visited, path);
        return path;
    }

    private static boolean dfs(int[][][] maze, int x, int y, boolean[][] visited, boolean[][] path) {
        if (x == maze[0].length - 1 && y == maze.length - 1) {
            path[y][x] = true;
            return true;
        }

        visited[y][x] = true;
        int[][] directions = {{-1, 0, 0}, {0, -1, 1}, {1, 0, 2}, {0, 1, 3}};

        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1], wall = dir[2];

            if (nx >= 0 && ny >= 0 && nx < maze[0].length && ny < maze.length &&
                maze[y][x][wall] == 1 && !visited[ny][nx]) {

                if (dfs(maze, nx, ny, visited, path)) {
                    path[y][x] = true;
                    return true;
                }
            }
        }
        return false;
    }
}

// MazePrinter class
class MazePrinter {
    public static void printMaze(int[][][] maze, boolean[][] path) {
        int height = maze.length;
        int width = maze[0].length;

        for (int y = 0; y < height; y++) {
            // print top walls
            for (int x = 0; x < width; x++) {
                System.out.print("+");
                System.out.print(maze[y][x][1] == 1 ? "   " : "---");
            }
            System.out.println("+");

            // print side walls and path
            for (int x = 0; x < width; x++) {
                System.out.print(maze[y][x][0] == 1 ? " " : "|");
                System.out.print(path != null && path[y][x] ? " * " : "   ");
            }
            System.out.println("|");
        }

        // bottom wall
        for (int x = 0; x < width; x++) {
            System.out.print("+---");
        }
        System.out.println("+");
        
    }
}
        
