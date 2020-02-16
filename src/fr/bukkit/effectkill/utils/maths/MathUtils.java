package fr.bukkit.effectkill.utils.maths;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.Random;

public class MathUtils {

	public static Random random = new Random(System.nanoTime());
	public static BlockFace[] axis = new BlockFace[] { BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST };
	public static byte[] axisByte = new byte[] { 3, 4, 2, 5 };
	public static final float degreesToRadians = 0.017453292F;

	public static double offset(Location a, Location b) {
		return offset(a.toVector(), b.toVector());
	}

	public static double offset(Vector a, Vector b) {
		return a.subtract(b).length();
	}

	public static float randomRange(float min, float max) {
		return min + (float)Math.random() * (max - min);
	}

	public static int randomRange(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;
		return randomNum;
	}

	public static double randomDouble(double min, double max) {
		return (Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min);
	}

	public static Vector getBackVector(Location loc) {
		float newZ = (float)(loc.getZ() + 0.75 * Math.sin(Math.toRadians(loc.getYaw() + 90.0f)));
		float newX = (float)(loc.getX() + 0.75 * Math.cos(Math.toRadians(loc.getYaw() + 90.0f)));
		return new Vector(newX - loc.getX(), 0.0, newZ - loc.getZ());
	}

	public static double randomRange(double min, double max) {
		return (Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min);
	}

