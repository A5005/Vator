package de.luke.naruto.constantData.Items;

import org.bukkit.ChatColor;

public class MaterialIcon extends BaseIcon {

	private int _materialId;

	public MaterialIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName, ChatColor chatColor, int priority) {
		super(uniqueId, position, displayName, dbAccessName, chatColor, priority);
		_materialId = materialGroupId;
	}

	public int GetMaterialGroupId() {
		return _materialId;
	}

}
