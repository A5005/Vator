package de.luke.listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.luke.naruto.constantData.Collections.MaterialIcons;
import de.luke.naruto.database.NarutoDataBase;

public class JoinListener implements Listener {

	@EventHandler
	public void onPlayerFirstJoin(PlayerJoinEvent event) throws SQLException {

		Player player = event.getPlayer();

		if (!isInDatabase(MaterialIcons.TableName, player.getUniqueId())) {
			InitMatarial(player);
		}

	}

	public static boolean isInDatabase(String tableName, UUID uuid) throws SQLException {

		String sqlString = String.format("Select `ID` FROM `%s` WHERE `ID` = '%s'", tableName, uuid.toString());
		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sqlString);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			return true;
		}
		return false;

	}

	private void InitMatarial(Player player) throws SQLException {

		MaterialIcons.DbSetAllAmountsToValue(player.getUniqueId(), 0);
	}

}
