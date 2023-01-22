# CourierShortestDeliveryPath

## Program Goal

A courier has multiple delivery locations that they must arrive at to deliver packages. However, the courier wants to take the shortest path by distance such that they visit each of the locations exactly once. *The program solely takes distance into account*.

## Required Inputs

This program takes in any number of valid locations as input and produces a URL that draws the shortest route on Google Maps. It also requires a GCP API key to work (as input). For now, it takes input through the console.

To use the program, first enter the API key when prompted (as shown below). Then you will be prompted to enter a location. It must be entered in the order Country, City, and Street - all separated by underscores.

![image](https://user-images.githubusercontent.com/55364141/213923628-935b12fa-39c1-451e-aaee-addff5382a5c.png)

You will then be prompted to state whether it is the starting point for the courier's deliveries, an ending point, or neither. If it is a starting point you must enter "S", if it is an ending point you must enter "E", and any input (except "E" or "S") is accepted for neither.

![image](https://user-images.githubusercontent.com/55364141/213923444-1126e8ea-25f7-45c2-a645-2c509de7f178.png)
![image](https://user-images.githubusercontent.com/55364141/213923453-5217b644-e1da-40c6-8d88-5eb4f9f9a768.png)
![image](https://user-images.githubusercontent.com/55364141/213923469-18df1a7a-b0f8-4ac1-9c37-fc251b5fab06.png)

Once all addresses have been inputted, then when prompted for the next location, enter a "-".

![image](https://user-images.githubusercontent.com/55364141/213923505-5a96a09f-93b8-4c85-9c64-f6a4a51bab45.png)

Once all locations have been inputted, the program will then return the URL containing the shortest path on Google Maps.

It uses the Google Maps Distance Matrix and Address Validation APIs to get the location data (including distance between all locations).

This program is written in Java, and is a Maven project. It uses a Gson dependency to convert java objects to JSON and vice versa. 
