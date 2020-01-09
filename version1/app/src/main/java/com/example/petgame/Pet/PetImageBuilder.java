package com.example.petgame.Pet;

import com.example.petgame.R;

/** Selects corresponding images using selectDrawables */
class PetImageBuilder {

    /** Set of all passive state images of all Pets. */
    private int[][] passiveImageSet;

    /** Set of all active state images of all Pets. */
    private int[][] activeImageSet;

    /** Set of all head only images of all Pets. */
    private int[][] headImageSet;

    /** Create a new PetImageBuilder with all image ID's set. */
    PetImageBuilder() {
        // set all passive images
        int[] passiveDogs = new int[]
                {R.drawable.doggo__white_happy, R.drawable.doggo_black_happy, R.drawable.doggo_brown_happy};
        int[] passiveCats = new int[]
                {R.drawable.meow_white_happy, R.drawable.meow_black_happy, R.drawable.meow_brown_happy};
        int[] passiveHamsters = new int[]
                {R.drawable.hamster_white_happy, R.drawable.hamster_black_happy, R.drawable.hamster_brown_happy};
        int[] passiveDragons = new int[]
                {R.drawable.dragon_red_passive, R.drawable.dragon_red_passive, R.drawable.dragon_red_passive};
        this.passiveImageSet = new int[][] {passiveDogs, passiveCats, passiveHamsters, passiveDragons};

        // set all active images
        int[] activeDogs = new int[]
                {R.drawable.doggo__white_laugh, R.drawable.doggo_black_laugh, R.drawable.doggo_brown_laugh};
        int[] activeCats = new int[]
                {R.drawable.meow_white_laugh, R.drawable.meow_black_laugh, R.drawable.meow_brown_laugh};
        int[] activeHamsters = new int[]
                {R.drawable.hamster_white_laugh, R.drawable.hamster_black_laugh, R.drawable.hamster_brown_laugh};
        int[] activeDragons = new int[]
                {R.drawable.dragon_red_active, R.drawable.dragon_red_active, R.drawable.dragon_red_active};
        this.activeImageSet = new int[][] {activeDogs, activeCats, activeHamsters, activeDragons};

        // set all head-only images
        int[] avatarDogs = new int[]
                {R.drawable.avatar_dog_white, R.drawable.avatar_dog_black, R.drawable.avatar_dog_brown};
        int[] avatarHamsters = new int[]
                {R.drawable.avatar_hamster_white, R.drawable.avatar_hamster_black, R.drawable.avatar_hamster_brown};
        int[] avatarCats = new int[]
                {R.drawable.avatar_cat_white, R.drawable.avatar_cat_black, R.drawable.avatar_cat_brown};
        int[] avatarDragons = new int[]
                {R.drawable.avatar_dragon_red, R.drawable.avatar_dragon_red, R.drawable.avatar_dragon_red};
        this.headImageSet = new int[][] {avatarDogs, avatarCats, avatarHamsters, avatarDragons};
    }

    /** Return an image set of either passive or active pets depending on specified action type. */
    int[][] selectActionType(boolean passive, boolean fullBody) {
        if (!fullBody) {
            return this.headImageSet;
        } else {
            if (passive) {
                return this.passiveImageSet;
            } else {
                return this.activeImageSet;
            }
        }
    }

    /** Return set of species images from the given image set depending on the specified species. */
    int[] selectSpeciesSet(int[][] imageSet, String species) {
        switch (species) {
            case ("Dog"):
                return imageSet[0];
            case ("Cat"):
                return imageSet[1];
            case ("Hamster"):
                return imageSet[2];
            case ("Dragon"):
                return imageSet[3];
            default:
                return null;
        }
    }

    /** Return the ID of an image from the given image set depending on the specified color. */
    int selectColorVariant(int[] imageSet, String color) {
        switch (color) {
            case ("White"):
                return imageSet[0];
            case ("Black"):
                return imageSet[1];
            case ("Brown"):
                return imageSet[2];
            default:
                return -1;
        }
    }

}
