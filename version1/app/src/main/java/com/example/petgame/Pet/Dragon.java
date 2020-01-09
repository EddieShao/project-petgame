package com.example.petgame.Pet;

import android.os.Parcel;

import com.example.petgame.R;

class Dragon extends Pet {

    // inherited constructors

    Dragon(String name, String color) {
        super(name, color, R.raw.dragon_roar);
    }

    Dragon(String name, int health, int energy, int[] birthDate, String color) {
        super(name, health, energy, birthDate, color, R.raw.dragon_roar);
    }

    private Dragon(Parcel in) {
        super(in, R.raw.dragon_roar);
    }

    public static final Creator<Dragon> CREATOR = new Creator<Dragon>() {
        /**
         * Re-create this Dragon from the given Parcel.
         *
         * @param in The Parcel containing information on the Cat to re-create.
         * @return The re-created Dragon.
         */
        @Override
        public Dragon createFromParcel(Parcel in) {
            return new Dragon(in);
        }

        /**
         * Create a new array of Dragons of the given size.
         *
         * @param size The size of the array to return.
         * @return A new array of Dragons.
         */
        @Override
        public Dragon[] newArray(int size) {
            return new Dragon[0];
        }
    };

}
