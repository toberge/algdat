import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * Brainstorming:
 *
 * Land, Water, Clouds
 * Can handle clouded nodes like land since we're after *minimal* number of islands!
 * ...or not.
 * If clouds connect land, count them in. If clouds cover isolated areas, count them as water.
 * This means we have two 'states' of discovered islands: "clouded only" and "with guaranteed land"
 * Program flow:
 *      Inspect node.
 *      Merge with other isle(s),
 *      land overrides cloud state.
 *      Afterwards, count all 'land' islands & print.
 * No, wait a minute:
 *      Run DFS/BFS from all unexamined nodes.
 *      If we hit or start on land, it is an island
 *      and will increment the count when the DFS is complete.
 *
 * ...spent < 1.5 hours on this, went from BFS to DFS after the former did not work out that well.
 */
public class Islands3 {
    private final int rows; // no given limit
    private final int columns; // up to 50

    private final Tile[][] tiles;

    // an overcomplicating enum.
    private enum TileType {
        LAND, CLOUDED, WATER
    }

    // I don't really need a Tile class, either
    private class Tile {
        final TileType type;
        final int row;
        final int column;
        boolean found = false;

        public Tile(char token, int row, int column) {
            switch (token) {
                case 'L':
                    type = TileType.LAND;
                    break;
                case 'C':
                    type = TileType.CLOUDED;
                    break;
                case 'W':
                    type = TileType.WATER;
                    break;
                default:
                    throw new IllegalArgumentException("INVALID TILE CHARACTER!");
            }
            this.row = row;
            this.column = column;
        }
    }

    public Islands3(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        columns = Integer.parseInt(st.nextToken());

        tiles = new Tile[rows][columns];

        for (int r = 0; r < rows; r++) {
//            st = new StringTokenizer(br.readLine());
            char[] chars = br.readLine().toCharArray();
            for (int c = 0; c < columns; c++) {
//                System.out.println(c + ", " + r);
//                tiles[r][c] = new Tile(st.nextToken(), r, c);
                tiles[r][c] = new Tile(chars[c], r, c);
            }
        }
    }

    // HERE IS THE ACTUAL CODE NOW...

    private void markIsland(Tile tile) {
        tile.found = true;

        // NOT EVEN NEEDED GAAAAH...
        // we do not VISIT nodes that are WATER
        // cuz that SOMEHOW SCREWED IT ALL UP
        // ...and I don't see WHY.
        // if (tile.type == TileType.WATER) return;

        // an abundance of duplicated code

        // running DFS on any neighbouring node that is
        if (tile.row > 0) { // there's one above
            Tile neighbour = tiles[tile.row - 1][tile.column];
            if (!neighbour.found && (neighbour.type == TileType.LAND || neighbour.type == TileType.CLOUDED)) markIsland(neighbour);
        }
        if (tile.row < rows - 1) { // if there's one below
            Tile neighbour = tiles[tile.row + 1][tile.column];
            if (!neighbour.found && (neighbour.type == TileType.LAND || neighbour.type == TileType.CLOUDED)) markIsland(neighbour);
        }
        if (tile.column > 0) { // if there's one to the left
            Tile neighbour = tiles[tile.row][tile.column - 1];
            if (!neighbour.found && (neighbour.type == TileType.LAND || neighbour.type == TileType.CLOUDED)) markIsland(neighbour);
        }
        if (tile.column < columns - 1) { // if there's one to the right
            Tile neighbour = tiles[tile.row][tile.column + 1];
            if (!neighbour.found && (neighbour.type == TileType.LAND || neighbour.type == TileType.CLOUDED)) markIsland(neighbour);
        }
    }

    public int countIslands() {
        int islandCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // ye olde BFS
                // if (!tiles[r][c].found && isPartOfIsland(r, c)) islandCount++;

                // DFS that only triggers at newfound land nodes
                if (!tiles[r][c].found && tiles[r][c].type == TileType.LAND) {
                    // then visits all connected NON-WATER nodes!
                    markIsland(tiles[r][c]);
                    islandCount++;
                }
            }
        }

        return islandCount;
    }

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Islands3 inspector = new Islands3(br);
            System.out.println(inspector.countIslands());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*=========================================================*/
    /* CAUTION: HERE IS A BFS IMPLEMENTATION THAT DID NOT WORK */
    /*=========================================================*/

    private boolean inspectAndAdd(ArrayDeque<Tile> queue, int r, int c) {
        Tile neighbour = tiles[r][c];
        if (!neighbour.found) {
            // mark as found
            neighbour.found = true;
            // if also not water, add to queue
            if (!(neighbour.type == TileType.WATER))
                queue.add(neighbour);
        }
        // only setting foundLand to *true*
        if (neighbour.found && neighbour.type == TileType.LAND) System.out.println("IT HAPPENED");
        return neighbour.type == TileType.LAND;
    }

    private boolean isPartOfIsland(int r, int c) {
        tiles[r][c].found = true;
        if (tiles[r][c].type == TileType.WATER) return false; // DO NOT PROPAGATE WATERYNESS
        boolean foundLand = tiles[r][c].type == TileType.LAND;
        ArrayDeque<Tile> queue = new ArrayDeque<>();
        queue.add(tiles[r][c]);

        while (!queue.isEmpty()) {
            Tile tile = queue.poll();

            // go through neighbours
            if (tile.row > 0) { // if there's one above
                if (inspectAndAdd(queue, tile.row - 1, tile.column)) foundLand = true;
            }
            if (tile.row < rows - 1) { // if there's one below
                if (inspectAndAdd(queue, tile.row + 1, tile.column)) foundLand = true;
            }
            if (tile.column > 0) { // if there's one to the left
                if (inspectAndAdd(queue, tile.row, tile.column - 1)) foundLand = true;
            }
            if (tile.column < columns - 1) { // if there's one to the right
                if (inspectAndAdd(queue, tile.row, tile.column + 1)) foundLand = true;
            }
        }

        return foundLand;
    }
}
