# Phanisment's Command Libs
Simple lightweight DSL-base Command Library for making Command Tree in Minecraft Plugins.

This is has some lack feature that really need and mybe some bug that im not encounter.
For example you want make this command `/test user <player name> ping`, this structure i dont know how to make it but in future i will know how to make it, for now just like this `test user ping <player name>` is enough.

Example:
```java
new CommandManager((JavaPlugin)this, "mycmd").addNode("test", (sender, args) -> {
	return CommandResult.success("Your name is " + sender.getName());
}).addNode("admin", admin -> {
	admin.permission("mycmd.admin");
	admin.addChild("inspect", admin_inspect -> {
		admin_inspect.setExecutor((sender, args) -> {
			if (args[0].isEmpty()) return CommandResult.failed("Player input shouldn't empty.");
			Player player = OnlinePlayerArgument.parse(args[0]);
			if (player == null) return CommandResult.failed("Unknown Player");
			
			return CommandResult.success("Player health: " + player.getHealth());
		});
		admin_inspect.setArgument(new OnlinePlayerArgument());
	});
});
```