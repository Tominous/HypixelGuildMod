package me.kevqn.guildmod.commands;

import me.kevqn.guildmod.GuildMod;
import me.kevqn.guildmod.utils.CmdHandler;
import net.minecraft.command.*;

public class cg extends CommandBase
{
    String cmd;
    
    public cg() {
        this.cmd = "cg";
    }
    
    public String getCommandName() {
        return this.cmd;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return null;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
    	if(GuildMod.isPlayCommandsEnabled()) CmdHandler.cmd(this.cmd, args);
    }
}