package de.luke.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.luke.naruto.tools.PrintHelp;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class WeaponHelix extends WeaponBase {

	protected double startradius;
	protected double endradius;
	protected double blockspercycle;
	private final Vector _upVector;

	public WeaponHelix() {

		speed = 20;
		range = 20;
		fillCount = 1;
		startradius = 1;
		endradius = 0;
		blockspercycle = 20;
		_upVector = new Vector(0, 1, 0);

	}

	@Override
	protected String getFileName() {
		return "weaponhelix.yml";
	}

	@Override
	protected void CreateParticles(Player player, Plugin plugin, double projectileVectorlength, Vector vecOffset, double offsetLength, Location curLocation, double particleCount) {

		PrintHelp.PrintDouble("particleCount", particleCount);

		// System.currentTimeMillis();

		new BukkitRunnable() {

			double curLength = 0;
			double stepAngle = (2 * Math.PI / particleCount) * (range / blockspercycle);
			double stepRadius = (startradius - endradius) / particleCount;
			double curAngle = 0;
			double curRadius = startradius;

			PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

			Vector around = vecOffset.clone().normalize();
			Vector toRotate = around.clone().crossProduct(_upVector).normalize();

			public void run() {

				for (int i = 0; i < fillCount; i++) {

					// TODO !material.isSolid()

					// PrintHelp.PrintDouble("length", curLength);

					Vector curVector = (Rotate(toRotate, around, curAngle)).multiply(curRadius);

					// PrintHelp.PrintDouble("angle", angle);
					// PrintHelp.PrintVector("curVector", curVector);
					// PrintHelp.PrintDouble("CurLength", curLength);

					curLocation.add(vecOffset);
					Location helixLocation = curLocation.clone().add(curVector);

					if (curLength > 1) {

						if (helixLocation.getBlock().getType().isSolid()) {
							this.cancel();
							return;
						}
					}

					double x = helixLocation.getX();
					double y = helixLocation.getY();
					double z = helixLocation.getZ();

					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float) x, (float) (y), (float) (z), 0, 0, 0, 0, 1);
					playerConnection.sendPacket(packet);

					// TODO Only Players around
					for (Player online : Bukkit.getOnlinePlayers()) {
						((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
					}

					curLength += offsetLength;

					if (HasDamages(player, helixLocation, x, y, z)) {

						this.cancel();
						return;
					}

					if (curLength > projectileVectorlength) {

						this.cancel();
						return;
					}

					// PrintHelp.PrintDouble("curAngle", curAngle);
					// PrintHelp.PrintDouble("curRadius", curRadius);

					curAngle += stepAngle;

					if (curAngle >= 360)
						curAngle = 0;

					curRadius -= stepRadius;
					if (curRadius < 0)
						curRadius = 0;

				}

			}
		}.runTaskTimer(plugin, 0, 1);
	}

	@Override
	protected void LoadParamsFromConfig(YamlConfiguration yamlConfiguration) {
		super.LoadParamsFromConfig(yamlConfiguration);

		startradius = yamlConfiguration.getDouble("startradius");
		endradius = yamlConfiguration.getDouble("endradius");
		blockspercycle = yamlConfiguration.getDouble("blockspercycle");
	}
}
