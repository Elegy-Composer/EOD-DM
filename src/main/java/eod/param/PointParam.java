package eod.param;

import eod.warObject.Status;

import java.util.ArrayList;

public class PointParam {
    public int range = 1;
    public boolean emptySpace = false;
    public ArrayList<Status> excludeStatus = new ArrayList<>();
}
