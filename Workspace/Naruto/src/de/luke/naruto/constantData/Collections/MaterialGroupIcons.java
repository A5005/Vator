package de.luke.naruto.constantData.Collections;

import java.util.HashMap;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialGroupIcon;

public class MaterialGroupIcons {

	private static HashMap<Integer, MaterialGroupIcon> _materialGroupIcons;

	public static void Create() throws Exception {

		if (!MaterialIcons.GetIsInit()) {
			throw new Exception("Call MaterialIcons.Create() first!");
		}

		_materialGroupIcons = new HashMap<Integer, MaterialGroupIcon>();
		AddIcon(UniqueIds.CommonMat, 0, "§7§lCommon Materials", "Materialnumber");
		AddIcon(UniqueIds.UnCommonMat, 1, "§a§lUncommon Materials", "Materialnumber");
		AddIcon(UniqueIds.RareMat, 2, "§3§lRare Materials", "Materialnumber");
		AddIcon(UniqueIds.EpicMat, 3, "§5§lEpic Materials", "Materialnumber");

	}

	private static void AddIcon(int materialInfoId, int position, String displayName, String dbAccessName) {

		// Backward Pointer
		int[] materialIconIds = MaterialIcons.FindMaterialGroupIcons(materialInfoId);
		_materialGroupIcons.put(materialInfoId, new MaterialGroupIcon(materialInfoId, position, displayName, dbAccessName, materialIconIds));
	}

}
