package de.luke.naruto;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.luke.naruto.Perspectives.CraftingPerspective;
import de.luke.naruto.Perspectives.MainPerspective;
import de.luke.naruto.constantData.Ids.PerspectiveIds;
import de.luke.naruto.tools.ItemMetadata;

public class InventoryListener implements Listener {

	@EventHandler
	public void handleNavigatorGUIClickTop(InventoryClickEvent event) {

		event.setCancelled(true);

		ItemStack selectedItemStack = TryGetValidItemStack(event);
		if (selectedItemStack == null)
			return;

		if (!ItemMetadata.hasMetadata(selectedItemStack, "P"))
			return;

		int perspectiveId = (int) ItemMetadata.getMetadata(selectedItemStack, "P");

		switch (perspectiveId) {

		case PerspectiveIds.MainPerspoective:
			Inventory clickedInventory = event.getClickedInventory();
			MainPerspective.ProcessSelectedItem(selectedItemStack, clickedInventory);
			break;

		case PerspectiveIds.CraftingtPerspective:
			CraftingPerspective.ProcessSelectedItem(selectedItemStack);
			break;

		default:
			return;

		}

	}

	private ItemStack TryGetValidItemStack(InventoryClickEvent event) {
		HumanEntity humanEntity = event.getWhoClicked();

		if (!(humanEntity instanceof Player))
			return null;

		Inventory clickedInventory = event.getClickedInventory();

		if (clickedInventory == null)
			return null;

		ItemStack currentItemStack = event.getCurrentItem();
		if (currentItemStack == null)
			return null;

		if (!currentItemStack.hasItemMeta())
			return null;

		return currentItemStack;

	}

	@EventHandler
	public void onLeftClick(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		if (player == null)
			return;

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

			Location loc = player.getLocation();

			Vector direction = loc.getDirection().normalize();

			DecimalFormat format = new DecimalFormat("0.00");

			// System.out.println("Player Location x/y/z=" + format.format(loc.getX()) + "/"
			// + format.format(loc.getY()) + "/" + format.format(loc.getZ()));

			double pitchDegree = loc.getPitch();
			double yawDegree = loc.getYaw();

			//https://bukkit.org/threads/tutorial-how-to-calculate-vectors.138849/
			
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

}
