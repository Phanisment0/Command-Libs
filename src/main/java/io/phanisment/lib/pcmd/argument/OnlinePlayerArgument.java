package io.phanisment.lib.pcmd.argument;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class OnlinePlayerArgument extends AbstractArgument<Player> {
	@Override
	public List<String> suggest(CommandSender sender, String[] args) {
		return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).toList();
	}
	
	@Override
	public Player parse(String name) {
		return Bukkit.getPlayerExact(name);
	}
}