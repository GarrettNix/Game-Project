package entity;

import org.newdawn.slick.opengl.Texture;

import tilemap.TileMap;

import static helpers.Render.*;

/**
 * 
 * @author Garrett
 * 
 * The Player class contains all data related to the player's avatar.
 * It is used for updating the player's position, actions, etc.
 *
 */

public class Player extends Entity {
	
	// player texture(s)
	private Texture texture;
	
	// effects
	private Texture damageVignette;
	private boolean drawDV;
	private int dvTimer;
	
	// player attributes
	private float health;
	private float maxHealth;
	private float energy;
	private float maxEnergy;
	private float food;
	private float maxFood;
	private float water;
	private float maxWater;
	private boolean sprinting;
	private boolean regenerating;
	private boolean recharging;
	private int regenTimer;
	private int regenTarget;
	private int rechargeTimer;
	private int rechargeTarget;
	
	public Player(TileMap tm) {
		super(tm);
		texture = quickLoad("entities", "player_placeholder");
		damageVignette = quickLoadTGA("effects", "damage_vignette");
		width = 40;
		height = 64;
		health = maxHealth = 100f;
		energy = maxEnergy = 100f;
		food = maxFood = 500f;
		water = maxWater = 500f;
		regenTarget = 600;
		rechargeTarget = 900;
	}

	public float getHealth() { return health; }
	public float getMaxHealth() { return maxHealth; }
	public float getEnergy() { return energy; }
	public float getMaxEnergy() { return maxEnergy; }
	public float getFood() { return food;}
	public float getMaxFood() { return maxFood; }
	public float getWater() { return water; }
	public float getMaxWater() { return maxWater; }
	public boolean isSprinting() { return sprinting; }
	public void setSprinting(boolean b) { 
		sprinting = b;
		if (energy <= 0) {
			energy = 0;
			sprinting = false;
		}
	}
	
	public void update() {
		basicMovement();
		updateSurvival();
		updateRegeneration();
		updateEffects();
	}
	
	private void basicMovement() {
		if (left) {
			if (sprinting) {
				x -= 3;
				energy -= 0.05;
				food -= 0.009;
			}
			else x -=2;
		}
		if (right) {
			if (sprinting) {
				x += 3;
				energy -= 0.05;
				food -= 0.009;
			}
			else x += 2;
		}
		if (up) {
			if (sprinting) {
				y -= 3;
				energy -= 0.05;
				food -= 0.009;
			}
			else y -= 2;
		}
		if (down) {
			if (sprinting) {
				y += 3;
				energy -= 0.05;
				food -= 0.009;
			}
			else y += 2;
		}
		// keep player on the map
		if (x < 20) x = 20;
		if (x > 63980) x = 63980;
		if (y < 64) y = 64;
		if (y > 64000) y = 64000;
	}
	
	private void updateSurvival() {
		food -= 0.002;
		water -= 0.003;
		if (food < 0) {
			food = 0;
		}
		if (water < 0) {
			water = 0;
		}
	}
	
	private void updateRegeneration() {
		// check health regen (if player has 90% or higher food)
		if (health < maxHealth && !regenerating) { // countdown to recharge
			if (food / maxFood > 0.895) {
				regenTimer++;
				if (regenTimer >= regenTarget) {
					regenTimer = 0;
					regenerating = true;
				}
			}
		}
		else if (regenerating) {
			health += 0.015;
			if (health >= maxHealth) {
				health = maxHealth;
				regenerating = false;
			}
		}
		// check energy regen
		if (energy < maxEnergy && !recharging) { // player is not sprinting, continue countdown to recharge
			if (!sprinting) {
				rechargeTimer++;
				if (rechargeTimer >= rechargeTarget) {
					rechargeTimer = 0;
					recharging = true;
				}
			}
			else rechargeTimer = 0; // player is sprinting, reset countdown
		}
		else if (recharging) {
			if (sprinting) {
				rechargeTimer = 0;
				recharging = false;
			}
			else {
				energy += 0.04;
				if (energy >= maxEnergy) {
					energy = maxEnergy;
					recharging = false;
				}
			}
		}
	}
	
	/**
	 * Update effetcs such as the damage vignette.
	 */
	private void updateEffects() {
		if (drawDV) {
			dvTimer--;
			if (dvTimer <= 0) {
				dvTimer = 0;
				drawDV = false;
			}
		}
	}
	
	/**
	 * Renders the player and effects to the screen.
	 */
	public void render() {
		// update map position (centers player)
		setMapPosition();
		// draw player
		drawImage(texture, x + xmap - width / 2, y + ymap - height);
		// draw effects
		if (drawDV) {
			drawImageFaded(damageVignette, 0, 0, (dvTimer / 60.0f) * 1.0f);
		}
	}
	
	/**
	 * Substracts <damage> health from the player's health and resets health regen.
	 */
	public void damage(float damage) {
		health -= damage;
		if (health < 0) health = 0;
		regenerating = false;
		regenTimer = 0;
		dvTimer = 60;
		drawDV = true;
	}
}