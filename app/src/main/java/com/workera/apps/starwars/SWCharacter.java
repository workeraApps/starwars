package com.workera.apps.starwars;


import java.io.Serializable;

/**
 * An {@link SWCharacter} object contains information related to a single Star Wars Character.
 */

public class SWCharacter implements Serializable {

    /** Mass of the Character */
    private double mMass;

    /** Height of the Character */
    private double mHeight;

    /** Name of the Character */
    private String mName;

    /** Time and Date of when the data was created */
    private String mdTime;


    /**
     * Constructs a new {@link SWCharacter} object.
     *
     * @param  mass is the mass of the character in kg
     * @param height is the height of the character in cm
     * @param dtime is the date and time when the record was created in Z format (ISO)
     */
    public SWCharacter(double mass, double height, String name, String dtime) {
        mMass = mass;
        mHeight = height;
        mName = name;
        mdTime = dtime;

    }


    /**
     * Returns the mass of the character.
     */
    public double getMass() {
        return mMass;
    }

    /**
     * Returns the height of the character.
     */
    public double getHeight() {
        return mHeight;
    }


    /**
     * Returns the name of the character.
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns the date and time when the character record was created.
     */
    public String getdTime() {
        return mdTime;
    }



}
