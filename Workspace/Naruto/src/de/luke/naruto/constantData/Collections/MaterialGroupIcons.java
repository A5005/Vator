package de.luke.naruto.constantData.Collections;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.tools.ItemMetadata;

public class MaterialGroupIcons {

	private static HashMap<Integer, MaterialGroupIcon> _materialGroupIcons;
	private static HashMap<Integer, MaterialGroupIcon> _positions;
	private static int _minPosition = 10000;
	private static int _maxPosition = 0;

	private static int _minSubPosition = 10000;
	private static int _maxSubPosition = 0;

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
		UpdateSubMinMax(materialIconIds);
		MaterialGroupIcon materialGroupIcon = new MaterialGroupIcon(materialInfoId, position, displayName, dbAccessName, materialIconIds);
		_materialGroupIcons.put(materialInfoId, materialGroupIcon);

		if (position < _minPosition)
			_minPosition = position;

		if (position > _maxPosition)
			_maxPosition = position;

		if (!_positions.containsKey(position))
			_positions.put(position, materialGroupIcon);
		else {
			throw new Exception("Position occurs twice!");
		}

	}

	private static void UpdateSubMinMax(int[] materialIconIds) {

		for (int i = 0; i < materialIconIds.length; i++) {

			MaterialIcon materialIcon = MaterialIcons.GetMaterialIconFromId(materialIconIds[i]);
			int position = materialIcon.GetPosition();

			if (position < _minSubPosition)
				_minSubPosition = position;

			if (position > _maxSubPosition)
				_maxSubPosition = position;

		}
	}

	@SuppressWarnings("deprecation")
	public static void PutIconsToInventory(Inventory inventory, int perspectiveId) {

		for (int position = _minPosition; position <= _maxPosition; position++) {

			MaterialGroupIcon materialGroupIcon = _positions.getOrDefault(position, null);
			if (materialGroupIcon == null)
				continue;

			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(materialGroupIcon.GetUniqueId());

			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(materialGroupIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, "P", perspectiveId);
			itemStack = ItemMetadata.setMetadata(itemStack, "T", TypeIds.MaterialGroup);
			itemStack = ItemMetadata.setMetadata(itemStack, "U", materialGroupIcon.GetUniqueId());

			inventory.setItem(position, itemStack);

		}

	}

	public static void PutSubIconsToInventory(Inventory inventory, int materialGroupId, int perspectiveId) {

		MaterialGroupIcon materialGroupIcon = _materialGroupIcons.get(materialGroupId);

		// TODO MIN MAX BESCHREIBEN _minSubPosition _maxSubPosition

		int[] materialIconIds = materialGroupIcon.GetMaterialIconIds();

		for (int i = 0; i < materialIconIds.length; i++) {

			MaterialIcon materialIcon = MaterialIcons.GetMaterialIconFromId(materialIconIds[i]);
			int uniqueId = materialIcon.GetUniqueId();
			MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(materialIcon.GetUniqueId());

			@SuppressWarnings("deprecation")
			ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(materialGroupIcon.GetDisplayName());
			itemStack.setItemMeta(itemMeta);

			// Important!!! Metadata only in the return value
			itemStack = ItemMetadata.setMetadata(itemStack, "P", perspectiveId);
			itemStack = ItemMetadata.setMetadata(itemStack, "T", TypeIds.Material);
			itemStack = ItemMetadata.setMetadata(itemStack, "U", uniqueId);

			inventory.setItem(materialIcon.GetPosition(), itemStack);
		}

	}

	public static MaterialGroupIcon GetMaterialGroupIconFromPosition(int position) {
		return _positions.get(position);

	}

}
