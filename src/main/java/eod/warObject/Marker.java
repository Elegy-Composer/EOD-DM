package eod.warObject;

import eod.GameObject;

import java.awt.*;
import java.util.ArrayList;

public interface Marker {
    void mark(Point point);
    void unmark(Point point);
    void clearMark();
    ArrayList<Point> getMarks();
}
