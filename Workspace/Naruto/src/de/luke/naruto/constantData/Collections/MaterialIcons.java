package de.luke.naruto.constantData.Collections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.database.NarutoDataBase;

public class MaterialIcons {

	private static HashMap<Integer, MaterialIcon> _materialIcons;
	private static HashMap<Material, MaterialIcon> _mcMaterialDict;

	private static boolean _isInit = false;
	public static final String TableName = "Material";

	public static void Create() {

		_materialIcons = new HashMap<Integer, MaterialIcon>();
		_mcMaterialDict = new HashMap<Material, MaterialIcon>();

		// CommonMat
		AddIcon(UniqueIds.Stick, UniqueIds.CommonMat, 18, "Stick", "A1", ChatColor.WHITE, 1);
		AddIcon(UniqueIds.WoodenBtn, UniqueIds.CommonMat, 19, "Wooden Button", "A2", ChatColor.WHITE, 2);
		AddIcon(UniqueIds.StoneBtn, UniqueIds.CommonMat, 20, "Stone Button", "A3", ChatColor.WHITE, 3);

		AddIcon(UniqueIds.WoodenPresPlate, UniqueIds.CommonMat, 21, "Wooden Pressure Plate", "A4", ChatColor.WHITE, 4);
		AddIcon(UniqueIds.StonePresPlate, UniqueIds.CommonMat, 22, "Stone Pressure Plate", "A5", ChatColor.WHITE, 5);
		AddIcon(UniqueIds.Feather, UniqueIds.CommonMat, 23, "Feather", "A6", ChatColor.WHITE, 6);

		AddIcon(UniqueIds.Leather, UniqueIds.CommonMat, 24, "Leather", "A7", ChatColor.WHITE, 7);
		AddIcon(UniqueIds.Paper, UniqueIds.CommonMat, 25, "Paper", "A8", ChatColor.WHITE, 8);
		AddIcon(UniqueIds.Book, UniqueIds.CommonMat, 26, "Book", "A9", ChatColor.WHITE, 9);

		// UnCommonMat
		AddIcon(UniqueIds.String, UniqueIds.UnCommonMat, 18, "String", "B1", ChatColor.WHITE, 10);
		AddIcon(UniqueIds.Bone, UniqueIds.UnCommonMat, 19, "Wooden Button", "B2", ChatColor.WHITE, 11);
		AddIcon(UniqueIds.Brick, UniqueIds.UnCommonMat, 20, "Stone Button", "B3", ChatColor.WHITE, 12);

		AddIcon(UniqueIds.NeBrick, UniqueIds.UnCommonMat, 21, "Nether Brick", "B4", ChatColor.WHITE, 13);
		AddIcon(UniqueIds.Clay, UniqueIds.UnCommonMat, 22, "Clay", "B5", ChatColor.WHITE, 14);
		AddIcon(UniqueIds.Coal, UniqueIds.UnCommonMat, 23, "Coal", "B6", ChatColor.WHITE, 15);

		AddIcon(UniqueIds.Flint, UniqueIds.UnCommonMat, 24, "Flint", "B7", ChatColor.WHITE, 16);
		AddIcon(UniqueIds.Creeper, UniqueIds.UnCommonMat, 25, "Creeper", "B8", ChatColor.WHITE, 17);
		AddIcon(UniqueIds.Compass, UniqueIds.UnCommonMat, 26, "Compass", "B9", ChatColor.WHITE, 18);

		// RareMat
		AddIcon(UniqueIds.Spider, UniqueIds.RareMat, 18, "Mob Eye", "C1", ChatColor.WHITE, 19);
		AddIcon(UniqueIds.Glowstone, UniqueIds.RareMat, 19, "Glowstone", "C2", ChatColor.WHITE, 20);
		AddIcon(UniqueIds.Quartz, UniqueIds.RareMat, 20, "Quartz", "C3", ChatColor.WHITE, 21);

		AddIcon(UniqueIds.Cream, UniqueIds.RareMat, 21, "Magma Cream", "C4", ChatColor.WHITE, 22);
		AddIcon(UniqueIds.Iron, UniqueIds.RareMat, 22, "Iron", "C5", ChatColor.WHITE, 23);
		AddIcon(UniqueIds.Gold, UniqueIds.RareMat, 23, "Gold", "C6", ChatColor.WHITE, 24);

		AddIcon(UniqueIds.PrisShard, UniqueIds.RareMat, 24, "Prismarine Shard", "C7", ChatColor.WHITE, 25);
		AddIcon(UniqueIds.PrisCrystal, UniqueIds.RareMat, 25, "Prismarine Crystal", "C8", ChatColor.WHITE, 26);
		AddIcon(UniqueIds.Watch, UniqueIds.RareMat, 26, "Clock", "C9", ChatColor.WHITE, 27);

		// EpicMat
		AddIcon(UniqueIds.Ferm, UniqueIds.EpicMat, 18, "Special Mob Eye", "D1", ChatColor.WHITE, 28);
		AddIcon(UniqueIds.Tear, UniqueIds.EpicMat, 19, "Ghast Tear", "D2", ChatColor.WHITE, 29);
		AddIcon(UniqueIds.Blaze, UniqueIds.EpicMat, 20, "Blaze Powder", "D3", ChatColor.WHITE, 30);

		AddIcon(UniqueIds.Slime, UniqueIds.EpicMat, 21, "Slimeball", "D4", ChatColor.WHITE, 30);
		AddIcon(UniqueIds.Ender, UniqueIds.EpicMat, 22, "Eye of Ender", "D5", ChatColor.WHITE, 31);
		AddIcon(UniqueIds.Diamond, UniqueIds.EpicMat, 23, "Diamond", "D6", ChatColor.WHITE, 32);

		AddIcon(UniqueIds.Emerald, UniqueIds.EpicMat, 24, "Emerald", "D7", ChatColor.WHITE, 33);
		AddIcon(UniqueIds.Bottle, UniqueIds.EpicMat, 25, "Enchanting Bottle", "D8", ChatColor.WHITE, 34);
		AddIcon(UniqueIds.Star, UniqueIds.EpicMat, 26, "Nether Star", "D9", ChatColor.WHITE, 35);

		_isInit = true;
	}

