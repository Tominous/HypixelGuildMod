package me.kevqn.guildmod.utils;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;

public class PlayerFacing
{
    static Minecraft mc;
    
    public static boolean isFacingPlayer() {
        return PlayerFacing.mc.objectMouseOver.entityHit != null && PlayerFacing.mc.objectMouseOver.entityHit instanceof EntityPlayer;
    }
    
    public static String getFacingPlayerName() {
        if (PlayerFacing.mc.objectMouseOver.entityHit != null && PlayerFacing.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer facingPlayer = (EntityPlayer)PlayerFacing.mc.objectMouseOver.entityHit;
            System.out.println(facingPlayer.getDisplayNameString());
            return facingPlayer.getDisplayNameString();
        }
        return null;
    }
    
    static {
        PlayerFacing.mc = Minecraft.getMinecraft();
    }
}

