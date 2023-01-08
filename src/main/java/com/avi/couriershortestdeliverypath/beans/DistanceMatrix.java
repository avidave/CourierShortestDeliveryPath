package com.avi.couriershortestdeliverypath.beans;

import java.util.List;

public class DistanceMatrix {
    private String[] destination_addresses, origin_addresses;
    private List<Elements> rows;

    public List<Elements> getRows() {
        return rows;
    }

    public class Elements {
        private List<Element> elements;

        public List<Element> getElements() {
            return elements;
        }
    }

    public class Element {

        private Distance distance;
        private Duration duration;
        private String status;

        public double getDistance() {
            return distance.value;
        }

        public class Distance {

            private String text;
            private double value;
        }
        public class Duration {
            private String text;
            private double value;
        }
    }
}
