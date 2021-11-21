package de.luke.naruto.constantData.Items;

public class BaseIcon {

	private int _uniqueId;
	private int _position;
	private String _displayName;
	private String _dbAccessName;

	public BaseIcon(int uniqueId, int position, String displayName, String dbAccessName) {

		_uniqueId = uniqueId;
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

}
