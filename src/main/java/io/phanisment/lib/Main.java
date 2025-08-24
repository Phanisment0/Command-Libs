package io.phanisment.lib;

import org.bukkit.plugin.java.JavaPlugin;
import io.phanisment.lib.command.CommandManager;
import io.phanisment.lib.command.CommandResult;
import io.phanisment.lib.command.argument.OnlinePlayerArgument;
import io.phanisment.lib.command.argument.EnumArgument;

public class Main extends JavaPlugin {
	private static Main inst;
	
	public Main() {
		inst = this;
	}
	
	@Override
	public void onEnable() {
		new CommandManager(this, "mycmd").getRoot().addNode("admin", admin -> {
			admin.setArgument(new EnumArgument<>(TypeEnum.class));
			admin.addChild("i", (sender, args) -> {
				return CommandResult.success("Inspect: " + args[0]);
			});
		});
	}
	
	public static Main inst() {
		return inst;
	}
}