package de.luke.naruto.constantData.Items;

public class MaterialGroupIcon {

	private int _uniqueId;
	private int _position;
	private String _displayName;
	private String _dbAccessName;
	private int[] _materialIconIds;

	public MaterialGroupIcon(int uniqueId, int position, String displayName, String dbAccessName, int[] materialIconIds) {

		_uniqueId = uniqueId;
		_materialIconIds = materialIconIds;
		_displayName = displayName;
		_position = position;
		_dbAccessName = dbAccessName;
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
	
	public String GetDbAccessName() {
		return _dbAccessName;
	}

	public int[] GetMaterialIconIds() {
		return _materialIconIds;
	}
}
