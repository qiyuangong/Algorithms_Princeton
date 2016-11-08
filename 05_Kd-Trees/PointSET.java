import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;
    public PointSET() {
        // construct an empty set of points
        points = new SET<Point2D>();
    }
    public boolean isEmpty() {
        // is the set empty?
        return points.isEmpty();
    }
    public int size() {
        // number of points in the set
        return points.size();
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (null == p)
            throw new java.lang.NullPointerException();
        if (!points.contains(p))
            points.add(p);
    }
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        if (null == p)
            throw new java.lang.NullPointerException();
        return points.contains(p);
    }
    public void draw() {
        // draw all points to standard draw 
        for (Point2D point : points)
            point.draw();
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        // all neighboring boards
        // all neighboring boards
        if (null == rect)
            throw new java.lang.NullPointerException();
        ArrayList<Point2D> it = new ArrayList<Point2D>();
        for (Point2D point : points)
            if (rect.contains(point))
                it.add(point);
        return it;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (null == p)
            throw new java.lang.NullPointerException();
        if (isEmpty())
            return null;
        double minDistance = 1000000;
        double curr = 0;
        Point2D res = points.min();
        for (Point2D point : points) {
            curr = p.distanceSquaredTo(point);
            if (curr < minDistance) {
                minDistance = curr;
                res = point;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        // unit testing of the methods (optional) 
    }
}