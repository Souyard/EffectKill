package fr.bukkit.effectkill.utils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Particle{

	public static void play(Location location, Effect effect) {
		play(location, effect, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 1);
	}

	public static void play(Location location, Effect effect, int data) {
		play(location, effect, data, data, 0.0f, 0.0f, 0.0f, 0.0f, 1);
	}

	public static void play(Location location, Effect effect, float offsetX, float offsetY, float offsetZ) {
		play(location, effect, 0, 0, offsetX, offsetY, offsetZ, 0.0f, 1);
	}

	public static void play(Location location, Effect effect, float speed) {
		play(location, effect, 0, 0, 0.0f, 0.0f, 0.0f, speed, 1);
	}

	public static void play(Location loc, Effect effect, int i, Vector vector, float f) {
		play(loc, effect, i, vector, f);
	}
	public static void play(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
		location.getWorld().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, amount, 128);
	}
	public static void drawTornado(Location loc, float radius,float increase){
		float y = (float) loc.getY();
		for(double t = 0; t<50; t+=0.05){
			float x = radius*(float)Math.sin(t);
			float z = radius*(float)Math.cos(t);
			play(loc.add(loc.getX() + x, y,loc.getZ() + z), Effect.FLAME);
			y += 0.01f;
			radius += increase;
		}
	}
}
