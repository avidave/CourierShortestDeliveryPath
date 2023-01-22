# CourierShortestDeliveryPath

## Program Goal

A courier has multiple delivery locations that they must arrive at to deliver packages. However, the courier wants to take the shortest path by distance such that they visit each of the locations exactly once. *The program solely takes distance into account*.

This program is written in Java, and is a Maven project. It uses a Gson dependency to convert java objects to JSON and vice versa. 
It uses the Google Maps Distance Matrix and Address Validation APIs to get the location data (including distance between all locations).

## Required Inputs

This program takes in any number of valid locations as input and produces a URL that draws the shortest route on Google Maps. It also requires a GCP API key to work (as input). For now, it takes input through the console.

To use the program, first enter the API key when prompted (as shown below). Then you will be prompted to enter a location. It must be entered in the order Country ([check Google Maps region codes](https://developers.google.com/maps/coverage)), City, and Street - all separated by underscores. For example, CA_Montreal_3358 Papineau Ave.

![image](https://user-images.githubusercontent.com/55364141/213923628-935b12fa-39c1-451e-aaee-addff5382a5c.png)

You will then be prompted to state whether it is the starting point for the courier's deliveries, an ending point, or neither. If it is a starting point you must enter "S", if it is an ending point you must enter "E", and any input (except "E" or "S") is accepted for neither.

![image](https://user-images.githubusercontent.com/55364141/213923444-1126e8ea-25f7-45c2-a645-2c509de7f178.png)
![image](https://user-images.githubusercontent.com/55364141/213923453-5217b644-e1da-40c6-8d88-5eb4f9f9a768.png)
![image](https://user-images.githubusercontent.com/55364141/213923469-18df1a7a-b0f8-4ac1-9c37-fc251b5fab06.png)

Once all addresses have been inputted, then when prompted for the next location, enter a "-".

![image](https://user-images.githubusercontent.com/55364141/213923505-5a96a09f-93b8-4c85-9c64-f6a4a51bab45.png)

Once all locations have been inputted, the program will then return the URL containing the shortest path on Google Maps.

## Classes and Dependencies

THe classes used include a Utility class, as well as several Java beans: Address, Location, DistanceMatrix, FullPath, Path. The logic for finding the shortest path is written in methods contained in the main class.

### CourierShortestDeliveryPath (main)

The main class contains the methods responsible for finding the shortest path. These methods are:

* findPath - a recursive function which takes in the starting location and finds all path combinations. It utilizes a stack of FullPaths (look at the FullPath class below) to recursively find all complete shortest path combinations. Once a FullPath has been completed, it adds it to a list of completed FullPaths, which will be used to calculate the shortest path.
** Basically, findPath takes the starting location, gets the possible Paths (excluding onces containing the starting or ending locations), and creates FullPaths for each possibility (which are added to a stack). It then recursively calls itself. findPath will recognize that the stack is not empty, and will pop the latest item in the stack. It will find all possible Paths for this FullPath (excluding starting/ending locations and locations already in the FullPath), and add new FullPaths containing the Path combinations. If at any point, a FullPath popped from the stack has no combinations, a Path containing the endpoint will be added to the FullPath, and it will be added to a list of completed FullPaths.
* getShortestPath - which finds the path with the shortest distance, using a list of completed FullPaths (and calcuating the distance for each).
* createPath - which takes the shortest path and returns a URL that draws the shortest path on Google Maps.

*Note that as inputs are entered into the main method, they are added to Address object, which are then validated. If they are valid addresses, then these objects are added to Location objects.*

### Utility

The Utility class contains two methods:

* isValidAddress - takes in an Address object (which are created as locations are entered in the main method) and the API key, and checks if the location is valid using the Address Validation API by making a POST call and converted the Address objects into JSON input using Gson. The function returns a boolean value.
* setDistances - takes a list of Locations (after all locations have been entered) and the API key, and retrieves distance between each of the locations by making a GET call, which are then inputted into a DistanceMatrix object using Gson to convert the JSON into the DistanceMatrix object. It makes use of the Distance Matrix API, and then adds the distances to the proper Location object.

### Address

The Address class requires a region code, locality, and address. It sole purpose is to hold the location data inputted by the user in the console, and be prepared to be used as JSON input for the Address Validation API (check Utility).

### Location

Valid addresses are added to newly created Location objects (and to a list of locations in the main class). Location objects contain an Address object, booleans that state whether or not they are starting or ending locations, and a list of paths (to other locations). They require an Address object to be constructed, and some methods include: isStartPoint, isEndPoint, setStartPoint, setEndPoint, setPaths, hasPath, addPath, getBranchPaths (which returns a list of locations with the starting and ending locations removed).

### Path

Paths are objects that require two Locations (the origin Location and destination Location). They are used to hold distances between locations, and to find all the combinations of FullPaths. Methods include: isLocationPresent, getFromLocation, getToLocation, getDistance, setDistance, hasEndpoint.

### DistanceMatrix

The DistanceMatrix class is used solely for taking the distances between locations from the Distance Matrix API. To properly get all the distances, it is designed to match the JSON object that is returned from the API call (look at the Utility class).

### 

The FullPath class contains a LinkedList of Paths. It is used by the main method in a Stack, to generate different route combinations. Methods include: getFp, setFp, isPathInFullPath, isFullPathComplete, isLastPathToLocation, addToFullPath, cloneFullPath, getBranchesToFullPath.


