package com.example.petgame.Pet;

/** Uses the PetImageBuilder to select images according to input. */
class PetImageDirector {

    /** Select a Drawable ID to return depending on the given specifications.
     *
     * @param species Which species variant to select.
     * @param color Which color variant to select.
     * @param passive Whether to select a passive or active (interaction) image.
     * @param fullBody Whether or not to select a full body image.
     * @return The Drawable ID associated with the given selections.
     */
    static int selectDrawable(String species, String color, boolean passive, boolean fullBody) {
        // create a builder with the Drawables stored
        PetImageBuilder builder = new PetImageBuilder();
        // select and return according to given inputs
        int[][] petAction = builder.selectActionType(passive, fullBody);
        int[] petSpecies = builder.selectSpeciesSet(petAction, species);
        return builder.selectColorVariant(petSpecies, color);
    }

}
