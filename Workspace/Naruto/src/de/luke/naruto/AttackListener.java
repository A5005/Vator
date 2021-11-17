package de.luke.naruto;

import java.io.IOException;
import java.text.DecimalFormat;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import de.luke.config.ConfigManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

//https://helpch.at/docs/1.8.8/

public class AttackListener implements Listener {

	private Plugin _plugin;

	public AttackListener(Plugin plugin) {
		_plugin = plugin;
	}

	@EventHandler
	public void onLeftClick(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		if (player == null)
			return;

		Action action = event.getAction();

		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {

			Location playerLocation = player.getLocation();
			Vector lookAtDirection = playerLocation.getDirection();

			DecimalFormat format = new DecimalFormat("0.00");
			Vector lookAtDirectionNorm = lookAtDirection.normalize();
			System.out.println("Player Direction x/y/z=" + format.format(lookAtDirectionNorm.getX()) + "/" + format.format(lookAtDirectionNorm.getY()) + "/" + format.format(lookAtDirectionNorm.getZ()));

			double length = lookAtDirection.length();
			System.out.println("Length: " + length);
			// -> direction already normalized

			final int lookAtRange = 50;
			Block targetBlock = getTargetBlock(player, lookAtRange);
			System.out.println("Target Block: " + targetBlock.getType());

			particleBeam(player);

		}

		ConfigManager.LoadOrCreateConfig();
		


	}

	public void particleBeam(Player player) {
		// Player's eye location is the starting location for the particle
		Location startLoc = player.getEyeLocation();
		startLoc.subtract(new Vector(0, 0.5, 0));

		// We need to clone() this location, because we will add() to it later.
		Location particleLoc = startLoc.clone();

		World world = startLoc.getWorld(); // We need this later to show the particle

		// dir is the Vector direction (offset from 0,0,0) the player is facing in 3D
		// space
		Vector dir = startLoc.getDirection();

		dir.add(new Vector(0, 0.5, 0));

		/*
		 * vecOffset is used to determine where the next particle should appear We are
		 * taking the direction and multiplying it by 0.5 to make it appear 1/2 block in
		 * its continuing Vector direction. NOTE: We have to clone() because multiply()
		 * modifies the original variable! For a straight beam, we only need to
		 * calculate this once, as the direction does not change.
		 */
		Vector vecOffset = dir.clone().multiply(0.5);

		// This can also be done without the extra "dir" variable:
		// Vector vecOffset = startLoc.getDirection().clone().multiply(0.5);

		new BukkitRunnable() {
			// The run() function runs every X number of ticks - see below

			double d = 0;

			public void run() {

				d++;

				// PARAMETER
				// https://minecraft-server.eu/forum/threads/spigot-1-13-partikel.51243/

				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float) (particleLoc.getX()), (float) (particleLoc.getY()), (float) (particleLoc.getZ()), 0, 0, 0, 0, 1);
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

				// Display the particle in the new location

				// world.spawnParticle(Particle.FIREWORKS_SPARK, particleLoc, 0);
				// System.out.println("NOIW");

				// Now we add the direction vector offset to the particle's current location
				particleLoc.add(vecOffset);

				if (d > 20) {

					this.cancel();
				}

			}
		}.runTaskTimer(_plugin, 0, 1);
		// 0 is the delay in ticks before starting this task
		// 1 is the how often to repeat the run() function, in ticks (20 ticks are in
		// one second)
	}

	public void fire(Player player) {

		Location loc = player.getLocation();
		// Projectile projectile = player.launchProjectile(Egg.class,
		// loc.getDirection().normalize());
		// Vector v = projectile.getLocation().getDirection().normalize();

		new BukkitRunnable() {

			double d = 0;

			Location loc = player.getEyeLocation();
			Vector originalDir = loc.getDirection().normalize();

			Vector stepDir = originalDir.multiply(1);

			float x = (float) loc.getX();
			float y = (float) loc.getY();
			float z = (float) loc.getZ();

			public void run() {

				d++;
				z = z - 1;
				x = x + 1;
				y = y + 1;

				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y, z, 0, 0, 0, 0, 1);
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

				this.cancel();

				/*
				 * if (d > 60) {
				 * 
				 * this.cancel(); }
				 */
			}

		}.runTaskTimer(_plugin, 0, 1);

	}

	public Block getTargetBlock(Player player, int range) {
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

	// Only for Debug!
	public void Spy(Player player) {

		Location loc = player.getLocation();

		Vector direction = loc.getDirection().normalize();

		DecimalFormat format = new DecimalFormat("0.00");

		// System.out.println("Player Location x/y/z=" + format.format(loc.getX()) + "/"
		// + format.format(loc.getY()) + "/" + format.format(loc.getZ()));

		double pitchDegree = loc.getPitch();
		double yawDegree = loc.getYaw();

		// https://bukkit.org/threads/tutorial-how-to-calculate-vectors.138849/

		System.out.println("Player Direction x/y/z/pitch/yaw=" + format.format(direction.getX()) + "/" + format.format(direction.getY()) + "/" + format.format(direction.getZ()) + "/" + format.format(pitchDegree) + "/" + format.format(yawDegree));

		double pitchRad = ((pitchDegree + 90) * Math.PI) / 180;
		double yawRad = ((yawDegree + 90) * Math.PI) / 180;

		System.out.println("Radiants pitch/yaw =" + format.format(pitchRad) + "/" + format.format(yawRad));

		double x = Math.sin(pitchRad) * Math.cos(yawRad);
		double y = Math.sin(pitchRad) * Math.sin(yawRad);
		double z = Math.cos(pitchRad);

		System.out.println("Calc DirVec =" + format.format(x) + "/" + format.format(y) + "/" + format.format(z));

		/*
		 * Location newLocation = new Location(Bukkit.getWorld("world"), loc.getX(),
		 * loc.getY(), loc.getZ(), 0, 0); player.teleport(newLocation);
		 * 
		 * Vector newdirection = newLocation.getDirection().normalize();
		 * System.out.println("Player New Direction x/y/z/pitch/yaw=" +
		 * format.format(newdirection.getX()) + "/" + format.format(newdirection.getY())
		 * + "/" + format.format(newdirection.getZ()) + "/" +
		 * format.format(newLocation.getPitch()) + "/" +
		 * format.format(newLocation.getYaw()));
		 */

	}

}
