package de.luke.weapons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import de.luke.config.ConfigManager;
import de.luke.naruto.tools.PrintHelp;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public abstract class WeaponBase {

	protected double speed;
	protected double range;
	protected int fillCount;

	public WeaponBase() {

		speed = 16;
		range = 32;
		fillCount = 1;
	}

	protected String getFileName() {
		return "weaponbase.yml";
	}

	public void execute(Boolean loadParamsFromConfig, Player player, Plugin plugin) {
		if (loadParamsFromConfig) {
			YamlConfiguration customConfig = ConfigManager.LoadOrCreateConfig(getFileName());
			LoadParamsFromConfig(customConfig);
		}

		PrintHelp.PrintDouble("Range", range);

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
		PrintHelp.PrintDouble("projectileVectorlength", projectileVectorlength);
		Vector projectileVectorDir = projectileVector.clone().normalize();

		double particleCount = range / speed * 20;
		Vector vecOffset = projectileVectorDir.clone().multiply(range / (particleCount * fillCount));
		double offsetLength = vecOffset.length();
		Location curLocation = midLocation.clone();

		CreateParticles(player, plugin, projectileVectorlength, vecOffset, offsetLength, curLocation);

	}

	private void CreateParticles(Player player, Plugin plugin, double projectileVectorlength, Vector vecOffset, double offsetLength, Location curLocation) {
		new BukkitRunnable() {

			double curLength = 0;
			PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

			public void run() {

				for (int i = 0; i < fillCount; i++) {

					PrintHelp.PrintDouble("length", curLength);

					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float) (curLocation.getX()), (float) (curLocation.getY()), (float) (curLocation.getZ()), 0, 0, 0, 0, 1);
					playerConnection.sendPacket(packet);

					curLength += offsetLength;

					if (curLength > projectileVectorlength) {

						this.cancel();
						return;
					}

					curLocation.add(vecOffset);
				}

			}
		}.runTaskTimer(plugin, 0, 1);
	}

	private Block getTargetBlock(Player player, int range) {
		BlockIterator iter = new BlockIterator(player, range);
		Block lastBlock = iter.next();
		while (iter.hasNext()) {
			lastBlock = iter.next();
			if (lastBlock.getType() == Material.AIR) {
				continue;
			}
			break;
		}
		return lastBlock;
	}

	protected void LoadParamsFromConfig(YamlConfiguration yamlConfiguration) {

		speed = yamlConfiguration.getDouble("speed");
		range = yamlConfiguration.getDouble("range");
		fillCount = yamlConfiguration.getInt("fillcount");

	}

}