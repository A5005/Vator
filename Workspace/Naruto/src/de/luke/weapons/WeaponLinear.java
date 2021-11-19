package de.luke.weapons;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class WeaponLinear extends WeaponBase {

	public WeaponLinear() {

		speed = 16;
		range = 32;
		fillCount = 1;

	}

	@Override
	protected String getFileName() {
		return "weaponlinear.yml";
	}

	@Override
	protected void CreateParticles(Player player, Plugin plugin, double projectileVectorlength, Vector vecOffset, double offsetLength, Location curLocation, double particleCount) {
		new BukkitRunnable() {

			
			
			double curLength = 0;
			
			EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
			PlayerConnection playerConnection = entityPlayer.playerConnection;

			public void run() {

				for (int i = 0; i < fillCount; i++) {

					// PrintHelp.PrintDouble("length", curLength);

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

	@Override
	protected void LoadParamsFromConfig(YamlConfiguration yamlConfiguration) {
		super.LoadParamsFromConfig(yamlConfiguration);

	}
}
