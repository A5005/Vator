package de.luke.naruto.constantData.Items;

import org.bukkit.ChatColor;

public class MaterialGroupIcon extends BaseIcon{


	private int[] _materialIconIds;

	public MaterialGroupIcon(int uniqueId, int position, String displayName, String dbAccessName, int[] materialIconIds,  ChatColor chatColor) {

		super(uniqueId, position, displayName, dbAccessName, chatColor);
		_materialIconIds = materialIconIds;

	}



	public int[] GetMaterialIconIds() {
		return _materialIconIds;
	}
}
