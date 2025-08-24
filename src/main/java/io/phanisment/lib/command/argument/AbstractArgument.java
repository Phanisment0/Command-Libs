package io.phanisment.lib.command.argument;

public abstract class AbstractArgument<T> implements IArgument {
	public abstract T parse(String value);
}