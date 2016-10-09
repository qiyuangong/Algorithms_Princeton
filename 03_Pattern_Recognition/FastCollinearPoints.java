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
        Point[] ptemp = points.clone();
        collinear = new LineSegment[ptemp.length * ptemp.length];
        Point[] temp = new Point[ptemp.length];
        n = 0;
        Arrays.sort(ptemp);
        for (int i = 0; i < ptemp.length - 1; i++) {
            if (ptemp[i] == null || ptemp[i].compareTo(ptemp[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < ptemp.length - 3; i++) {
            Comparator<Point> comparator = ptemp[i].slopeOrder();
            for (int j = i + 1; j < ptemp.length; j++)
                temp[j] = ptemp[j];
            Arrays.sort(temp, i + 1, ptemp.length, comparator);
            // System.out.printf("i=%d\n", i);
            // for (int j = i + 1; j < ptemp.length; j++)
                // System.out.printf("%f,", ptemp[i].slopeTo(temp[j]));
            // System.out.println();
            int pos = i + 1;
            int last;
            double currS = ptemp[i].slopeTo(temp[pos]);
            while (pos < ptemp.length) {
                double os = currS;
                last = pos;
                while (pos < ptemp.length) {
                    if (pos != last) {
                        currS = ptemp[i].slopeTo(temp[pos]);
                        if (currS != os)
                            break;
                    }
                    pos++;
                }
                if (pos - last >= 3) {
                    // System.out.printf("last=%d,pos=%d\n", last, pos);
                    Arrays.sort(temp, last, pos);
                    collinear[n++] = new LineSegment(ptemp[i], temp[pos - 1]);
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