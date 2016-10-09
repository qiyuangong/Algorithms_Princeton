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
        Point[] ptemp = points.clone();
        collinear = new LineSegment[ptemp.length * ptemp.length];
        n = 0;
        Arrays.sort(ptemp);
        for (int i = 0; i < ptemp.length - 1; i++) {
            if (ptemp[i] == null || ptemp[i].compareTo(ptemp[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < ptemp.length; i++) {
            for (int j = i + 1; j < ptemp.length; j++) {
                double s1 = ptemp[i].slopeTo(ptemp[j]);
                for (int k = j + 1; k < ptemp.length; k++) {
                    double s2 = ptemp[i].slopeTo(ptemp[k]);
                    if (s1 != s2)
                        continue;
                    for (int l = k + 1; l < ptemp.length; l++) {
                        double s3 = ptemp[i].slopeTo(ptemp[l]);
                        if (s1 == s3)
                            collinear[n++] = new LineSegment(ptemp[i], ptemp[l]);
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