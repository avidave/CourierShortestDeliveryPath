package com.avi.couriershortestdeliverypath;

import com.avi.couriershortestdeliverypath.beans.FullPath;
import com.avi.couriershortestdeliverypath.beans.Location;
import com.avi.couriershortestdeliverypath.beans.Path;
import com.avi.couriershortestdeliverypath.beans.Address;
import com.avi.couriershortestdeliverypath.util.Utility;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.*;

public class CourierShortestDeliveryPath {

    private static Stack<Path> pathStack = new Stack<Path>();
    private static List<FullPath> fullPaths = new LinkedList<>();
    private static List<Location> locations = new ArrayList<>();

    private static List<Path> paths = new ArrayList<>();
    private static Location endPointLocation;
    private static Location startPointLocation;

    private static Scanner addressScanner = new Scanner(System.in);
    private String APIKEY;

    private static int nLocations;
    //private static Logger logger = LoggerFactory.getLogger(CourierShortestDeliveryPath.class);


    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String APIKEY) {
        this.APIKEY = APIKEY;
    }

    public static void main(String[] args) throws Exception {

        // get input (Scanner)

        // find location which is a start point, call findPath(location)

        Location loc;

        System.out.println("Please enter you API Key here: ");
        CourierShortestDeliveryPath courierShortestDeliveryPath = new CourierShortestDeliveryPath();

        courierShortestDeliveryPath.setAPIKEY(addressScanner.nextLine());

        while (true) {

            System.out.println("Please enter an address below (Country_City_Address): \nOnce all addresses have been entered enter \"-\" to finish.");

            String in = addressScanner.nextLine();

            if (in.equals("-")) {
                break;
            }

            String[] lines = in.split("_");

            Address ad = new Address(lines[0], lines[1], lines[2]);

            if (Utility.isValidAddress(ad, courierShortestDeliveryPath.getAPIKEY())) {
                loc = new Location(ad);
            } else {
                System.out.println("The address entered is invalid. Please retry.");
                continue;
            }

            locations.add(loc);

            System.out.println("Enter if it is a starting point (S), ending point (E), or neither (Any): ");

            String input = addressScanner.nextLine();

            if (input.equals("S")) {
                loc.setStartPoint(true);
                startPointLocation = loc;
            } else if (input.equals("E")){
                loc.setEndPoint(true);
                endPointLocation = loc;
            }
        }

        addressScanner.close();

        Utility.setDistances(locations, courierShortestDeliveryPath.getAPIKEY());

        nLocations = locations.size();

        findPath(startPointLocation);
    }


    public static void findPath (Location l) {

        // add all paths without endpoint to stack

        // pop first path

        // check whether to add to fullpath (method), returns boolean
        // if true, find num of possibilities without end point for path, call addToFullPath(path, nPossibilities)

        // call recursively with path

        List<Path> pathsFromLocation = l.getBranchPaths();

        if (!pathsFromLocation.isEmpty()) {
            pathStack.addAll(pathsFromLocation);
        }

        Path p = pathStack.pop();
        if (checkAddToFullPath(p, l)) {
            addToFullPath(p, p.getToLocation().getBranchPaths().size());
        }
        findPath(p.getToLocation());


    }

    public static boolean checkAddToFullPath(Path path, Location l) {

        // reverse check
        if (path.getToLocation().equals(l))
            return false;

        // check if already in path
        if (pathStack.contains(path))
            return false;

        return true;
    }

    public static void addToFullPath(Path path, int nFullPaths) {

        boolean allCompleted = false;

        // if fullPaths is empty, add nFullPath path
        if (fullPaths.isEmpty()) {
            for (int i = 0; i <= nFullPaths; i++) {
                FullPath fullPath = new FullPath(nLocations - 1);
                fullPath.addToFullPath(path);
                fullPaths.add(fullPath);
            }
        } else {
            for (FullPath fp : fullPaths) {
                if (fp.isFullPathComplete()) {
                    allCompleted = true;
                    continue;
                } else {
                    allCompleted = false;

                    FullPath growPath = new FullPath(nLocations - 1);

                    if (fp.isPathInFullPath(path))
                        continue;

                    if (fp.isLastPathToLocation(path.getFromLocation())) {

                        growPath = fp;
                    }

                    growPath.addToFullPath(path);

                    for (int i = 0; i < nFullPaths - 1; i++) {

                        fullPaths.add(new FullPath(nLocations - 1));
                        fullPaths.get(i).cloneFullPath(growPath);
                    }

                }
            }
        }

    }
    public void checkPaths () {




    }
}
