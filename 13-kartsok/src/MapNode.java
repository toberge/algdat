public class MapNode implements Comparable<MapNode> {
    final double latitude;
    final double longtitude;
    private final double latitudeInRadians; // breddegrad
    private final double longtitudeInRadians; // lengdegrad
    private final double cosineOfLatitude; // optimalization
    static final int INFINITY = Integer.MAX_VALUE / 3;

    MapNode parent = null;
    boolean found = false;
    int distance = INFINITY;
    int estimate = 0;

    MapEdge firstEdge;

    public MapNode(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.latitudeInRadians = Math.toRadians(latitude);
        this.longtitudeInRadians = Math.toRadians(longtitude);
        this.cosineOfLatitude = Math.cos(this.latitudeInRadians);
    }

    public int estimateDistanceTo(MapNode to) {
        double sineOfLatitude = Math.sin((this.latitudeInRadians - to.latitudeInRadians) / 2.0);
        double sineOfLongtitude = Math.sin((this.longtitudeInRadians - to.longtitudeInRadians) / 2.0);
        // 2r --> in cs 2r / (max speed limit * 3600 secs per hour * 100 cs per s), where r is radius of Earth
        return (int) (35285538.46153846153846153846 *
                // sin^2 --> sin(stuff)*sin(stuff) 4 fastness
                Math.asin(Math.sqrt(sineOfLatitude*sineOfLatitude + this.cosineOfLatitude*to.cosineOfLatitude*sineOfLongtitude*sineOfLongtitude))
        );
    }

    /**
     * ToDo check if this gives correct priority
     * I think it does, but who knows ¯\_(ツ)_/¯
     * @param o
     * @return
     */
    @Override
    public int compareTo(MapNode o) {
        return Integer.compare(this.distance + this.estimate, o.distance + o.estimate);
    }
}
