public class MapEdge {
    final int driveTime; // i hundredels sekunder

    // not necessary
    final int length;
    final short speedLimit;

    final MapNode to;
    MapEdge nextEdge;

    public MapEdge(int driveTime, int length, short speedLimit, MapNode to) {
        this.driveTime = driveTime;
        this.length = length;
        this.speedLimit = speedLimit;
        this.to = to;
    }
}
