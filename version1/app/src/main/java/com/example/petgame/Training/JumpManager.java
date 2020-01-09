package com.example.petgame.Training;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class JumpManager extends Observable{

	private List<PetTraining> pets = new ArrayList<>();
	// false = on ground, true = in air
	private boolean state;

	public JumpManager(){
		state = false;
	}

	public synchronized void addObserver(PetTraining o) {
		pets.add(o);
	}

	public boolean getState(){
		return state;
	}

	@Override
	public void notifyObservers() {
		if (hasChanged()){
			for (PetTraining pet: pets) {
				pet.update(this,state);
			}
			clearChanged();
		}
	}

	public void setState(boolean state) {
		if (this.state || state){ // if not changed from onground to on ground, update (jump)
			this.state = state;
			Log.d("jm", "state changed to " + state);
			setChanged();
		}
		notifyObservers();
	}



}
