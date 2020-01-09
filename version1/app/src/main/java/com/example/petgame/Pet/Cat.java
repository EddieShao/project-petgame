package com.example.petgame.Pet;

import android.os.Parcel;

import com.example.petgame.R;

class Cat extends Pet {

    // inherited constructors

    Cat(String name, String color) {
        super(name, color, R.raw.cat_meow_1);
    }

    Cat(String name, int health, int energy, int[] birthDate, String color) {
        super(name, health, energy, birthDate, color, R.raw.cat_meow_1);
    }

    private Cat(Parcel in) {
        super(in, R.raw.cat_meow_1);
    }

    public static final Creator<Cat> CREATOR = new Creator<Cat>() {
        /**
         * Re-create this Cat from the given Parcel.
         *
         * @param in The Parcel containing information on the Cat to re-create.
         * @return The re-created Cat.
         */
        @Override
        public Cat createFromParcel(Parcel in) {
            return new Cat(in);
        }

        /**
         * Create a new array of Cats of the given size.
         *
         * @param size The size of the array to return.
         * @return A new array of Cats.
         */
        @Override
        public Cat[] newArray(int size) {
            return new Cat[0];
        }
    };

}
