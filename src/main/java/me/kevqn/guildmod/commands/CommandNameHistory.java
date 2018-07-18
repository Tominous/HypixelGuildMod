package me.kevqn.guildmod.commands;

import me.kevqn.guildmod.utils.PlayerNames;
import net.minecraft.command.*;

public class CommandNameHistory extends CommandBase {
	public String getCommandName() {
		return "names";
	}
	
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	public String getCommandUsage(final ICommandSender sender) {
		return "/names <username>";
	}
	
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if(args.length == 0 || args.length > 1) {
			this.getCommandUsage(sender);
		} else {
			final String name = args[0];
			PlayerNames.startFetchingHistory(name, false);
		}
	}
}
