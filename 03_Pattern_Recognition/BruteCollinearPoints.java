import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] collinear;
    private int n;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        // finds all line segments containing 4 points
        collinear = new LineSegment[points.length * points.length];
        n = 0;
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++){
            for (int j = i + 1; j < points.length; j++){
                double s1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++){
                    double s2 = points[i].slopeTo(points[k]);
                    if (s1 != s2)
                        continue;
                    for (int l = k + 1; l < points.length; l++){
                        double s3 = points[i].slopeTo(points[l]);
                        if (s1 == s3)
                            collinear[n++] = new LineSegment(points[i], points[l]);
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}