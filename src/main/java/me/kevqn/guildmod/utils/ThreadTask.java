package me.kevqn.guildmod.utils;

import me.kevqn.guildmod.listeners.*;
import java.util.*;

public class ThreadTask extends TimerTask
{
    @Override
    public void run() {
        ChatListener.setShouldStart(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ChatListener.setShouldStart(false);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ChatListener.setShouldStart(true);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                ChatListener.setShouldStart(false);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        ChatListener.setShouldStart(true);
                                        new Timer().schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                ChatListener.setShouldStart(false);
                                            }
                                        }, 1500L);
                                    }
                                }, 1500L);
                            }
                        }, 1500L);
                    }
                }, 1500L);
            }
        }, 1500L);
    }
}
