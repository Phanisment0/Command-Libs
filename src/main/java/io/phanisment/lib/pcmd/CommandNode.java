package io.phanisment.lib.pcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import io.phanisment.lib.pcmd.argument.IArgument;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class CommandNode {
	private final Map<String, CommandNode> children = new HashMap<>();
	
	private String name;
	private String permission;
	private CommandNode parent;
	private NodeExecutor executor;
	private IArgument argument;
	private boolean console = true;
	private boolean op;
	
	protected CommandNode() {
		this("root");
	}
	
	public CommandNode(String name) {
		this(name, null);
	}
	
	public CommandNode(String name, NodeExecutor executor) {
		this(name, executor, null);
	}
	
	public CommandNode(String name, NodeExecutor executor, IArgument argument) {
		this.name = name.toLowerCase();
		this.executor = executor;
		this.argument = argument;
	}
	
	public String getName() {
		return this.name;
	}
	
	private void setParent(CommandNode parent) {
		this.parent = parent;
	}
	
	public CommandNode permission(String permission) {
		this.permission = permission;
		return this;
	}
	
	public CommandNode console(boolean allow) {
		this.console = allow;
		return this;
	}
	
	public CommandNode op(boolean op) {
		this.op = op;
		return this;
	}
	
	public CommandNode addChild(String name, Consumer<CommandNode> data) {
		var child = new CommandNode(name);
		child.setParent(this);
		this.children.put(child.getName(), child);
		data.accept(child);
		return child;
	}
	
	public CommandNode addChild(String name) {
		return this.addChild(new CommandNode(name));
	}
	
	public CommandNode addChild(String name, NodeExecutor executor) {
		return this.addChild(new CommandNode(name, executor));
	}
	
	public CommandNode addChild(String name, NodeExecutor executor, IArgument argument) {
		return this.addChild(new CommandNode(name, executor, argument));
	}
	
	public CommandNode addChild(CommandNode child) {
		child.setParent(this);
		this.children.put(child.getName(), child);
		return child;
	}
	
	public CommandNode addNode(String name, Consumer<CommandNode> data) {
		var node = new CommandNode(name);
		node.setParent(this);
		this.children.put(node.getName(), node);
		data.accept(node);
		return this;
	}
	
	public CommandNode addNode(String name) {
		return this.addNode(new CommandNode(name));
	}
	
	public CommandNode addNode(String name, NodeExecutor executor) {
		return this.addNode(new CommandNode(name, executor));
	}
	
	public CommandNode addNode(String name, NodeExecutor executor, IArgument argument) {
		return this.addNode(new CommandNode(name, executor, argument));
	}
	
	public CommandNode addNode(CommandNode node) {
		node.setParent(this);
		this.children.put(node.getName(), node);
		return this;
	}
	
	public CommandNode getParent() {
		return this.parent;
	}
	
	public CommandNode setExecutor(NodeExecutor executor) {
		this.executor = executor;
		return this;
	}
	
	public CommandNode setArgument(IArgument argument) {
		this.argument = argument;
		return this;
	}
	
	private CommandResult canAccess(CommandSender sender) {
		if (this.permission != null && !sender.hasPermission(this.permission)) return CommandResult.failed("You don't have a permission to access this command");
		if (!this.console && sender instanceof ConsoleCommandSender) return CommandResult.failed("You can't run this command on Console");
		if (this.op && !sender.isOp()) return CommandResult.failed("Only Operator can run this command");
		return CommandResult.success();
	}
	
	protected CommandResult execute(CommandSender sender, String[] args) {
		CommandResult iscan = this.canAccess(sender);
		if (!iscan.isSuccess()) return iscan;
		
		if (args.length == 0 && this.executor != null) return executor.run(sender, args);
		
		CommandNode child = this.children.get(args[0].toLowerCase());
		if (child != null) return child.execute(sender, this.reduceArray(args));
		
		if (this.executor != null) return this.executor.run(sender, args);
		
		return CommandResult.failed("Unknown subcommand: " + args[0].toLowerCase());
	}
	
	protected List<String> tab(CommandSender sender, String[] args) {
		if (args.length == 1 && !this.children.isEmpty()) {
			List<String> suggestions = new ArrayList<>();
			for (var node_data : this.children.entrySet()) {
				CommandNode child = node_data.getValue();
				CommandResult iscan = child.canAccess(sender);
				if (iscan.isSuccess()) suggestions.add(child.getName());
			}
			return suggestions;
		}
		
		CommandNode child = this.children.get(args[0].toLowerCase());
		if (child != null) return child.tab(sender, this.reduceArray(args));
		
		if (this.argument != null && args.length == 1) return this.argument.suggest(sender, this.reduceArray(args));
		
		return new ArrayList<>();
	}
	
	private String[] reduceArray(String[] args) {
		return Arrays.copyOfRange(args, 1, args.length);
	}
	
	@FunctionalInterface 
	public interface NodeExecutor {
		CommandResult run(CommandSender sender, String[] args);
	}
}