package com.avi.couriershortestdeliverypath;

import com.avi.couriershortestdeliverypath.beans.FullPath;
import com.avi.couriershortestdeliverypath.beans.Location;
import com.avi.couriershortestdeliverypath.beans.Path;
import com.avi.couriershortestdeliverypath.beans.Address;
import com.avi.couriershortestdeliverypath.util.Utility;

import java.util.*;

public class CourierShortestDeliveryPath {

    private static Stack<FullPath> pathStack = new Stack<FullPath>();
    private static List<FullPath> fullPaths = new LinkedList<>();
    private static List<Location> locations = new ArrayList<>();

    private static List<Path> paths = new ArrayList<>();
    private static Location endPointLocation;
    private static Location startPointLocation;

    private static Scanner addressScanner = new Scanner(System.in);
    private static boolean first = true;
    private String APIKEY;

    private static int nLocations;


    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String APIKEY) {
        this.APIKEY = APIKEY;
    }

    public static void main(String[] args) throws Exception {

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

        findPath();

        createPath(getShortestPath());
    }


    public static void findPath () {

        List<Path> pathsFromLocation;

        if (pathStack.isEmpty() && first) {

            first = false;
            pathsFromLocation = startPointLocation.getBranchPaths();

            pathsFromLocation.forEach(path ->
            {
                FullPath fp = new FullPath(nLocations - 1);
                fp.addToFullPath(path);

                pathStack.add(fp);
            });

            findPath();

        } else {

            if (!pathStack.isEmpty()) {


                FullPath fp = pathStack.pop();

                pathsFromLocation = fp.getBranchPathsToFullPath();

                if (pathsFromLocation.isEmpty()) {

                    Path lastPath = new Path(fp.getFp().getLast().getToLocation(), endPointLocation);
                    fp.addToFullPath(lastPath);
                    lastPath.setDistance(lastPath.getFromLocation().getPathTo(endPointLocation).getDistance());
                    fullPaths.add(fp);
                } else {

                    pathsFromLocation.forEach(path ->
                    {
                        FullPath fullPath = new FullPath(nLocations - 1);
                        fullPath.cloneFullPath(fp);
                        fullPath.addToFullPath(path);

                        pathStack.add(fullPath);
                    });
                }

                
                findPath();
            }
        }

    }

    public static FullPath getShortestPath() {

        FullPath shortestPath = null;
        double shortestDist = -1;

        for (FullPath fp : fullPaths) {

            double fpDist = fp.getFullPathDistance();

            if (shortestDist == -1) {

                shortestDist = fpDist;
                shortestPath = fp;
            } else if (shortestDist > fpDist) {

                shortestDist = fpDist;
                shortestPath = fp;
            }
        }

        return shortestPath;
    }

    public static void createPath(FullPath fp) {

        String URL = "https://www.google.com/maps/dir/";

        Address fromAddress = fp.getFp().getFirst().getFromLocation().getAddress();

        String[] pcs = fromAddress.getAddressLines().split(" ");
        URL = URL.concat(pcs[0] + "+" + pcs[1] + "+" + pcs[2] + ",+" + fromAddress.getLocality() + ",+" + fromAddress.getRegionCode() + "/");

        for (Path path : fp.getFp()) {

            Address toAddress = path.getToLocation().getAddress();

            String[] pieces = toAddress.getAddressLines().split(" ");

            String street = pieces[0] + "+" + pieces[1] + "+" + pieces[2] + ",";
            String locality = "+" + toAddress.getLocality() + ",";
            String regionCode = "+" + toAddress.getRegionCode() + "/";

            URL = URL.concat(street + locality + regionCode);
        }

        System.out.println("\n\n\n" + URL);
    }
}
