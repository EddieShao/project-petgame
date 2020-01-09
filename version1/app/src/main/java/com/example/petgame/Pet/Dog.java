package com.example.petgame.Pet;

import android.os.Parcel;

import com.example.petgame.R;

class Dog extends Pet {

    // inherited constructors

    Dog(String name, String color) {
        super(name, color, R.raw.dog_bark_1);
    }

    Dog(String name, int health, int energy, int[] birthDate, String color) {
        super(name, health, energy, birthDate, color, R.raw.dog_bark_1);
    }

    private Dog(Parcel in) {
        super(in, R.raw.dog_bark_1);
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        /**
         * Re-create this Dog from the given Parcel.
         *
         * @param in The Parcel containing information on the Dog to re-create.
         * @return The re-created Dog.
         */
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        /**
         * Create a new array of Dogs of the given size.
         *
         * @param size The size of the array to return.
         * @return A new array of Dogs.
         */
        @Override
        public Dog[] newArray(int size) {
            return new Dog[0];
        }
    };

}
