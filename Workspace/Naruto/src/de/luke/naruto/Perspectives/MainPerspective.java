package de.luke.naruto.Perspectives;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.luke.naruto.constantData.Collections.MaterialGroupIcons;
import de.luke.naruto.constantData.Collections.WeaponGroupIcons;
import de.luke.naruto.constantData.Ids.PerspectiveIds;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;
import de.luke.naruto.tools.ItemMetadata;

public class MainPerspective {

	public static Inventory OpenInventory(Player player) {

		Inventory inventory = Bukkit.createInventory(null, 36, "§7§lMaterials");
		UpdateAll(inventory);
		player.openInventory(inventory);
		return inventory;
	}

	public static void UpdateAll(Inventory inventory) {

		UpdateMainItems(inventory);
		MaterialGroupIcon firstmMaterialGroupIconId = MaterialGroupIcons.GetMaterialGroupIconFromPosition(0);
		UpdateSubItems(inventory, firstmMaterialGroupIconId.GetUniqueId());
	}

	private static void UpdateMainItems(Inventory inventory) {
		MaterialGroupIcons.PutIconsToInventory(inventory, PerspectiveIds.MainPerspoective);
		WeaponGroupIcons.PutIconsToInventory(inventory, PerspectiveIds.MainPerspoective);
	}

	public static void UpdateSubItems(Inventory inventory, int materialGroupIconId) {
		MaterialGroupIcons.PutSubIconsToInventory(inventory, materialGroupIconId, PerspectiveIds.MainPerspoective);
		System.out.println("UpdateSubItems " + materialGroupIconId);
	}

	public static void ProcessSelectedItem(ItemStack itemStack, Inventory clickedInventory) {

		if (!ItemMetadata.hasMetadata(itemStack, "T"))
			return;

		int typeId = (int) ItemMetadata.getMetadata(itemStack, "T");
		if (typeId == TypeIds.MaterialGroup) {
			if (!ItemMetadata.hasMetadata(itemStack, "U"))
				return;

			int uniqueId = (int) ItemMetadata.getMetadata(itemStack, "U");
			
			//TODO Gibt ja nur SubItems für ein Mat + Löschen der unbenutzen Felder
			UpdateSubItems(clickedInventory, uniqueId);
		}

		System.out.println("+++++++++++++ ProcessSelectedItem");
	}

}
