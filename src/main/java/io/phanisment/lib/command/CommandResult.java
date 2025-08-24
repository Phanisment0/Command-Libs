package io.phanisment.lib.command;

public class CommandResult {
	private String prefix = "";
	private String error_color = "§c";
	private String message;
	private boolean result;
	
	public CommandResult(String message, boolean result) {
		this.message = message;
		this.result = result;
	}
	
	public boolean isSuccess() {
		return this.result;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public CommandResult prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public CommandResult message(String message) {
		this.message = message;
		return this;
	}
	
	public CommandResult result(boolean result) {
		this.result = result;
		return this;
	}
	
	public CommandResult errorColor(String color) {
		this.error_color = error_color;
		return this;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getErrorColor() {
		return this.error_color;
	}
	
	public static CommandResult success() {
		return new CommandResult(null, true);
	}
	
	public static CommandResult success(String message) {
		return new CommandResult(message, true);
	}
	
	public static CommandResult failed() {
		return new CommandResult("Failed to run the command!", false);
	}
	
	public static CommandResult failed(String message) {
		return new CommandResult(message, false);
	}
}