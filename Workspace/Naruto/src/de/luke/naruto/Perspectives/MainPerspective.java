package de.luke.naruto.Perspectives;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.luke.naruto.constantData.Collections.MaterialGroupIcons;
import de.luke.naruto.constantData.Collections.WeaponGroupIcons;
import de.luke.naruto.constantData.Ids.PerspectiveIds;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;
import de.luke.naruto.tools.ItemMetadata;
import de.luke.naruto.tools.PrintHelp;

public class MainPerspective {

	public static Inventory OpenInventory(Player player, Inventory openInventory) {

		if (openInventory != null)
			player.closeInventory();

		Inventory inventory = Bukkit.createInventory(null, 36, "§7§l> Materials");
		UpdateAll(inventory);
		player.openInventory(inventory);
		return inventory;
	}

	public static void UpdateAll(Inventory inventory) {

		UpdateMainItems(inventory);
		MaterialGroupIcon firstmMaterialGroupIconId = MaterialGroupIcons.GetMaterialGroupIconFromPosition(0);
		UpdateMaterialSubItems(inventory, firstmMaterialGroupIconId.GetUniqueId());
	}

	private static void UpdateMainItems(Inventory inventory) {
		MaterialGroupIcons.PutIconsToInventory(inventory);
		WeaponGroupIcons.PutIconsToInventory(inventory);
	}

	public static void UpdateMaterialSubItems(Inventory inventory, int materialGroupIconId) {
		MaterialGroupIcons.PutSubIconsToInventory(inventory, materialGroupIconId);
	}

	public static void UpdateWeaponSubItems(Inventory inventory, int weaponGroupIconId) {
		WeaponGroupIcons.PutSubIconsToInventory(inventory, weaponGroupIconId);
	}

}
