package de.luke.naruto.Perspectives;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Collections.BaseIcons;
import de.luke.naruto.constantData.Collections.WeaponIcons;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.WeaponIcon;


public class CraftingPerspective {

	@SuppressWarnings("deprecation")
	public static Inventory OpenInventory(Player player, Inventory openInventory, int weaponId) {

		if (openInventory != null)
			player.closeInventory();

		WeaponIcon weaponIcon = WeaponIcons.GetWeaponIconFromId(weaponId);
		Inventory inventory = Bukkit.createInventory(null, 36, weaponIcon.GetDisplayName());

		WeaponIcons.AddToInventory(inventory, weaponId, 4, TypeIds.CraftWeaponIcon);
		BaseIcons.AddToInventory(inventory, UniqueIds.Workbench, 20, TypeIds.WorkBenchIcon);
		BaseIcons.AddToInventory(inventory, UniqueIds.Barrier, 27, TypeIds.BackIcon);
		BaseIcons.AddToInventory(inventory, UniqueIds.GreenWool, 31, TypeIds.ClaimIcon);

		player.openInventory(inventory);
		return inventory;
	}

}
