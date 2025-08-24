package io.phanisment.lib.command.argument;

import org.bukkit.command.CommandSender;
import java.util.List;

public class BooleanArgument extends AbstractArgument<Boolean> {
	@Override
	public List<String> suggest(CommandSender sender, String[] args) {
		return List.of("true", "false");
	}
	
	@Override
	public Boolean parse(String value) {
		if (value.equalsIgnoreCase("true")) return true;
		return false;
	}
}