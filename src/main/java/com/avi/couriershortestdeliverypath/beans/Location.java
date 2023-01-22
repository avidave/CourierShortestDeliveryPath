package com.avi.couriershortestdeliverypath.beans;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private Address ad;
    private boolean isStartPoint = false, isEndPoint = false;
    private List<Path> paths = new ArrayList<>();

    public Location(Address ad) {
        this.ad = ad;
    }

    public Address getAddress() {
        return ad;
    }

    public boolean isStartPoint() {
        return isStartPoint;
    }

    public boolean isEndPoint() {
        return isEndPoint;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setStartPoint(boolean startPoint) {
        isStartPoint = startPoint;
    }

    public void setEndPoint(boolean endPoint) {
        isEndPoint = endPoint;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public boolean hasPath(Path p) {
        return this.paths.contains(p);
    }

    public void addPath(Path p) {
        paths.add(p);
    }

    /**
     * If location is a starting location, return all locations except that ending with an end location
     * If location is not a starting location and ending location, return all locations except that ending with start or end location
     * If location is a ending location, return empty list
     * @return
     */
    public List<Path> getBranchPaths() {
        List<Path> pathList = new ArrayList<>();
        if (isStartPoint()) {
            paths.forEach(p -> {
                if (p.getToLocation().isEndPoint() == false)
                    pathList.add(p);
            });
        } else if (!isStartPoint() && !isEndPoint()) {
            paths.forEach(p -> {
                if (p.getToLocation().isEndPoint() == false && p.getToLocation().isStartPoint() == false)
                    pathList.add(p);
            });
        }

        return pathList;
    }

    @Override
    public String toString() {
        return "Location{" +
                "ad=" + ad +
                ", isStartPoint=" + isStartPoint +
                ", isEndPoint=" + isEndPoint +
                '}';
    }

    public Path getPathTo(Location l) {

        for (Path p : this.getPaths()) {

            if (p.getToLocation().equals(l)) {

                return p;
            }
        }

        return null;
    }
}
