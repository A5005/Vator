package de.luke.listener;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.luke.naruto.Perspectives.CraftingPerspective;
import de.luke.naruto.Perspectives.MainPerspective;
import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.tools.ItemMetadata;
import de.luke.naruto.tools.PrintHelp;

public class InventoryListener implements Listener {

	@EventHandler
	public void handleNavigatorGUIClickTop(InventoryClickEvent event) throws SQLException {

		ItemStack selectedItemStack = TryGetValidItemStack(event);
		if (selectedItemStack == null)
			return;

		event.setCancelled(true);

		if (!ItemMetadata.hasMetadata(selectedItemStack, MetaDataIds.TypeMetaData))
			return;

		if (!ItemMetadata.hasMetadata(selectedItemStack, MetaDataIds.UniqueIdMetaData))
			return;

		int typeMetaData = (int) ItemMetadata.getMetadata(selectedItemStack, MetaDataIds.TypeMetaData);

		int uniqueIdMetaData = (int) ItemMetadata.getMetadata(selectedItemStack, MetaDataIds.UniqueIdMetaData);

		// PrintHelp.Print("Type " + typeMetaData);
		// PrintHelp.Print("Unique " + uniqueIdMetaData);

		Inventory clickedInventory = event.getClickedInventory();

		Player player = (Player) event.getWhoClicked();

		UUID uuid = player.getUniqueId();

		switch (typeMetaData) {
		case TypeIds.MaterialGroup:
			MainPerspective.UpdateMaterialSubItems(clickedInventory, uuid, uniqueIdMetaData);
			break;

		case TypeIds.WeaponGroup:
			MainPerspective.UpdateWeaponSubItems(clickedInventory, uniqueIdMetaData);
			break;

		case TypeIds.Weapon:
			CraftingPerspective.OpenInventory(player, clickedInventory, uniqueIdMetaData);
			break;

		case TypeIds.BackIcon:
			MainPerspective.OpenInventory(player, clickedInventory);
			break;

		case TypeIds.WorkBenchIcon:
			PrintHelp.Print("WorkBenchIcon " + uniqueIdMetaData);
			CraftingPerspective.Craft(player, clickedInventory);
			break;

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
