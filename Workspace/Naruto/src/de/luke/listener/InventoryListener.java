package de.luke.listener;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.luke.naruto.Perspectives.CraftingPerspective;
import de.luke.naruto.Perspectives.MainPerspective;
import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.tools.ItemMetadata;

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

		InventoryView inventoryView = event.getView();

		Inventory topInventory = inventoryView.getTopInventory();
		Inventory bottomInventory = inventoryView.getBottomInventory();

		Inventory clickedInventory = event.getClickedInventory();
		if (!clickedInventory.equals(topInventory))
			return;

		Player player = (Player) event.getWhoClicked();

		UUID uuid = player.getUniqueId();

		switch (typeMetaData) {

		case TypeIds.Weapon:
			CraftingPerspective.OpenInventory(player, clickedInventory, uniqueIdMetaData);
			break;

		case TypeIds.BackIcon:
			MainPerspective.OpenInventory(player, clickedInventory);
			break;

		case TypeIds.MaterialGroup:
			MainPerspective.UpdateMaterialSubItems(topInventory, uuid, uniqueIdMetaData);
			break;

		case TypeIds.WeaponGroup:
			MainPerspective.UpdateWeaponSubItems(topInventory, uniqueIdMetaData);
			break;

		case TypeIds.WorkBenchIcon:
			CraftingPerspective.Craft(player, topInventory);
			break;

		// TODO Update Claimicon when dropping
		case TypeIds.ClaimIcon:
			CraftingPerspective.Claim(player, topInventory, bottomInventory);
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
