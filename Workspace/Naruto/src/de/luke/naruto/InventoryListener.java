package de.luke.naruto;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



public class InventoryListener implements Listener {

	@EventHandler
	public static void handleNavigatorGUIClickTop(InventoryClickEvent event) {

		System.out.println("Before Metadata 1");
		HumanEntity humanEntity = event.getWhoClicked();

		System.out.println("Before Metadata 2");
		if (!(humanEntity instanceof Player))
			return;

		System.out.println("Before Metadata 3");
		Player player = (Player) humanEntity;

		System.out.println("Before Metadata 4");
		Inventory clickedInventory = event.getClickedInventory();

		System.out.println("Before Metadata 5");
		if (clickedInventory == null)
			return;

		System.out.println("Before Metadata 6");
		ItemStack currentIemStack = event.getCurrentItem();

		System.out.println("Before Metadata 7");
		if (currentIemStack == null)
			return;
		
		String kenner="SomeInt";

		System.out.println("Before Metadata 8");
		if (!currentIemStack.hasItemMeta())
			return;
		else {
			currentIemStack = ItemMetadata.setMetadata(currentIemStack, kenner, 4711);
			System.out.println("Write Meta");
		}

		System.out.println("Before Metadata 90");

		if (ItemMetadata.hasMetadata(currentIemStack, kenner)) {
			int myMetaData = (int) ItemMetadata.getMetadata(currentIemStack, kenner);
			System.out.println(myMetaData);
		} else {
			System.out.println("No Metadata");
		}

		System.out.println("Before Metadata 10");

	}

}
