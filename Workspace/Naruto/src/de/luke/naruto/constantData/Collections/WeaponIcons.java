package de.luke.naruto.constantData.Collections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.Cost;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.constantData.Items.WeaponIcon;
import de.luke.naruto.database.NarutoDataBase;

public class WeaponIcons extends BaseIcons {

	private static HashMap<Integer, WeaponIcon> _weaponIcons;
	private static HashMap<Material, WeaponIcon> _mcMaterialDict;

	private static boolean _isInit = false;
	public static final String TableName = "Weapon";

	@SuppressWarnings("serial")
	public static void Create() {

		_weaponIcons = new HashMap<Integer, WeaponIcon>();
		_mcMaterialDict = new HashMap<Material, WeaponIcon>();

		ArrayList<Cost> costs;

		// CommonWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 2), new Cost(UniqueIds.Feather, 5)));
		AddIcon(UniqueIds.Arrow, UniqueIds.CommonWeap, 18, "Throwing Knife", "A1", costs, ChatColor.WHITE);

		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 2), new Cost(UniqueIds.Leather, 5)));
		AddIcon(UniqueIds.WoodAxe, UniqueIds.CommonWeap, 19, "Throwing Axe", "A2", costs, ChatColor.WHITE);

		// UnCommonWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 20), new Cost(UniqueIds.Clay, 5), new Cost(UniqueIds.StonePresPlate, 3)));
		AddIcon(UniqueIds.StoneAxe, UniqueIds.UnCommonWeap, 18, "Throwing Axe", "B1", costs, ChatColor.WHITE);

		// RareWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 100), new Cost(UniqueIds.Clay, 20), new Cost(UniqueIds.Spider, 2), new Cost(UniqueIds.Iron, 3)));
		AddIcon(UniqueIds.IronAxe, UniqueIds.RareWeap, 18, "Throwing Axe", "C1", costs, ChatColor.WHITE);

		// LegendWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 500), new Cost(UniqueIds.Clay, 100), new Cost(UniqueIds.Spider, 15), new Cost(UniqueIds.Slime, 1), new Cost(UniqueIds.Diamond, 3)));
		AddIcon(UniqueIds.DiamondAxe, UniqueIds.LegendWeap, 18, "Throwing Axe", "D1", costs, ChatColor.WHITE);

		_isInit = true;
	}

	public static boolean GetIsInit() {
		return _isInit;
	}

	private static void AddIcon(int uniqueId, int weaponGroupId, int position, String displayName, String dbAccessName, ArrayList<Cost> costs, ChatColor chatColor) {

		WeaponIcon weaponIcon = new WeaponIcon(uniqueId, weaponGroupId, position, displayName, dbAccessName, costs, chatColor);
		MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);
		_weaponIcons.put(uniqueId, weaponIcon);
		_mcMaterialDict.put(materialInfo.GetMaterial(), weaponIcon);
	}

	public static int[] FindMaterialGroupIcons(int weaponGroupId) {

		List<Integer> _weaponGroupIcons = new ArrayList<Integer>();

		_weaponIcons.entrySet().forEach(entry -> {

			int curWeaponGroupId = entry.getValue().GetWeaponGroupId();
			if (curWeaponGroupId == weaponGroupId)
				_weaponGroupIcons.add(entry.getKey());
		});

		int[] weaponGroupIconsArray = _weaponGroupIcons.stream().mapToInt(i -> i).toArray();

		return weaponGroupIconsArray;

	}

	public static WeaponIcon TryGetMaterialIconFromMcMaterial(Material mcMaterial) {

		if (!_mcMaterialDict.containsKey(mcMaterial))
			return null;

		return _mcMaterialDict.get(mcMaterial);
	}

	public static WeaponIcon GetWeaponIconFromId(int uniqueId) {
		return _weaponIcons.get(uniqueId);
	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _weaponIcons.get(uniqueId);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId, List<String> lore) {

		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		BaseIcons.AddToInventory(baseIcon, inventory, uniqueId, position, typeId, lore);
	}

	public static void ListWeaponIcons() {

		for (HashMap.Entry<Integer, WeaponIcon> entry : _weaponIcons.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

	public static void DbSetAllAmountsToValue(UUID uuid, int value) throws SQLException {

		String uuidString = uuid.toString();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbValues = new StringBuilder();
		sbValues.append(String.format("VALUES (\"%s\"", uuidString));

		sb.append(String.format("INSERT INTO `%s`(`%s`", TableName, "ID"));

		_weaponIcons.entrySet().forEach(entry -> {
			WeaponIcon weaponIcon = entry.getValue();
			sb.append(String.format(",`%s`", weaponIcon.GetDbAccessName()));
			sbValues.append(String.format(",%s", value));
		});

		sbValues.append(")");
		sb.append(") ");
		sb.append(sbValues.toString());

		//System.out.println(sb.toString());
		PreparedStatement preparedStatement = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		preparedStatement.execute();

	}

	public static void DbSetSpecificAmounts(HashMap<WeaponIcon, Integer> amounts, UUID uuid) throws SQLException {

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("UPDATE `%s` SET ", TableName));

		boolean firstEnty = true;

		for (HashMap.Entry<WeaponIcon, Integer> entry : amounts.entrySet()) {

			WeaponIcon weaponIcon = entry.getKey();
			int amount = entry.getValue();

			if (firstEnty) {
				sb.append(String.format("`%s`=%d", weaponIcon.GetDbAccessName(), amount));
				firstEnty = false;
			} else {
				sb.append(String.format(",`%s`=%d", weaponIcon.GetDbAccessName(), amount));
			}

		}

		if (firstEnty) {
			// Nothing to do
			return;
		}

		sb.append(String.format(" WHERE `ID`=\"%s\"", uuid.toString()));
		// System.out.println(sb.toString());

		PreparedStatement preparedStatement = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		preparedStatement.execute();

	}

	public static void DbSetSpecificAmount(WeaponIcon weaponIcon, int amount, UUID uuid) throws SQLException {

		String col = weaponIcon.GetDbAccessName();
		String select = String.format("UPDATE `%s` SET `%s`=%d WHERE `ID`=\"%s\"", TableName, col, amount, uuid.toString());
		//System.out.println(select);
		PreparedStatement preparedStatement = NarutoDataBase.mysql.getConnection().prepareStatement(select);
		preparedStatement.execute();
	}

	public static int DbGetSpecificAmount(WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		String col = weaponIcon.GetDbAccessName();
		String select = String.format("SELECT `%s` FROM `%s` WHERE `ID`=\"%s\"", col, TableName, uuid.toString());
		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(select);
		ResultSet rs = st.executeQuery();

		int result = 0;

		while (rs.next()) {

			try {
				result = rs.getInt(col);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	public static HashMap<WeaponIcon, Integer> DbGetSpecificAmounts(ArrayList<WeaponIcon> amounts, UUID uuid) throws SQLException {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");

		boolean firstEnty = true;

		for (int i = 0; i < amounts.size(); i++) {
			WeaponIcon weaponIcon = amounts.get(i);
			String col = weaponIcon.GetDbAccessName();

			if (firstEnty) {
				sb.append(String.format("`%s`", col));
				firstEnty = false;
			} else {
				sb.append(String.format(",`%s`", col));
			}

		}

		sb.append(String.format(" FROM `%s` WHERE `ID`=\"%s\"", TableName, uuid.toString()));

		HashMap<WeaponIcon, Integer> result = new HashMap<WeaponIcon, Integer>();

		if (firstEnty) {
			// Nothing to do
			return result;
		}

		// System.out.println(sb.toString());

		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			for (int i = 0; i < amounts.size(); i++) {

				WeaponIcon weaponIcon = amounts.get(i);
				try {
					int amount = rs.getInt(weaponIcon.GetDbAccessName());
					result.put(weaponIcon, amount);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return result;
	}
	
	
	public static HashMap<WeaponIcon, Integer> DbReadAllIconAmounts(UUID uuid) throws SQLException {

		String sqlString = String.format("SELECT * FROM `%s` WHERE ID = '%s'", TableName, uuid.toString());

		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sqlString);
		ResultSet rs = st.executeQuery();

		HashMap<WeaponIcon, Integer> amounts = new HashMap<WeaponIcon, Integer>();

		while (rs.next()) {

			_weaponIcons.entrySet().forEach(entry -> {

				WeaponIcon weaponIcon = entry.getValue();

				try {
					int amount = rs.getInt(weaponIcon.GetDbAccessName());
					amounts.put(weaponIcon, amount);
					// System.out.println("DB EXISTING: " + materialIcon.GetDisplayName() + " " +
					// amount);
				} catch (SQLException e) {
					//
					e.printStackTrace();
				}

			});
		}

		return amounts;
	}

}