	private static void AddIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName, ChatColor chatColor, int priority) {

		MaterialIcon materialIcon = new MaterialIcon(uniqueId, materialGroupId, position, displayName, dbAccessName, chatColor, priority);
		MaterialInfo materialInfo = MaterialInfos.GetMaterialInfo(uniqueId);
		_materialIcons.put(uniqueId, materialIcon);
		_mcMaterialDict.put(materialInfo.GetMaterial(), materialIcon);

	}

	public static boolean GetIsInit() {
		return _isInit;
	}

	public static int[] FindMaterialGroupIcons(int materialGroupId) {

		List<Integer> materialGroupIcons = new ArrayList<Integer>();

		_materialIcons.entrySet().forEach(entry -> {

			int curMaterialGroupId = entry.getValue().GetMaterialGroupId();
			if (curMaterialGroupId == materialGroupId)
				materialGroupIcons.add(entry.getKey());
		});

		int[] materialGroupIconsArray = materialGroupIcons.stream().mapToInt(i -> i).toArray();

		return materialGroupIconsArray;

	}

	public static MaterialIcon GetMaterialIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}

	public static MaterialIcon TryGetMaterialIconFromMcMaterial(Material mcMaterial) {

		if (!_mcMaterialDict.containsKey(mcMaterial))
			return null;

		return _mcMaterialDict.get(mcMaterial);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId) {

		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		BaseIcons.AddToInventory(baseIcon, inventory, uniqueId, position, typeId, null);
	}

	public static void ListWeaponIcons() {

		for (HashMap.Entry<Integer, MaterialIcon> entry : _materialIcons.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

	public static void DbSetAllAmountsToValue(UUID uuid, int value) throws SQLException {

		String uuidString = uuid.toString();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbValues = new StringBuilder();
		sbValues.append(String.format("VALUES (\"%s\"", uuidString));

		sb.append(String.format("INSERT INTO `%s`(`%s`", TableName, "ID"));

		_materialIcons.entrySet().forEach(entry -> {

			MaterialIcon materialIcon = entry.getValue();
			sb.append(String.format(",`%s`", materialIcon.GetDbAccessName()));
			sbValues.append(String.format(",%s", value));

		});

		sbValues.append(")");
		sb.append(") ");
		sb.append(sbValues.toString());

		PreparedStatement preparedStatement = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		preparedStatement.execute();

	}

	public static HashMap<Material, Integer> DbReadAllMaterialAmounts(UUID uuid) throws SQLException {

		String sqlString = String.format("SELECT * FROM `%s` WHERE ID = '%s'", TableName, uuid.toString());

		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sqlString);
		ResultSet rs = st.executeQuery();

		HashMap<Material, Integer> amounts = new HashMap<Material, Integer>();

		while (rs.next()) {

			_materialIcons.entrySet().forEach(entry -> {

				MaterialIcon materialIcon = entry.getValue();

				try {
					int amount = rs.getInt(materialIcon.GetDbAccessName());
					amounts.put(materialIcon.GetMaterialInfo().GetMaterial(), amount);
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

	public static HashMap<MaterialIcon, Integer> DbReadAllIconAmounts(UUID uuid) throws SQLException {

		String sqlString = String.format("SELECT * FROM `%s` WHERE ID = '%s'", TableName, uuid.toString());

		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sqlString);
		ResultSet rs = st.executeQuery();

		HashMap<MaterialIcon, Integer> amounts = new HashMap<MaterialIcon, Integer>();

		while (rs.next()) {

			_materialIcons.entrySet().forEach(entry -> {

				MaterialIcon materialIcon = entry.getValue();

				try {
					int amount = rs.getInt(materialIcon.GetDbAccessName());
					amounts.put(materialIcon, amount);
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

	public static void DbSetSpecificAmounts(HashMap<MaterialIcon, Integer> amounts, UUID uuid) throws SQLException {

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("UPDATE `%s` SET ", TableName));

		boolean firstEnty = true;

		for (HashMap.Entry<MaterialIcon, Integer> entry : amounts.entrySet()) {

			MaterialIcon materialIcon = entry.getKey();
			int amount = entry.getValue();

			if (firstEnty) {
				sb.append(String.format("`%s`=%d", materialIcon.GetDbAccessName(), amount));
				firstEnty = false;
			} else {
				sb.append(String.format(",`%s`=%d", materialIcon.GetDbAccessName(), amount));
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

	public static HashMap<MaterialIcon, Integer> DbGetSpecificAmounts(ArrayList<MaterialIcon> amounts, UUID uuid) throws SQLException {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");

		boolean firstEnty = true;

		for (int i = 0; i < amounts.size(); i++) {
			MaterialIcon materialIcon = amounts.get(i);
			String col = materialIcon.GetDbAccessName();

			if (firstEnty) {
				sb.append(String.format("`%s`", col));
				firstEnty = false;
			} else {
				sb.append(String.format(",`%s`", col));
			}

		}

		sb.append(String.format(" FROM `%s` WHERE `ID`=\"%s\"", TableName, uuid.toString()));

		HashMap<MaterialIcon, Integer> result = new HashMap<MaterialIcon, Integer>();

		if (firstEnty) {
			// Nothing to do
			return result;
		}

		// System.out.println(sb.toString());

		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			for (int i = 0; i < amounts.size(); i++) {

				MaterialIcon materialIcon = amounts.get(i);
				try {
					int amount = rs.getInt(materialIcon.GetDbAccessName());
					result.put(materialIcon, amount);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return result;
	}

}
