package com.avi.couriershortestdeliverypath.beans;

public class Path {

    private Location fromLocation;
    private Location toLocation;
    private Double distance;

    public Path(Location fromLocation, Location toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    @Override
    public String toString() {
        return "Path{" +
                "fromLocation=" + fromLocation +
                ", toLocation=" + toLocation +
                ", distance=" + distance +
                '}';
    }

    public boolean isLocationPresent(Location l) {

        if(this.fromLocation.equals(l) || this.toLocation.equals(l)) return true;

        return false;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public boolean hasEndpoint() {
        if (this.fromLocation.isEndPoint() || this.toLocation.isEndPoint()) return true;

        return false;
    }
}
