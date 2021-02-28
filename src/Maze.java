public class Maze {
    private MazeObject[][] map;
    private final int width;
    private final int height;
    private Point currentPosition;
    private Point end;
    private boolean isCompleted;

    public Maze(char[][] map) throws Exception {
        if (map.length == 0 || map[0].length == 0) {
            throw new Exception("Map can not be empty");
        }

        int a = map[0].length;
        for (char[] chars : map) {
            if (chars.length != a) {
                throw new Exception("Map must not be multi-stage");
            }
        }

        width = map.length;
        height = map[0].length;
        initMap(map);
    }

    private void initMap(char[][] map) throws Exception {
        this.map = new MazeObject[map.length][map[0].length];
        int starts = 0;
        int ends = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                switch (map[i][j]) {
                    case '#':
                        this.map[i][j] = MazeObject.Wall;
                        break;
                    case '.':
                        this.map[i][j] = MazeObject.Empty;
                        break;
                    case 's':
                        this.map[i][j] = MazeObject.Start;
                        currentPosition = new Point(i, j);
                        starts++;
                        break;
                    case 'f':
                        this.map[i][j] = MazeObject.End;
                        end = new Point(i, j);
                        ends++;
                        break;
                    case '*':
                        this.map[i][j] = MazeObject.Way;
                        break;
                    default:
                        throw new Exception("Found a unexpected symbol");
                }
            }
        }

        if (starts > 1) {
            throw new Exception("Start count must be 1");
        }

        if (ends > 1) {
            throw  new Exception("End count must be 1");
        }
    }

    public char[][] complete() throws Exception {
        findWay();
        return convertMapToCharArray(map);
    }

    private void findWay() throws Exception {
        if (isCompleted) return;

        checkMove(Direction.Up);
        if (!isCompleted) checkMove(Direction.Down);
        if (!isCompleted) checkMove(Direction.Left);
        if (!isCompleted) checkMove(Direction.Right);

        return;
    }

    private void checkMove(Direction direction) throws Exception {
        MazeObject[][] mapCopy = copyMap();
        Point positionCopy = new Point(currentPosition.x, currentPosition.y);

        if (canMove(direction)) {
            move(direction);
        } else {
            return;
        }
        if (isCompleted) return;
        findWay();
        if (isCompleted) return;

        currentPosition = positionCopy;
        map = mapCopy;
    }

    private MazeObject[][] copyMap() {
        MazeObject[][] output = new MazeObject[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                output[i][j] = map[i][j];
            }
        }

        return output;
    }

    private char[][] convertMapToCharArray(MazeObject[][] map) throws Exception {
        char[][] output = new char[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                switch (map[i][j]) {
                    case Wall:
                        output[i][j] = '#';
                        break;
                    case Empty:
                        output[i][j] = '.';
                        break;
                    case Start:
                        output[i][j] = 's';
                        break;
                    case End:
                        output[i][j] = 'f';
                        break;
                    case Way:
                        output[i][j] = '*';
                        break;
                    default:
                        throw new Exception("Found a unexpected direction");
                }
            }
        }

        return output;
    }

    private void move(Direction direction) throws Exception {
        currentPosition = getPosAfterMove(direction);

        if (!currentPosition.equals(end)) {
            map[currentPosition.x][currentPosition.y] = MazeObject.Way;
        }
        else {
            isCompleted = true;
        }
    }

    private boolean canMove(Direction direction) throws Exception {
        Point posAfterMove = getPosAfterMove(direction);

        if (0 <= posAfterMove.x && posAfterMove.x < width
                && 0 <= posAfterMove.y && posAfterMove.y < height) {
            MazeObject stayOn = map[posAfterMove.x][posAfterMove.y];
            return stayOn == MazeObject.Empty || stayOn == MazeObject.End;
        }
        return false;
    }

    private Point getPosAfterMove(Direction direction) throws Exception {
        Point posAfterMove = new Point(currentPosition.x, currentPosition.y);

        switch (direction) {
            case Up:
                posAfterMove.y++;
                break;
            case Down:
                posAfterMove.y--;
                break;
            case Left:
                posAfterMove.x--;
                break;
            case Right:
                posAfterMove.x++;
                break;
            default:
                throw new Exception("Found a unexpected direction");
        }

        return posAfterMove;
    }
}