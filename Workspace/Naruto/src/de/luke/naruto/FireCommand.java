package de.luke.naruto;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class FireCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		Location loc = player.getLocation();
		Vector direction = loc.getDirection().normalize();

		DecimalFormat format = new DecimalFormat("0.00");
		
		
		System.out.println("Player Location x/y/z=" + format.format(loc.getX()) + "/" +  format.format(loc.getY()) + "/" + format.format(loc.getZ()));
		System.out.println("Player Direction x/y/z=" + format.format(direction.getX()) + "/" +  format.format(direction.getY()) + "/" + format.format(direction.getZ()));
		
		
		
		int radius = 1;
		for (double y = 0.5; y <= 25; y += 0.05) {
			double dx = direction.getX() * y;
			double dz = direction.getZ() * y;
			double x = radius * Math.cos(y * 2);
			double z = radius * Math.sin(y * 2);

			loc.add(dx, 1.5, dz);
			//System.out.println(loc);

			float xx = (float) (loc.getX() + x);
			float yy = (float) (loc.getY() + z * -1);
			float zz = (float) (loc.getZ());

			
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, xx, yy, zz, 0, 0, 0, 0, 1);
			loc.subtract(dx, 1.5, dz);

			for (Player online : Bukkit.getOnlinePlayers()) {

				((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
			}
		}

		return true;
	}

}