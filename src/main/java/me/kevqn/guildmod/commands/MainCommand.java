package me.kevqn.guildmod.commands;

import java.util.Arrays;
import java.util.List;
import me.kevqn.guildmod.GuildMod;
import me.kevqn.guildmod.gui.SettingsGui;
import me.kevqn.guildmod.utils.ChatColor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class MainCommand extends CommandBase
{
	private GuildMod mod;
	
	public MainCommand(GuildMod modIn) {
		this.mod = modIn;
	}
	
	@Override
	public String getCommandName() {
		return "gconfig";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return ChatColor.RED + "Usage: /" + getCommandName();
	}
	
	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("guildconfig", "guildmod", "gm");
	}
	
	@Override 
	public void processCommand(ICommandSender sender, String[] args) {
		new SettingsGui(this.mod).display();
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index){
		return index == 0;
	}
	
	
}
