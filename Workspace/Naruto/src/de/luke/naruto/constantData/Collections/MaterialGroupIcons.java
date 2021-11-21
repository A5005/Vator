package de.luke.naruto.constantData.Collections;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.tools.ItemMetadata;
import de.luke.naruto.tools.PrintHelp;

public class MaterialGroupIcons {

	private static HashMap<Integer, MaterialGroupIcon> _materialGroupIcons;
	private static HashMap<Integer, MaterialGroupIcon> _positions;
	private static final int _minPosition = 0;
	private static final int _maxPosition = 3;

	private static int _minSubPosition = 18;
	private static int _maxSubPosition = 26;

	public static void Create() throws Exception {

		if (!MaterialIcons.GetIsInit()) {
			throw new Exception("Call MaterialIcons.Create() first!");
		}

		_materialGroupIcons = new HashMap<Integer, MaterialGroupIcon>();
		_positions = new HashMap<Integer, MaterialGroupIcon>();

		AddIcon(UniqueIds.CommonMat, 0, "§7§lCommon Materials", "Materialnumber");
		AddIcon(UniqueIds.UnCommonMat, 1, "§a§lUncommon Materials", "Materialnumber");
		AddIcon(UniqueIds.RareMat, 2, "§3§lRare Materials", "Materialnumber");
		AddIcon(UniqueIds.EpicMat, 3, "§5§lEpic Materials", "Materialnumber");

	}

	private static void AddIcon(int materialInfoId, int position, String displayName, String dbAccessName) throws Exception {

		// Backward Pointer
		int[] materialIconIds = MaterialIcons.FindMaterialGroupIcons(materialInfoId);
		MaterialGroupIcon materialGroupIcon = new MaterialGroupIcon(materialInfoId, position, displayName, dbAccessName, materialIconIds);
		_materialGroupIcons.put(materialInfoId, materialGroupIcon);

		if (!_positions.containsKey(position))
			_positions.put(position, materialGroupIcon);
		else {
			throw new Exception("Position occurs twice!");
		}

	}

	@SuppressWarnings("deprecation")
	public static void PutIconsToInventory(Inventory inventory) {

		for (int position = _minPosition; position <= _maxPosition; position++) {

			MaterialGroupIcon materialGroupIcon = _positions.getOrDefault(position, null);
			if (materialGroupIcon == null)
				continue;

			int uniqueId = materialGroupIcon.GetUniqueId();

			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);

			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(materialGroupIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, TypeIds.MaterialGroup);
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);

			inventory.setItem(position, itemStack);

		}

	}

	@SuppressWarnings("deprecation")
	public static void PutSubIconsToInventory(Inventory inventory, int materialGroupId) {

		MaterialGroupIcon materialGroupIcon = _materialGroupIcons.get(materialGroupId);

		HashSet<Integer> positions = new HashSet<Integer>();

		int[] materialIconIds = materialGroupIcon.GetMaterialIconIds();

		for (int i = 0; i < materialIconIds.length; i++) {

			MaterialIcon materialIcon = MaterialIcons.GetMaterialIconFromId(materialIconIds[i]);
			int uniqueId = materialIcon.GetUniqueId();
			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(materialIcon.GetUniqueId());

			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(materialIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, TypeIds.Material);
			itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);

			int position = materialIcon.GetPosition();
			positions.add(position);

			inventory.setItem(materialIcon.GetPosition(), itemStack);
		}

		// Erase unused slots
		for (int i = _minSubPosition; i <= _maxSubPosition; i++) {

			if (!positions.contains(i)) {
				ItemStack itemstack = inventory.getItem(i);
				inventory.remove(itemstack);
			}

		}

	}

	public static MaterialGroupIcon GetMaterialGroupIconFromPosition(int position) {
		return _positions.get(position);

	}

}
