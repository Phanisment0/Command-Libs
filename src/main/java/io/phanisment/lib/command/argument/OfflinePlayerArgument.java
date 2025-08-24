package io.phanisment.lib.command.argument;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OfflinePlayerArgument extends AbstractArgument<OfflinePlayer> {
	@Override
	public List<String> suggest(CommandSender sender, String[] args) {
		return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).filter(name -> name != null && !name.isEmpty()).collect(Collectors.toList());
	}
	
	@Override
	public OfflinePlayer parse(String name) {
		return Bukkit.getOfflinePlayer(name);
	}
}