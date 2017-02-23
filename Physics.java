package ser.main;

import java.util.LinkedList;

import ser.main.classes.EntityA;
import ser.main.classes.EntityB;

@SuppressWarnings("unused")
public class Physics {
	private static Sound sound;
	
	
	public static boolean Collision(EntityA enta, EntityB entb) {

		if (enta.getBounds().intersects(entb.getBounds())) {
			sound = new Sound();
			sound.playExplosion();
			return true;
		}
		return false;
	}

	public static boolean Collision(EntityB entb, EntityA enta) {

		if (entb.getBounds().intersects(enta.getBounds())) {
			sound = new Sound();
			sound.playExplosion();
			return true;
		}
		return false;
	}
}
