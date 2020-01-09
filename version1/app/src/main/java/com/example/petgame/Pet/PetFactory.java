package com.example.petgame.Pet;

/** Factory used to create Pet subclasses depending on input. */
public class PetFactory {

    /**
     * Construct and return a new Pet.
     *
     * @param petType type of the new pet.
     * @param petName name of this new pet.
     * @param petColor color of this new pet.
     * @return the new Pet constructed.
     */
    public Pet constructPet(String petType, String petName, String petColor){
        switch (petType){
            case ("Dog"):
                return new Dog(petName, petColor);
            case ("Cat"):
                return new Cat(petName, petColor);
            case ("Hamster"):
                return new Hamster(petName, petColor);
            case ("Dragon"):
                return new Dragon(petName, petColor);
            default:
                return null;
        }
    }

    /**
     * Re-construct the pet with given information.
     *
     * @param petType type of the pet.
     * @param petName name of this pet.
     * @param health remaining health of this pet.
     * @param energy remaining energy of this pet.
     * @param year the year when the pet was first constructed.
     * @param month the month when the pet was first constructed.
     * @param day the day when the pet was first constructed.
     * @param petColor the color of this pet.
     * @return the Pet constructed with given information.
     */
    public Pet constructPet(String petType, String petName, int health, int energy, int year, int month,
                            int day, String petColor){
        switch (petType){
            case ("Dog"):
                return new Dog(petName,health,energy,new int[]{year,month,day},petColor);
            case ("Cat"):
                return new Cat(petName,health,energy,new int[]{year,month,day},petColor);
            case ("Hamster"):
                return new Hamster(petName,health,energy,new int[]{year,month,day},petColor);
            case ("Dragon"):
                return new Dragon(petName,health,energy,new int[]{year,month,day},petColor);
            default:
                return null;
        }
    }
}
