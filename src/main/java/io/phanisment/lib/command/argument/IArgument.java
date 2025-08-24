package io.phanisment.lib.command.argument;

import org.bukkit.command.CommandSender;
import java.util.List;

@FunctionalInterface
public interface IArgument {
	List<String> suggest(CommandSender sender, String[] args);
}