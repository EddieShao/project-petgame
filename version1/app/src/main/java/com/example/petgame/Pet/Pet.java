package com.example.petgame.Pet;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.Period;

/** A pet. */
public abstract class Pet implements Parcelable {

    /** The name of this Pet. */
    private String name;

    /** Getter for name */
    public String getName() {
        return this.name;
    }

    /** This Pet's health value. */
    private int health;

    /** Getter for health */
    public int getHealth() {
        return this.health;
    }

    /** This Pet's energy level value. */
    private int energy;

    /** Getter for energy. */
    public int getEnergy() {
        return this.energy;
    }

    /** The birth date of this Pet. */
    private final LocalDate BIRTH_DATE;

    /** The color (appearance) of this Pet. */
    private final String COLOR;

    /** Getter for COLOR */
    public String getCOLOR() {
        return this.COLOR;
    }

    /** Responsible for selecting appropriate image ID for this Pet. */
    private PetImageBuilder imageBuilder;

    /** The sound this pet makes when interacted with. */
    private int soundID;

    /** Getter for the sound ID. */
    public int getSoundID() {
        return this.soundID;
    }

    /**
     * Create a new Pet that is born at this current moment.
     * Use this to create a brand new Pet.
     *
     * @param name The name to assign to this Pet.
     * @param color The color appearance of this Pet.
     */
    public Pet(String name, String color, int soundID) {
        this.name = name;
        this.health = 100;  // start with full health
        this.energy = 100;  // start with full energy
        this.BIRTH_DATE = LocalDate.now();  // this Pet was just born!
        this.COLOR = color;
        this.imageBuilder = new PetImageBuilder();
        this.soundID = soundID;
    }

    /**
     * Create a new Pet with the given parameters.
     * Use this to re-create Pets that already exist in file.
     *
     * @param name The name to give this Pet.
     * @param health The health of this Pet.
     * @param energy The energy level of this Pet.
     * @param birthDate The birth date of this Pet [year, month, date].
     * @param color The color appearance of this Pet.
     */
    public Pet(String name, int health, int energy, int[] birthDate, String color, int soundID) {
        this.name = name;
        this.health = health;
        this.energy = energy;
        this.BIRTH_DATE = LocalDate.of(birthDate[0], birthDate[1], birthDate[2]);
        this.COLOR = color;
        this.imageBuilder = new PetImageBuilder();
        this.soundID = soundID;
    }

    /**
     * Create a new Pet with the data from the given Parcel.
     * This method should only be used by CREATOR.createFromParcel to unpack Pet Parcels.
     *
     * @param in The Parcel containing data on the Pet to create.
     */
    public Pet(Parcel in, int soundID) {
        this.name = in.readString();
        int year = in.readInt();
        int month = in.readInt();
        int date = in.readInt();
        this.BIRTH_DATE = LocalDate.of(year, month, date);
        this.health = in.readInt();
        this.energy = in.readInt();
        this.COLOR = in.readString();
        this.imageBuilder = new PetImageBuilder();
        this.soundID = soundID;
    }

    /** Return whether this Pet is alive or not. */
    public boolean isAlive() {
        return health > 0;
    }

    /** Returns in order of [year, month, date]. */
    public int[] getBirthDate() {
        return new int[] {this.BIRTH_DATE.getYear(), this.BIRTH_DATE.getMonthValue(),
                this.BIRTH_DATE.getDayOfMonth()};
    }

    /**
     * Obtain the age of this Pet as a Period of years, months, and days.
     *
     * @return The Period this Pet has been alive for.
     */
    public Period getAge() {
        return Period.between(this.BIRTH_DATE, LocalDate.now());
    }

    /**
     * Obtain the age of this Pet as an int of days.
     *
     * @return The number of days this Pet has been alive for.
     */
    public int getAgeInDays() {
        return this.getAge().getDays();
    }

    /**
     * Change the health of this Pet by the given amount.
     *
     * @param amount The amount to increase or decrease this Pet's health by.
     */
    public void updateHealth(int amount) {
        this.health += amount;  // update the health

        if (this.health > 100) {  // level health off at max whenever it goes over
            this.health = 100;
        }
    }

    /**
     * Change the energy of this Pet by the given amount.
     *
     * @param amount The amount to increase or decrease this Pet's health by.
     */
    public void updateEnergy(int amount) {
        this.energy += amount;  // update the energy

        if (this.energy > 100) {  // level energy off at max whenever it goes over
            this.energy = 100;
        } else if (this.energy < 0) {  // level energy off at 0 whenever it goes under
            this.energy = 0;
        }
    }

    /** Return the ID corresponding to the Drawable.
     *
     * @param passive Whether a passive image should be obtained.
     * @param fullBody Whether a full body image should be obtained.
     * @return The Drawable ID value.
     */
    public int getDrawableID(boolean passive, boolean fullBody) {
        return PetImageDirector.selectDrawable(this.getClass().getSimpleName(), this.getCOLOR(),
                passive, fullBody);
    }

    // Implementations for Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Store data about this Pet in the given Parcel. When reading data, order to read will be
     * the same as order of input
     * name -> b-year -> b-month -> b-day -> health -> energy
     *
     * @param out The Parcel to write to.
     * @param flags Additional flags about how the how this Pet should be written.
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.name);
        out.writeInt(this.BIRTH_DATE.getYear());
        out.writeInt(this.BIRTH_DATE.getMonthValue());
        out.writeInt(this.BIRTH_DATE.getDayOfMonth());
        out.writeInt(this.health);
        out.writeInt(this.energy);
        out.writeString(this.COLOR);
    }

}
