package de.luke.commands;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.luke.naruto.Perspectives.MainPerspective;

public class MenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;
		try {
			MainPerspective.OpenInventory(player, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

}
