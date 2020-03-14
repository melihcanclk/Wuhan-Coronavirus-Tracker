package com.melihcanclk.coronavirustracker.models;

public class LoadModel {
    private String state;
    private String country;
    private String lastRecord;
    private int diffFromPrevDay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLastRecord() {
        if(lastRecord.equals("")){
            return 0;
        }else
            return Integer.parseInt(lastRecord);
    }


    public void setLastRecord(String lastRecord) {
        this.lastRecord = lastRecord;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    @Override
    public String toString() {
        return "LoadModel{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", lastRecord=" + lastRecord +
                '}';
    }
}
