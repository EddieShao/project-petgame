package com.example.petgame.Pet;

import android.os.Parcel;

import com.example.petgame.R;

class Hamster extends Pet {

    // inherited constructors

    Hamster(String name, String color) {
        super(name, color, R.raw.hamster_squeek_1);
    }

    Hamster(String name, int health, int energy, int[] birthDate, String color) {
        super(name, health, energy, birthDate, color, R.raw.hamster_squeek_1);
    }

    private Hamster(Parcel in) {
        super(in, R.raw.hamster_squeek_1);
    }

    public static final Creator<Hamster> CREATOR = new Creator<Hamster>() {
        /**
         * Re-create this Hamster from the given Parcel.
         *
         * @param in The Parcel containing information on the Hamster to re-create.
         * @return The re-created Hamster.
         */
        @Override
        public Hamster createFromParcel(Parcel in) {
            return new Hamster(in);
        }

        /**
         * Create a new array of Hamsters of the given size.
         *
         * @param size The size of the array to return.
         * @return A new array of Hamsters.
         */
        @Override
        public Hamster[] newArray(int size) {
            return new Hamster[0];
        }
    };

}
