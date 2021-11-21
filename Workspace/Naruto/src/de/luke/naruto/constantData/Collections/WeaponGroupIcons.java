package de.luke.naruto.constantData.Collections;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.constantData.Items.WeaponGroupIcon;
import de.luke.naruto.constantData.Items.WeaponIcon;
import de.luke.naruto.tools.ItemMetadata;

public class WeaponGroupIcons {

	private static HashMap<Integer, WeaponGroupIcon> _weaponGroupIcons;
	private static HashMap<Integer, WeaponGroupIcon> _positions;
	private static int _minPosition = 4;
	private static int _maxPosition = 8;

	private static int _minSubPosition = 18;
	private static int _maxSubPosition = 26;

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

		if (!_positions.containsKey(position))
			_positions.put(position, weaponGroupIcon);
		else {
			throw new Exception("Position occurs twice!");
		}

	}

	@SuppressWarnings("deprecation")
	public static void PutIconsToInventory(Inventory inventory) {

		for (int position = _minPosition; position <= _maxPosition; position++) {

			WeaponGroupIcon weaponGroupIcon = _positions.getOrDefault(position, null);
			if (weaponGroupIcon == null)
				continue;

			int uniqueId = weaponGroupIcon.GetUniqueId();

			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);

			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(weaponGroupIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, TypeIds.WeaponGroup);
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);

			inventory.setItem(position, itemStack);

		}
	}

	@SuppressWarnings("deprecation")
	public static void PutSubIconsToInventory(Inventory inventory, int weaponGroupId) {

		WeaponGroupIcon weaponGroupIcon = _weaponGroupIcons.get(weaponGroupId);

		HashSet<Integer> positions = new HashSet<Integer>();

		int[] weaponIconIds = weaponGroupIcon.GetWeaponIconIds();

		for (int i = 0; i < weaponIconIds.length; i++) {

			WeaponIcon weaponIcon = WeaponIcons.GetWeaponIconFromId(weaponIconIds[i]);
			int uniqueId = weaponIcon.GetUniqueId();
			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(weaponIcon.GetUniqueId());

			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(weaponIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, TypeIds.Weapon);
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);

			int position = weaponIcon.GetPosition();
			positions.add(position);

			inventory.setItem(weaponIcon.GetPosition(), itemStack);
		}

		// Erase unused slots
		for (int i = _minSubPosition; i <= _maxSubPosition; i++) {

			if (!positions.contains(i)) {
				ItemStack itemstack = inventory.getItem(i);
				inventory.remove(itemstack);
			}

		}

	}

	public static WeaponGroupIcon GetWeaponGroupIconFromPosition(int position) {
		return _positions.get(position);

	}
	
	


}
