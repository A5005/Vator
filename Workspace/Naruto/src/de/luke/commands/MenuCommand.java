package de.luke.commands;

import de.luke.naruto.Perspectives.MainPerspective;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


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
