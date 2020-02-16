package com.frogobox.basegameboard2048.source;

public class PFASampleDataType {

    private int ID;
    private String DOMAIN;

    private String USERNAME;
    private int LENGTH;

    public PFASampleDataType() {
    }


    /**
     * Always use this constructor to generate data with values.
     *
     * @param ID       The primary key for the database (will be automatically set by the DB)
     * @param DOMAIN   Some sample String that could be in the DB
     * @param USERNAME Some sample String that could be in the DB
     * @param LENGTH   Some sample int that could be in the DB
     */
    public PFASampleDataType(int ID, String DOMAIN, String USERNAME, int LENGTH) {

        this.ID = ID;
        this.DOMAIN = DOMAIN;
        this.USERNAME = USERNAME;
        this.LENGTH = LENGTH;
    }

    /**
     * All variables need getters and setters. because the variables are private.
     */

    public int getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(int LENGTH) {
        this.LENGTH = LENGTH;
    }

    public String getDOMAIN() {
        return DOMAIN;
    }

    public void setDOMAIN(String DOMAIN) {
        this.DOMAIN = DOMAIN;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

}
