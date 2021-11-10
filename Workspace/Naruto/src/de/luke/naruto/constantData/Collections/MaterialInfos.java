package de.luke.naruto.constantData.Collections;

import java.util.HashMap;
import org.bukkit.Material;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialInfo;

public class MaterialInfos {

	

	private static HashMap<Integer, MaterialInfo> _materialInfos;

	public static void Create() {

		_materialInfos = new HashMap< Integer, MaterialInfo>();

		AddMaterialItem(UniqueIds.CommonMat, Material.INK_SACK, 8);
		AddMaterialItem(UniqueIds.UnCommonMat, Material.INK_SACK, 10);
		AddMaterialItem(UniqueIds.RareMat, Material.INK_SACK, 12);
		AddMaterialItem(UniqueIds.EpicMat, Material.INK_SACK, 5);
		AddMaterialItem(UniqueIds.CommonWeap, Material.INK_SACK, 15);
		AddMaterialItem(UniqueIds.UnCommonWeap, Material.INK_SACK, 2);
		AddMaterialItem(UniqueIds.RareWeap, Material.INK_SACK, 5);
		AddMaterialItem(UniqueIds.LegendWeap, Material.INK_SACK, 11);
		AddMaterialItem(UniqueIds.ExclusiveWeap, Material.INK_SACK, 1);

		AddMaterialItem(UniqueIds.Stick, Material.STICK, 0);
		AddMaterialItem(UniqueIds.WoodenBtn, Material.WOOD_BUTTON, 0);
		AddMaterialItem(UniqueIds.StoneBtn, Material.STONE_BUTTON, 0);
		AddMaterialItem(UniqueIds.WoodenPresPlate, Material.WOOD_PLATE, 0);
		AddMaterialItem(UniqueIds.StonePresPlate, Material.STONE_PLATE, 0);
		AddMaterialItem(UniqueIds.Feather, Material.FEATHER, 0);
		AddMaterialItem(UniqueIds.Leather, Material.LEATHER, 0);
		AddMaterialItem(UniqueIds.Paper, Material.PAPER, 0);
		AddMaterialItem(UniqueIds.Book, Material.BOOK, 0);

		// Sub CommonWeap
		AddMaterialItem(UniqueIds.Arrow, Material.ARROW, 0);

		// Fill
		AddMaterialItem(UniqueIds.Glass, Material.STAINED_GLASS_PANE, 15);

	}

	private static void AddMaterialItem(int uniqueId, Material material, int byteValue) {

		MaterialInfo materialItem = new MaterialInfo(uniqueId, material, byteValue);
		_materialInfos.put(uniqueId, materialItem);

	}

	public static MaterialInfo GetMaterialItem(int uniqueId) {

		return _materialInfos.get(uniqueId);
	}

}
