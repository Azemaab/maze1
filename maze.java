import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the width of the maze:");
        int width = scanner.nextInt();
        System.out.println("Enter the height of the maze:");
        int height = scanner.nextInt();

        MazeGenerator generator = new MazeGenerator(width, height);