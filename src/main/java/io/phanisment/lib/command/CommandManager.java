package io.phanisment.lib.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
	private CommandNode root = new CommandNode();
	
	public CommandManager(JavaPlugin plugin, String command) {
		plugin.getCommand(command).setExecutor(this);
		plugin.getCommand(command).setTabCompleter(this);
	}
	
	public CommandNode getRoot() {
		return this.root;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			CommandResult result = root.execute(sender, args);
			if (result.getMessage() != null) {
				if (result.isSuccess()) {
					sender.sendMessage(result.getPrefix() + result.getMessage());
				} else {
					sender.sendMessage(result.getPrefix() + result.getErrorColor() + result.getMessage());
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return root.tab(sender, args);
	}
}