	public static int randomRangeInt(int min, int max) {
		return (int)((Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
	}

	public static int randRange(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;
		return randomNum;
	}

	public static double arrondi(double A, int B) {
		return (int)(A * Math.pow(10.0, B) + 0.5) / Math.pow(10.0, B);
	}

	public static int getRandomWithExclusion(int start, int end, int... exclude) {
		int rand = start + random.nextInt(end - start + 1 - exclude.length);
		for (int ex : exclude) {
			if (rand < ex) {
				break;
			}
			++rand;
		}
		return rand;
	}

	public static LinkedList<Vector> createCircle(double n, double n2) {
		double n3 = n * n2;
		double n4 = 6.2 / n3;
		LinkedList<Vector> list = new LinkedList<Vector>();
		for (int n5 = 0; n5 < n3; ++n5) {
			double n6 = n5 * n4;
			list.add(new Vector(n * Math.cos(n6), 0.0, n * Math.sin(n6)));
		}
		return list;
	}

	public static float getLookAtYaw(Vector motion) {
		double dx = motion.getX();
		double dz = motion.getZ();
		double yaw = 0.0;
		if (dx != 0.0) {
			if (dx < 0.0) {
				yaw = 4.7;
			}
			else {
				yaw = 1.5;
			}
			yaw -= Math.atan(dz / dx);
		}
		else if (dz < 0.0) {
			yaw = 3.14;
		}
		return (float)(-yaw * 180.0 / 3.14 - 90.0);
	}

	public static boolean elapsed(long from, long required) {
		return System.currentTimeMillis() - from > required;
	}

	public static Vector getBumpVector(Entity entity, Location from, double power) {
		Vector bump = entity.getLocation().toVector().subtract(from.toVector()).normalize();
		bump.multiply(power);
		return bump;
	}

	public static Vector getPullVector(Entity entity, Location to, double power) {
		Vector pull = to.toVector().subtract(entity.getLocation().toVector()).normalize();
		pull.multiply(power);
		return pull;
	}

	public static void bumpEntity(Entity entity, Location from, double power) {
		entity.setVelocity(getBumpVector(entity, from, power));
	}

	public static void bumpEntity(Entity entity, Location from, double power, double fixedY) {
		Vector vector = getBumpVector(entity, from, power);
		vector.setY(fixedY);
		entity.setVelocity(vector);
	}

	public static void pullEntity(Entity entity, Location to, double power) {
		entity.setVelocity(getPullVector(entity, to, power));
	}

	public static void pullEntity(Entity entity, Location from, double power, double fixedY) {
		Vector vector = getPullVector(entity, from, power);
		vector.setY(fixedY);
		entity.setVelocity(vector);
	}

	public static Vector rotateAroundAxisX(Vector v, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double y = v.getY() * cos - v.getZ() * sin;
		double z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public static Vector rotateAroundAxisY(Vector v, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = v.getX() * cos + v.getZ() * sin;
		double z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static Vector rotateAroundAxisZ(Vector v, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = v.getX() * cos - v.getY() * sin;
		double y = v.getX() * sin + v.getY() * cos;
		return v.setX(x).setY(y);
	}

	public static Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
		rotateAroundAxisX(v, angleX);
		rotateAroundAxisY(v, angleY);
		rotateAroundAxisZ(v, angleZ);
		return v;
	}

	public static Vector rotate(Vector v, Location l) {
		double yaw = l.getYaw() / 180.0f * 3.14;
		double pitch = l.getPitch() / 180.0f * 3.14;
		v = rotateAroundAxisX(v, pitch);
		v = rotateAroundAxisY(v, -yaw);
		return v;
	}

	public static byte toPackedByte(float f) {
		return (byte)(f * 256.0f / 360.0f);
	}

	public static Vector getRandomVector() {
		double x = random.nextDouble() * 2.0 - 1.0;
		double y = random.nextDouble() * 2.0 - 1.0;
		double z = random.nextDouble() * 2.0 - 1.0;
		return new Vector(x, y, z).normalize();
	}

	public static Vector getRandomCircleVector() {
		double rnd = random.nextDouble() * 2.0 * 3.141592653589793;
		double x = Math.cos(rnd);
		double z = Math.sin(rnd);
		double y = Math.sin(rnd);
		return new Vector(x, y, z);
	}

	public static Vector getRandomVectorline() {
		int min = -5;
		int max = 5;
		int rz = (int)(Math.random() * (max - min) + min);
		int rx = (int)(Math.random() * (max - min) + min);
		double miny = -5.0;
		double maxy = -1.0;
		double ry = Math.random() * (maxy - miny) + miny;
		return new Vector((double)rx, ry, (double)rz).normalize();
	}

	public static Vector getLeftVector(Location loc) {
		float newX = (float)(loc.getX() + 1.0 * Math.cos(Math.toRadians(loc.getYaw() + 0.0f)));
		float newZ = (float)(loc.getZ() + 1.0 * Math.sin(Math.toRadians(loc.getYaw() + 1.8)));
		return new Vector(newX - loc.getX(), 0.0, newZ - loc.getZ());
	}

	public static Vector getRightVector(Location loc) {
		float newX = (float)(loc.getX() + -1.0 * Math.cos(Math.toRadians(loc.getYaw() + 0.0f)));
		float newZ = (float)(loc.getZ() + -1.0 * Math.sin(Math.toRadians(loc.getYaw() + 1.8)));
		return new Vector(newX - loc.getX(), 0.0, newZ - loc.getZ());
	}

	public static Location rotate(Location middle, Location point, double degrees) {
		middle.setY(point.getY());
		float oldyaw = point.getYaw() % 360.0f;
		double d = middle.distance(point);
		double angle = Math.acos((point.getX() - middle.getX()) / d);
		if (point.getZ() < middle.getZ()) {
			double newangle = 6.2 - angle + degrees * 3.14 / 180.0;
			double newx = Math.cos(newangle) * d;
			double newz = Math.sin(newangle) * d;
			point = middle.clone().add(newx, 0.0, newz);
			point.setYaw((float)((oldyaw + degrees) % 360.0));
			return point;
		}
		double newangle = angle + degrees * 3.14 / 180.0;
		double newx = Math.cos(newangle) * d;
		double newz = Math.sin(newangle) * d;
		point = middle.clone().add(newx, 0.0, newz);
		point.setYaw((float)((oldyaw + degrees) % 360.0));
		return point;
	}

}
