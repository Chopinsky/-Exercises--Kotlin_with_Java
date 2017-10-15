package com.company;

import javafx.util.Pair;
import jdk.nashorn.internal.ir.IfNode;
import org.jetbrains.annotations.NotNull;

import java.util.*;

class Building implements Comparable<Building> {
    private Integer startLoc;
    private Integer endLoc;
    private Integer height;

    Building(Integer StartLoc, Integer EndLoc, Integer Height) {
        super();
        this.startLoc = StartLoc >= 0 ? StartLoc : 0;
        this.endLoc = EndLoc >= StartLoc ? EndLoc : StartLoc;
        this.height = Height >= 0 ? Height : 0;
    }

    Integer getStartLoc() {
        return startLoc;
    }

    Integer getEndLoc() {
        return endLoc;
    }

    Integer getHeight() {
        return height;
    }

    public int compareTo(@NotNull Building anotherBuilding) {
        return this.height.compareTo(anotherBuilding.height);
    }
}

class SkyLine {
    private Integer viewSize;
    private LinkedList<Building> buildings;

    SkyLine(Integer ViewSize, LinkedList<Building> Buildings) {
        this.viewSize = ViewSize >0 ? ViewSize : 1;
        this.buildings = new LinkedList<>(Buildings);
    }

    private int findProperLoc(int loc, int startLimit, int endLimit) {
        if (loc < startLimit)
            return startLimit;
        else if (loc >= endLimit)
            return endLimit - 1;

        return loc;
    }

    void draw() {
        int[] skyLine = new int[viewSize];
        int start, end;
        Collections.sort(buildings);

        Iterator<Building> it = buildings.descendingIterator();
        while (it.hasNext()) {
            Building b = it.next();
            start = findProperLoc(b.getStartLoc(), 0, viewSize);
            end = findProperLoc(b.getEndLoc(), start, viewSize);

            for (int loc = b.getStartLoc(); loc <= b.getEndLoc(); loc++) {
                if (skyLine[loc] == 0 && b.getHeight() > 0) skyLine[loc] = b.getHeight();
            }
        }

        for (int height: skyLine) {
            System.out.print(height+ " ");
        }
    }
}
