package de.luke.naruto.constantData.Items;

public class MaterialIcon {

	private int _uniqueId;
	private int _position;
	private int _materialId;
	private String _displayName;
	private String _dbAccessName;


	public MaterialIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName) {

		_uniqueId = uniqueId;
		_materialId = materialGroupId;
		_displayName = displayName;
		_position = position;
		_dbAccessName = dbAccessName;
	}

	public int GetUniqueId() {
		return _uniqueId;
	}
	
	public int GetMaterialGroupId() {
		return _materialId;
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

}
