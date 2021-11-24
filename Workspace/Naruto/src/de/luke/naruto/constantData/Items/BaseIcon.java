package de.luke.naruto.constantData.Items;

import org.bukkit.ChatColor;

import de.luke.naruto.constantData.Collections.MaterialInfos;

public class BaseIcon {

	private int _uniqueId;
	private int _position;
	private String _displayName;
	private String _dbAccessName;
	private ChatColor _chatColor;
	private int _priority;

	public BaseIcon(int uniqueId, int position, String displayName, String dbAccessName, ChatColor chatColor, int priority) {

		_uniqueId = uniqueId;
		_displayName = displayName;
		_position = position;
		_dbAccessName = dbAccessName;
		_chatColor = chatColor;
		_priority = priority;
	}

	public int GetUniqueId() {
		return _uniqueId;
	}

	public int GetPosition() {
		return _position;
	}

	public String GetDisplayName() {
		return _displayName;
	}

	public String GetColoredDisplayName() {
		return _chatColor + _displayName;
	}

	public String GetDbAccessName() {
		return _dbAccessName;
	}

	public ChatColor GetChatColor() {
		return _chatColor;
	}
	
	public int GetPriority() {
		return _priority;
	}
	
	public MaterialInfo GetMaterialInfo()
	{
		return MaterialInfos.GetMaterialInfo(_uniqueId);
	}

}
