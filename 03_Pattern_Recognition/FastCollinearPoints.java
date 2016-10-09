import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] collinear;
    private int n;


    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null)
            throw new java.lang.NullPointerException();
        // finds all line segments containing 4 points
        collinear = new LineSegment[points.length * points.length];
        Point[] temp = new Point[points.length];
        n = 0;
        Arrays.sort(points);
        for (int i = 0; i < points.length - 4; i++){
            Comparator<Point> comparator = points[i].slopeOrder();
            for (int j = i + 1; j < points.length; j++)
                temp[j] = points[j];
            Arrays.sort(temp, i + 1, points.length, comparator);
            int pos = i + 1;
            int last = i + 1;
            double currS = points[i].slopeTo(temp[last]);
            while (pos < points.length){
                double os = currS;
                last = pos++;
                while (pos < points.length && currS == os){
                    currS = points[i].slopeTo(temp[pos++]);
                }
                if (pos - last >= 3){
                    Arrays.sort(temp, last, pos);
                    collinear[n++] = new LineSegment(points[i], temp[pos - 1]);
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return n;
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] res = new LineSegment[n];
        for (int i = 0; i < n; i++)
            res[i] = collinear[i];
        return res;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}