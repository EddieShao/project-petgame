package com.example.petgame.PetGame.PetGameSystem;

import com.example.petgame.Pet.Pet;

/** A builder for the complicated PetGameFacade class interaction set. */
class PetGameBuilder {

    /** The Pet in the PetGame to build. */
    private Pet pet;

    /** The Account's current balance. */
    private int accountMoney;

    /** The Account's current number of free-train candies. */
    private int numCandies;

    /**
     * Construct a new PetGameBuilder with the given components for construction.
     *
     * @param pet The Pet in the PetGame.
     * @param accMoney The Account's current balance.
     */
    PetGameBuilder(Pet pet, int accMoney, int candies) {
        this.pet = pet;
        this.accountMoney = accMoney;
        this.numCandies = candies;
    }

    PetGame buildBasePetGame() {
        return new PetGame(this.pet, this.accountMoney, this.numCandies);
    }

    HealthManager buildHealthManager(PetGame game) {
        return new HealthManager(game);
    }

    EnergyManager buildEnergyManager(PetGame game) {
        return new EnergyManager(game);
    }

    PetInteractor buildPetInteractor(PetGame game) {
        return new PetInteractor(game);
    }

    AccountBalanceManager buildAccountBalanceManager(PetGame game) {
        return new AccountBalanceManager(game);
    }

    OptionsManager buildOptionsManager(PetGame game) {
        return new OptionsManager(game);
    }

    CandyManager buildCandyManager(PetGame game) {
        return new CandyManager(game);
    }

}
