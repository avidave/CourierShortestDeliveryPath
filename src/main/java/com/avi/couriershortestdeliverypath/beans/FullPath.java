package com.avi.couriershortestdeliverypath.beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FullPath {
    private LinkedList<Path> fp;
    private int fpSize;

    public FullPath(int fpSize) {
        this.fpSize = fpSize;
        this.fp = new LinkedList<>();
    }

    public LinkedList<Path> getFp() {
        return fp;
    }

    public void setFp(LinkedList<Path> fp) {
        this.fp = fp;
    }

    public boolean isPathInFullPath(Path path) {
        if (this.fp.contains(path))
            return true;

        return false;
    }

    public boolean isFullPathComplete() {
        if (this.fp.isEmpty()) return false;

        if (this.fp.getLast().hasEndpoint() && this.fp.size() == this.fpSize)
            return true;

        return false;
    }

    public boolean isLastPathToLocation(Location location) {
        if (fp.isEmpty() || this.fp.getLast().getToLocation().equals(location))
            return true;

        return false;
    }

    public boolean addToFullPath(Path path) {
        if (this.isLastPathToLocation(path.getFromLocation())) {
            this.fp.add(path);
            return true;
        }

        return false;
    }

    public FullPath cloneFullPath(FullPath fullPath) {

        this.setFp((LinkedList<Path>) fullPath.getFp().clone());

        return this;
    }

    public List<Path> getBranchPathsToFullPath () {

        List<Path> branchPaths = this.getFp().getLast().getToLocation().getBranchPaths();

        List<Path> finalPaths = new ArrayList<Path>();

        for (Path branchPath : branchPaths) {

            Location toLocation = branchPath.getToLocation();
            boolean candidatePath = false;
            for (Path fpPath : this.getFp()) {

                if (fpPath.isLocationPresent(toLocation)) {

                    candidatePath = false;
                    break;
                }

                candidatePath = true;
            }

            if (candidatePath) {

                finalPaths.add(branchPath);
            }
        }

        return finalPaths;
    }

    public double getFullPathDistance() {

        double distance = 0;

        for (Path path : this.getFp()) {

            distance += path.getDistance();
        }

        return distance;
    }

    @Override
    public String toString() {
        return "FullPath{" +
                "fp=" + fp +
                ", fpSize=" + fpSize +
                '}';
    }
}
