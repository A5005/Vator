package de.luke.naruto.constantData.Collections;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.constantData.Items.WeaponGroupIcon;
import de.luke.naruto.tools.ItemMetadata;

public class WeaponGroupIcons {

	private static HashMap<Integer, WeaponGroupIcon> _weaponGroupIcons;
	private static HashMap<Integer, WeaponGroupIcon> _positions;
	private static int _minPosition = 10000;
	private static int _maxPosition = 0;

	public static void Create() throws Exception {

		if (!WeaponIcons.GetIsInit()) {
			throw new Exception("Call WeaponIcons.Create() first!");
		}

		_weaponGroupIcons = new HashMap<Integer, WeaponGroupIcon>();
		_positions = new HashMap<Integer, WeaponGroupIcon>();
		AddIcon(UniqueIds.CommonWeap, 4, "§f§lCommon Weapons", "Materialnumber");
		AddIcon(UniqueIds.UnCommonWeap, 5, "§2§lUncommon Weapons", "Materialnumber");
		AddIcon(UniqueIds.RareWeap, 6, "§1§lRare Weapons", "Materialnumber");
		AddIcon(UniqueIds.LegendWeap, 7, "§e§lLegendary Weapon", "Materialnumber");
		AddIcon(UniqueIds.ExclusiveWeap, 8, "§4§lExclusive Weapons", "Materialnumber");

	}

	private static void AddIcon(int materialInfoId, int position, String displayName, String dbAccessName) throws Exception {

		// Backward Pointer
		int[] weaponIconIds = WeaponIcons.FindMaterialGroupIcons(materialInfoId);
		WeaponGroupIcon weaponGroupIcon = new WeaponGroupIcon(materialInfoId, position, displayName, dbAccessName, weaponIconIds);
		_weaponGroupIcons.put(materialInfoId, weaponGroupIcon);

		extracted(position, weaponGroupIcon);
	}

	private static void extracted(int position, WeaponGroupIcon weaponGroupIcon) throws Exception {
		if (position < _minPosition)
			_minPosition = position;

		if (position > _maxPosition)
			_maxPosition = position;

		if (!_positions.containsKey(position))
			_positions.put(position, weaponGroupIcon);
		else {
			throw new Exception("Position occurs twice!");
		}
	}

	@SuppressWarnings("deprecation")
	public static void PutIconsToInventory(Inventory inventory, int perspectiveId) {

		for (int position = _minPosition; position <= _maxPosition; position++) {

			WeaponGroupIcon weaponGroupIcon = _positions.getOrDefault(position, null);
			if (weaponGroupIcon == null)
				continue;

			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(weaponGroupIcon.GetUniqueId());

			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(weaponGroupIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, "P", perspectiveId);

			inventory.setItem(position, itemStack);

		}
	}

}
