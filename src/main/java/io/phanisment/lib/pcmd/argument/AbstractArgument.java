package io.phanisment.lib.pcmd.argument;

public abstract class AbstractArgument<T> implements IArgument {
	public abstract T parse(String value);
}