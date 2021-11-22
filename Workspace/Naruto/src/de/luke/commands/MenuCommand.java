package de.luke.commands;

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
		// player.sendMessage(ChatColor.DARK_RED + "Hello, " + player.getName() +
		// ChatColor.GREEN + "!");
		MainPerspective.OpenInventory(player, null);

		return true;
	}

}
