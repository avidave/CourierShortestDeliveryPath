package com.avi.couriershortestdeliverypath.beans;

import java.util.Arrays;

public class Address {

    private String regionCode;
    private String locality;
    private String[] addressLines = new String[1];

    public Address(String regionCode, String locality, String addressLines) {
        this.regionCode = regionCode;
        this.locality = locality;
        this.addressLines[0] = addressLines;
    }

    public String getAddressLines() {
        return addressLines[0];
    }

    public String getLocality() {
        return locality;
    }

    public String getRegionCode() {
        return regionCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "regionCode='" + regionCode + '\'' +
                ", locality='" + locality + '\'' +
                ", addressLines=" + Arrays.toString(addressLines) +
                '}';
    }
}
