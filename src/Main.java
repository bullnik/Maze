import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        for (int n = 0; n < 100; n++) {
            System.out.print("Enter width of maze: ");
            Scanner in = new Scanner(System.in);
            int width = in.nextInt();

            System.out.print("Enter height of maze: ");
            int height = in.nextInt();

            char[][] arr = new char[height][width];
            for (int i = 0; i < height; i++) {
                System.out.print("Maze: ");
                String s = in.next();
                arr[i] = s.toCharArray();
            }

            try {
                Maze maze = new Maze(arr);
                char[][] way = maze.complete();

                for (char[] chArr : way) {
                    System.out.print("Completed maze: ");
                    for (char ch : chArr) {
                        System.out.print(ch);
                    }
                    System.out.println();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            System.out.println();
        }
    }
}
