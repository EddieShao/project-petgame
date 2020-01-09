package com.example.petgame.PetGame.PetGameSystem;

import com.example.petgame.Pet.Pet;

/** The construction manager that assembles a PetGameFacade from a PetGameBuilder. */
public class PetGameDirector {

    /**
     * Create a PetGameFacade with the given Pet, Account balance, and number of candies.
     *
     * @param pet The Pet to put in the PetGame.
     * @param accMoney The Account's balance.
     * @param candies The Account's number of candies.
     * @return A new PetGameFacade with the given initial Pet and values.
     */
    public static PetGameFacade buildPetGame(Pet pet, int accMoney, int candies) {
        PetGameBuilder builder = new PetGameBuilder(pet, accMoney, candies);

        // create the base pet game
        PetGame base = builder.buildBasePetGame();

        // create all managers for the PetGame
        HealthManager healthManager = builder.buildHealthManager(base);
        EnergyManager energyManager = builder.buildEnergyManager(base);
        PetInteractor interactor = builder.buildPetInteractor(base);
        OptionsManager optionsManager = builder.buildOptionsManager(base);
        AccountBalanceManager balanceManager = builder.buildAccountBalanceManager(base);
        CandyManager candyManager = builder.buildCandyManager(base);

        // create a facade that works with all the managers
        PetGameFacade petGame = new PetGameFacade(healthManager, energyManager, interactor,
                balanceManager, optionsManager, candyManager);

        // set the facade as receiver of all managers' update interfaces
        healthManager.setReceiver(petGame);
        energyManager.setReceiver(petGame);
        interactor.setReceiver(petGame);
        balanceManager.setReceiver(petGame);
        candyManager.setReceiver(petGame);

        // return the completely built PetGame facade
        return petGame;
    }

}
