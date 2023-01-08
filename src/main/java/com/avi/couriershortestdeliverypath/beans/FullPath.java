package com.avi.couriershortestdeliverypath.beans;

import java.util.LinkedList;

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

        // create a new FP
        FullPath fullPath1 = new FullPath(1);
        fullPath1.setFp((LinkedList<Path>) fullPath.getFp().clone());

        return fullPath1;
    }

    @Override
    public String toString() {
        return "FullPath{" +
                "fp=" + fp +
                ", fpSize=" + fpSize +
                '}';
    }
}
