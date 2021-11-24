package de.luke.naruto.Perspectives;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Collections.MaterialGroupIcons;
import de.luke.naruto.constantData.Collections.WeaponGroupIcons;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;

public class MainPerspective {

	public static Inventory OpenInventory(Player player, Inventory openInventory) throws SQLException {

		if (openInventory != null)
			player.closeInventory();

		Inventory inventory = Bukkit.createInventory(null, 36, "§7§lMaterials");
		UUID uuid = player.getUniqueId();
		UpdateAll(inventory, uuid);
		player.openInventory(inventory);
		return inventory;
	}

	public static void UpdateAll(Inventory topInventory, UUID uuid) throws SQLException {

		UpdateMainItems(topInventory);
		MaterialGroupIcon firstmMaterialGroupIconId = MaterialGroupIcons.GetMaterialGroupIconFromPosition(0);
		UpdateMaterialSubItems(topInventory, uuid, firstmMaterialGroupIconId.GetUniqueId());
	}

	private static void UpdateMainItems(Inventory topInventory) {
		MaterialGroupIcons.PutIconsToInventory(topInventory);
		WeaponGroupIcons.PutIconsToInventory(topInventory);
	}

	public static void UpdateMaterialSubItems(Inventory topInventory, UUID uuid, int materialGroupIconId) throws SQLException {
		MaterialGroupIcons.PutSubIconsToInventory(topInventory, uuid, materialGroupIconId);
	}

	public static void UpdateWeaponSubItems(Inventory topInventory, int weaponGroupIconId) {
		WeaponGroupIcons.PutSubIconsToInventory(topInventory, weaponGroupIconId);
	}

}
