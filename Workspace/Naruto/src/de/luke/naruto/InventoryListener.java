package de.luke.naruto;


import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


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

	
}
