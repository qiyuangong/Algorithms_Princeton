import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int level;

        public Node() {
            p = null;
            rect = new RectHV(0, 0, 1.0, 1.0);
            lb = null;
            rt = null;
            level = 0;
        }
        public Node(RectHV r, int currLevel) {
            this.rect = r;
            this.level = currLevel;
        }
        private Node splitNode(Point2D point, int currLevel) {
            p = point;
            if (level % 2 == 0) {
                lb = new Node(new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax()), currLevel + 1);
                rt = new Node(new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax()), currLevel + 1);
            }
            else {
                lb = new Node(new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y()), currLevel + 1);
                rt = new Node(new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax()), currLevel + 1);
            }
            return this;
        }
        public void draw() {
            if (p == null)
                return;
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.BLACK);
            // draw point
            p.draw();
            // draw line
            StdDraw.setPenRadius();
            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
            }
            lb.draw();
            rt.draw();
        }
    }

    public KdTree() {
        root = new Node();
        size = 0;
    }
    public boolean isEmpty() {
        // is the set empty?
        return (size == 0);
    }
    public int size() {
        // number of points in the set
        return size;
    }
    public boolean contains(Point2D point) {
        // does the set contain point p?
        if (null == point)
            throw new java.lang.NullPointerException();
        if (contains(root, point, 0) != null)
            return true;
        return false;
    }
    private Node contains(Node x, Point2D point, int level) {
        // does the set contain point p?
        if (null == x || x.p == null)
            return null;
        double cmp = 0;
        if (point.equals(x.p))
            return x;
        if (level % 2 == 0) 
            cmp = point.x() - x.p.x();
        else
            cmp = point.y() - x.p.y();
        if (cmp < 0)
            return contains(x.lb, point, level + 1);
        else
            return contains(x.rt, point, level + 1);
    }
    public void insert(Point2D point) {
        // add the point to the set (if it is not already in the set)
        if (null == point)
            throw new java.lang.NullPointerException();
        if (!contains(point)) {
            insert(root, point, 0);
            size++;
        }
    }
    private Node insert(Node x, Point2D point, int level) {
        if (x.p == null)
            return x.splitNode(point, level);
        double cmp = 0;
        if (level % 2 == 0) 
            cmp = point.x() - x.p.x();
        else
            cmp = point.y() - x.p.y();
        if (cmp < 0)
            x.lb = insert(x.lb, point, level + 1);
        else
            x.rt = insert(x.rt, point, level + 1);
        return x;
    }
    public void draw() {
        // draw all points to standard draw
        root.draw();
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        // all neighboring boards
        // all neighboring boards
        if (null == rect)
            throw new java.lang.NullPointerException();
        ArrayList<Point2D> it = new ArrayList<Point2D>();
        range(root, rect, it);
        return it;
    }
    private void range(Node x, RectHV rect, ArrayList<Point2D> it) {
        if (x == null || x.p == null)
            return;
        if (!x.rect.intersects(rect)) 
            return;
        if (rect.contains(x.p))
            it.add(x.p);
        range(x.lb, rect, it);
        range(x.rt, rect, it);
    }
    public Point2D nearest(Point2D point) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (isEmpty())
            return null;
        Point2D currRes = root.p;
        return nearest(root, point, currRes);
    }
    private Point2D nearest(Node x, Point2D point, Point2D currRes) {
        if (x == null || x.p == null)
            return currRes;
        if (point.distanceSquaredTo(currRes) < x.rect.distanceSquaredTo(currRes))
            return currRes;
        if (x.p.distanceSquaredTo(point) < currRes.distanceSquaredTo(point))
            currRes = x.p;
        double cmp = 0;
        if (x.level % 2 == 0) 
            cmp = point.x() - x.p.x();
        else
            cmp = point.y() - x.p.y();
        if (cmp < 0) {
            currRes = nearest(x.lb, point, currRes);
            currRes = nearest(x.rt, point, currRes);
        }
        else {
            currRes = nearest(x.rt, point, currRes);   
            currRes = nearest(x.lb, point, currRes);
        }
        return currRes;
    }
    public static void main(String[] args) {
        // unit testing of the methods (optional) 
    }
}