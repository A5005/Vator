package de.luke.naruto;

import java.text.DecimalFormat;

import de.luke.config.ConfigManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

//https://helpch.at/docs/1.8.8/

public class AttackListener implements Listener {

	private Plugin _plugin;
	private DecimalFormat format = new DecimalFormat("0.00");

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

	}

	private void PrintLocation(String name, Location loc) {

		System.out.println(name + "x/y/z " + format.format(loc.getX()) + "/" + format.format(loc.getY()) + "/" + format.format(loc.getZ()));

	}

	private void PrintVector(String name, Vector vec) {

		System.out.println(name + "x/y/z " + format.format(vec.getX()) + "/" + format.format(vec.getY()) + "/" + format.format(vec.getZ()));

	}

	public void particleBeam(Player player) {

		YamlConfiguration customConfig = ConfigManager.LoadOrCreateConfig();

		double speed = customConfig.getDouble("speed");
		double range = customConfig.getDouble("range");
		int fillCount = customConfig.getInt("fillcount");

		// double speed = 32; // Speed = z.B. 25 Blocks per Second
		// double range = 32; // Range = z.B. 50 Block Length
		// PacketPlayOutWorldParticles Max 32 when
		// Parameter 2 = false
		// Shot needs 2 Seconds, when Speed=25 Blocks/Second and range=50
		// 1 Second = 20 Ticks => 2 Seconds => Particle needs 40 Ticks

		double duration = range / speed;
		System.out.println("Shot Duration =" + duration + " Seconds");

		// Player's eye location is the starting location for the particle
		Location eyeLocation = player.getEyeLocation();
		Location groundLocation = player.getLocation();
		Vector fromGroundToEyeHalf = (eyeLocation.toVector().subtract(groundLocation.toVector())).multiply(0.5);
		Location midLocation = groundLocation.clone().add(fromGroundToEyeHalf);

		PrintLocation("eyeLocation", eyeLocation);
		PrintLocation("groundLocation", groundLocation);
		PrintVector("fromGroundToEyeHalf", fromGroundToEyeHalf);
		PrintLocation("midLocation", midLocation);

		// startLoc.subtract(new Vector(0, 0.5, 0));
		// World world = startLoc.getWorld(); // We need this later to show the particle

		// We need to clone() this location, because we will add() to it later.
		// Location particleLoc = startLoc.clone();

		// dir is the Vector direction (offset from 0,0,0) the player is facing in 3D
		// space
		Vector eyeDir = eyeLocation.getDirection();
		PrintVector("eyeDir", eyeDir);

		Block targetBlock = getTargetBlock(player, (int) range);

		Location targetLocation = targetBlock.getLocation();
		Vector formEyeToTarget = targetLocation.toVector().subtract(eyeLocation.toVector());

		double distanceEyeToTarget = formEyeToTarget.length();
		System.out.println("distanceEyeToTarget =" + distanceEyeToTarget + "Blocks");
		Vector targetVector = eyeLocation.getDirection().multiply(distanceEyeToTarget);
		PrintVector("targetVector", targetVector);

		Vector projectileVector = fromGroundToEyeHalf.clone().add(targetVector);
		double projectileVectorlength = projectileVector.length();
		Vector projectileVectorDir = projectileVector.clone().normalize();

		/*
		 * vecOffset is used to determine where the next particle should appear We are
		 * taking the direction and multiplying it by 0.5 to make it appear 1/2 block in
		 * its continuing Vector direction. NOTE: We have to clone() because multiply()
		 * modifies the original variable! For a straight beam, we only need to
		 * calculate this once, as the direction does not change.
		 */

		// This can also be done without the extra "dir" variable:
		// Vector vecOffset = startLoc.getDirection().clone().multiply(0.5);

		double particleCount = range / speed * 20;
		System.out.println("ParticleCount =" + duration + " Seconds");

		Vector vecOffset = projectileVectorDir.clone().multiply(range / (particleCount * fillCount));
		double offsetLength = vecOffset.length();

		Location curLocation = midLocation.clone();

		// curLocation.add(vecOffset.multiply(0.5));

		new BukkitRunnable() {
			// The run() function runs every X number of ticks - see below

			double curLength = 0;

			public void run() {

				// PrintLocation("Cur Loc", curLocation);

				// PARAMETER
				// https://minecraft-server.eu/forum/threads/spigot-1-13-partikel.51243/

				// Now we add the direction vector offset to the particle's current location

				// Idee: nur man selber sieht Fillcounts (die Pakete werden nicht an andere
				// Spieler weitergeleitet)
				for (int i = 0; i < fillCount; i++) {
					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float) (curLocation.getX()), (float) (curLocation.getY()), (float) (curLocation.getZ()), 0, 0, 0, 0, 1);
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

					// Display the particle in the new location

					// world.spawnParticle(Particle.FIREWORKS_SPARK, particleLoc, 0);
					// System.out.println("NOIW");

					curLength += offsetLength;

					if (curLength > projectileVectorlength) {

						this.cancel();
						return;
					}

					curLocation.add(vecOffset);
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
