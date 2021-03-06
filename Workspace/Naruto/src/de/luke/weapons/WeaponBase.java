package de.luke.weapons;

import de.luke.config.ConfigManager;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;


import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public abstract class WeaponBase {

	protected double speed;
	protected double range;
	protected int fillCount;

	public WeaponBase() {

	}

	protected abstract String getFileName();

	public void execute(Boolean loadParamsFromConfig, Player player, Plugin plugin) {
		if (loadParamsFromConfig) {
			YamlConfiguration customConfig = ConfigManager.LoadOrCreateConfig(getFileName());
			LoadParamsFromConfig(customConfig);
		}

		// PrintHelp.PrintDouble("Range", range);

		Location eyeLocation = player.getEyeLocation();
		Location groundLocation = player.getLocation();
		Vector fromGroundToEyeHalf = (eyeLocation.toVector().subtract(groundLocation.toVector())).multiply(0.5);
		Location midLocation = groundLocation.clone().add(fromGroundToEyeHalf);

		Block targetBlock = getTargetBlock(player, (int) range);

		Location targetLocation = targetBlock.getLocation();
		Vector formEyeToTarget = targetLocation.toVector().subtract(eyeLocation.toVector());

		double distanceEyeToTarget = formEyeToTarget.length();
		Vector targetVector = eyeLocation.getDirection().multiply(distanceEyeToTarget);

		Vector projectileVector = fromGroundToEyeHalf.clone().add(targetVector);
		double projectileVectorlength = projectileVector.length();

		// PrintHelp.PrintDouble("projectileVectorlength", projectileVectorlength);
		Vector projectileVectorDir = projectileVector.clone().normalize();

		double particleCount = (range / speed * 20) * fillCount;
		Vector vecOffset = projectileVectorDir.clone().multiply(range / particleCount);
		double offsetLength = vecOffset.length();
		Location curLocation = midLocation.clone();

		CreateParticles(player, plugin, projectileVectorlength, vecOffset, offsetLength, curLocation, particleCount);

	}

	protected abstract void CreateParticles(Player player, Plugin plugin, double projectileVectorlength, Vector vecOffset, double offsetLength, Location curLocation, double particleCount);

	private Block getTargetBlock(Player player, int range) {
		BlockIterator iter = new BlockIterator(player, range);
		Block lastBlock = iter.next();
		while (iter.hasNext()) {
			lastBlock = iter.next();

			// Air is not solid
			if (lastBlock.getType().isSolid() == false) {
				continue;
			}
			break;
		}
		return lastBlock;
	}

	protected Vector Rotate(Vector toRotate, Vector around, double angle) {
		if (angle == 0)
			return toRotate;

		double vx = around.getX(), vy = around.getY(), vz = around.getZ();
		double x = toRotate.getX(), y = toRotate.getY(), z = toRotate.getZ();
		double sinA = Math.sin(angle), cosA = Math.cos(angle);

		double x1 = x * (vx * vx * (1 - cosA) + cosA) + y * (vx * vy * (1 - cosA) - vz * sinA) + z * (vx * vz * (1 - cosA) + vy * sinA);
		double y1 = x * (vy * vx * (1 - cosA) + vz * sinA) + y * (vy * vy * (1 - cosA) + cosA) + z * (vy * vz * (1 - cosA) - vx * sinA);
		double z1 = x * (vz * vx * (1 - cosA) - vy * sinA) + y * (vz * vy * (1 - cosA) + vx * sinA) + z * (vz * vz * (1 - cosA) + cosA);

		Vector result = new Vector(x1, y1, z1);
		return result.normalize();

	}

	protected void LoadParamsFromConfig(YamlConfiguration yamlConfiguration) {

		speed = yamlConfiguration.getDouble("speed");
		range = yamlConfiguration.getDouble("range");
		fillCount = yamlConfiguration.getInt("fillcount");

	}

	protected boolean HasDamages(Player player, Location particleLoc, double x, double y, double z) {

		// TODO ONLY IF BLOCK CHANGED getNearbyEntities

		// Search for any entities near the particle's current location
		for (org.bukkit.entity.Entity entity : particleLoc.getWorld().getNearbyEntities(particleLoc, 3, 3, 3)) {
			// We only care about living entities. Any others will be ignored.
			if (!(entity instanceof LivingEntity))
				continue;

			if (entity == player)
				continue;

			Entity craftEntity = ((CraftEntity) entity).getHandle();

			// see https://bukkit.org/threads/axisalignedbb.9315/
			// Here's a list of what the useful methods do anyway:
			AxisAlignedBB bb = craftEntity.getBoundingBox();

			if (x > bb.a && x < bb.d && y > bb.b && y < bb.e && z > bb.c && z < bb.f) {
				System.out.println("*****HIT*******");

				((LivingEntity) entity).setHealth(0);
				return true;
			}

			/*
			 * System.out.println("xMin=" + xmin + " yMin=" + ymin + " zMin= " + zmin);
			 * System.out.println("xMax=" + xmax + " yMax=" + ymax + " zMax= " + zmax);
			 * 
			 * System.out.println("bb1 x=" + boundingBox.a + " y=" + boundingBox.b + " z= "
			 * + boundingBox.c); System.out.println("bb2 x=" + boundingBox.d + " y=" +
			 * boundingBox.e + " z= " + boundingBox.f);
			 */
		}

		return false;

	}

}