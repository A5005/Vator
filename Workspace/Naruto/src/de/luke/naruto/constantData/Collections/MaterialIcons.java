package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialIcon;

public class MaterialIcons {

	private static HashMap<Integer, MaterialIcon> _materialIcons;
	private static boolean _isInit = false;

	public static void Create() {

		_materialIcons = new HashMap<Integer, MaterialIcon>();

		// TODO One table for each material group
		AddIcon(UniqueIds.Stick, UniqueIds.CommonMat, 18, "§f§lStick", "sticknumber");
		AddIcon(UniqueIds.WoodenBtn, UniqueIds.CommonMat, 19, "§f§lWooden Button", "woodbuttonnumber");
		AddIcon(UniqueIds.StoneBtn, UniqueIds.CommonMat, 20, "§f§lStone Button", "stonebuttonnumber");

		AddIcon(UniqueIds.WoodenPresPlate, UniqueIds.CommonMat, 21, "§f§lWooden Pressure Plate", "woodpreasureplatenumber");
		AddIcon(UniqueIds.StonePresPlate, UniqueIds.CommonMat, 22, "§f§lStone Pressure Plate", "stonepreasureplatenumber");
		AddIcon(UniqueIds.Feather, UniqueIds.CommonMat, 23, "§f§lFeather", "feathernumber");

		AddIcon(UniqueIds.Leather, UniqueIds.CommonMat, 24, "§f§lLeather", "leathernumber");
		AddIcon(UniqueIds.Paper, UniqueIds.CommonMat, 25, "§f§lPaper", "papernumber");
		AddIcon(UniqueIds.Book, UniqueIds.CommonMat, 26, "§f§lBook", "booknumber");

		_isInit = true;
	}

	private static void AddIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName) {

		MaterialIcon materialIcon = new MaterialIcon(uniqueId, materialGroupId, position, displayName, dbAccessName);
		_materialIcons.put(uniqueId, materialIcon);
	}

	public static boolean GetIsInit() {
		return _isInit;
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

	public static MaterialIcon GetMaterialIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}
}
