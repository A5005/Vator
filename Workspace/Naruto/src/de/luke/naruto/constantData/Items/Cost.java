package de.luke.naruto.constantData.Items;

public class Cost {

	private int _uniqueId;
	private int _amount;

	public Cost(int uniqueId, int amount) {
		_uniqueId = uniqueId;
		_amount = amount;
	}

	public int GetUniqueId() {
		return _uniqueId;
	}

	public int GetAmount() {
		return _amount;
	}

}
