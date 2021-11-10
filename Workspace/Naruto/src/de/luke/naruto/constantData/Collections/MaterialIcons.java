package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialIcon;

public class MaterialIcons {

	private static HashMap<Integer, MaterialIcon> _materialIcons;

	public static void Create() {

		_materialIcons = new HashMap<Integer, MaterialIcon>();

		// TODO One table for each material group
		AddIcon(UniqueIds.Stick, UniqueIds.CommonMat, 0, "§f§lStick", "sticknumber");
		AddIcon(UniqueIds.WoodenBtn, UniqueIds.CommonMat, 1, "§f§lWooden Button", "woodbuttonnumber");
		AddIcon(UniqueIds.StoneBtn, UniqueIds.CommonMat, 2, "§f§lStone Button", "stonebuttonnumber");

		AddIcon(UniqueIds.WoodenPresPlate, UniqueIds.CommonMat, 3, "§f§lWooden Pressure Plate", "woodpreasureplatenumber");
		AddIcon(UniqueIds.StonePresPlate, UniqueIds.CommonMat, 4, "§f§lStone Pressure Plate", "stonepreasureplatenumber");
		AddIcon(UniqueIds.Feather, UniqueIds.CommonMat, 5, "§f§lFeather", "feathernumber");

		AddIcon(UniqueIds.Leather, UniqueIds.CommonMat, 6, "§f§lLeather", "leathernumber");
		AddIcon(UniqueIds.Paper, UniqueIds.CommonMat, 7, "§f§lPaper", "papernumber");
		AddIcon(UniqueIds.Book, UniqueIds.CommonMat, 8, "§f§lBook", "booknumber");

	}

	private static void AddIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName) {

		MaterialIcon materialIcon = new MaterialIcon(uniqueId, materialGroupId, position, displayName, dbAccessName);
		_materialIcons.put(uniqueId, materialIcon);
	}

	public static boolean GetIsInit() {
		return false;
	}

	public static int[] FindMaterialGroupIcons(int materialGroupId) {

		List<Integer> materialGroupIcons = new ArrayList<Integer>();

		_materialIcons.entrySet().forEach(entry -> {

			int curMaterialGroupId = entry.getValue().GetMaterialGroupId();
			if (curMaterialGroupId == materialGroupId)
				materialGroupIcons.add(entry.getKey());
		});

		int[] materialGroupIconsArray = materialGroupIcons.stream().mapToInt(i -> i).toArray();

		return materialGroupIconsArray;

	}

}
