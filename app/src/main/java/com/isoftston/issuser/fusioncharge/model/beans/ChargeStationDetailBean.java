package com.isoftston.issuser.fusioncharge.model.beans;


import java.util.List;

/**
 *  by zhangwei
 */
public class ChargeStationDetailBean {

        private String address;
        private int averageScore;
        private int id;
        private double latitude;
        private double longitude;
        private String name;
        private String objType;
        private String openStatus;
        private String photoUrl;
        private List<PileList> pileList;
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setAverageScore(int averageScore) {
            this.averageScore = averageScore;
        }
        public int getAverageScore() {
            return averageScore;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
        public double getLatitude() {
            return latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
        public double getLongitude() {
            return longitude;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setObjType(String objType) {
            this.objType = objType;
        }
        public String getObjType() {
            return objType;
        }

        public void setOpenStatus(String openStatus) {
            this.openStatus = openStatus;
        }
        public String getOpenStatus() {
            return openStatus;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPileList(List<PileList> pileList) {
            this.pileList = pileList;
        }
        public List<PileList> getPileList() {
            return pileList;
        }

    @Override
    public String toString() {
        return "ChargeStationDetailBean{" +
                "address='" + address + '\'' +
                ", averageScore=" + averageScore +
                ", id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", objType='" + objType + '\'' +
                ", openStatus='" + openStatus + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", pileList=" + pileList +
                '}';
    }
}