package de.luke.naruto.constantData.Items;

import org.bukkit.ChatColor;

import de.luke.naruto.constantData.Collections.MaterialInfos;

public class MaterialIcon extends BaseIcon {

	private int _materialId;

	public MaterialIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName, ChatColor chatColor) {
		super(uniqueId, position, displayName, dbAccessName, chatColor);
		_materialId = materialGroupId;
	}

	public int GetMaterialGroupId() {
		return _materialId;
	}
	
	
	

}
