package com.avi.couriershortestdeliverypath.util;

import com.avi.couriershortestdeliverypath.beans.Address;
import com.avi.couriershortestdeliverypath.beans.DistanceMatrix;
import com.avi.couriershortestdeliverypath.beans.Location;
import com.avi.couriershortestdeliverypath.beans.Path;
import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Utility {

    public static boolean isValidAddress(Address ad, String key) throws IOException {

        Gson gson = new Gson();

        //Add Java to JSON code here.
        String jsonADDRESS = "{\"address\" : " + gson.toJson(ad) + "}";
        System.out.println("\n\n" + jsonADDRESS + "\n");

        URL url = new URL("https://addressvalidation.googleapis.com/v1:validateAddress?key=" + key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "*/*");

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(jsonADDRESS, 0, jsonADDRESS.length());
        osw.flush();
        osw.close();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        System.out.println(response.toString());

        JsonObject job = (new JsonParser()).parse(response.toString()).getAsJsonObject();

        JsonElement result = job.get("result").getAsJsonObject().get("verdict").getAsJsonObject().get("hasInferredComponents");
        JsonElement unconfirmed = job.get("result").getAsJsonObject().get("verdict").getAsJsonObject().get("hasUnconfirmedComponents");

        if (unconfirmed != null) {

            return false;
        }

        if (result != null) {

            return result.getAsBoolean();
        }

        return false;
    }

    public static void setDistances(List<Location> locations, String key) throws IOException {

        Gson gson = new Gson();

        StringJoiner address = new StringJoiner("|");

        locations.forEach(
            loc -> {
                String str = loc.getAddress().getAddressLines().replaceAll(" ", "%20") +
                        "%20" + loc.getAddress().getLocality() + "%2C" + loc.getAddress().getRegionCode();
                address.add(str);
            }
        );

        String fullAddress = address.toString();

        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?" +
                "destinations=" + fullAddress + "&origins=" + fullAddress + "&units=imperial&key=" + key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        System.out.println(response.toString());

        DistanceMatrix dm = gson.fromJson(response.toString(), DistanceMatrix.class);

        final int[] originNum = {0};

        dm.getRows().forEach(elements ->
        {
            Location origLocation = locations.get(originNum[0]);

            final int[] destNum = {0};

            elements.getElements().forEach(element ->
            {
                Location destLocation = locations.get(destNum[0]);

                if (!origLocation.equals(destLocation)) {
                    double distance = element.getDistance();

                    Path newPath = new Path(origLocation, destLocation);
                    newPath.setDistance(distance);

                    origLocation.addPath(newPath);
                }

                destNum[0]++;
            });

            originNum[0]++;
        });
    }

}
