package io.phanisment.lib.pcmd.argument;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumArgument<E extends Enum<E>> implements IArgument {
	private final Class<E> enumClass;
	private final boolean lowercase;
	
	public EnumArgument(Class<E> enumClass) {
		this(enumClass, true);
	}
	
	public EnumArgument(Class<E> enumClass, boolean lowercase) {
		this.enumClass = enumClass;
		this.lowercase = lowercase;
	}
	
	@Override
	public List<String> suggest(CommandSender sender, String[] args) {
		if (lowercase) {
			return Arrays.stream(enumClass.getEnumConstants()).map(e -> e.name().toLowerCase()).collect(Collectors.toList());
		} else {
			return Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).collect(Collectors.toList());
		}
	}
	
	public E parse(String value) {
		try {
			if (lowercase) {
				return Enum.valueOf(enumClass, value.toUpperCase());
			} else {
				return Enum.valueOf(enumClass, value);
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}