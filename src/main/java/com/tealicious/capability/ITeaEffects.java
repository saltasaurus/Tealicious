package com.tealicious.capability;

import net.minecraft.world.entity.player.Player;

import java.util.Map;

// Define the com.tealicious.capability interface
public interface ITeaEffects {
    // Stacking tea methods
    int getTeaStackLevel(String teaType);
    void addTeaStack(String teaType, int maxStacks);
    void clearTeaStack(String teaType);

    // Sleepiness specific methods
    boolean hasSleepBuff();
    void setSleepBuff(boolean value, int duration);
    void updateSleepBuff();
    int getSleepBuffTimeRemaining();

    // Other utility methods
    Map<String, Integer> getAllTeaStacks();
    void tickEffects(Player player);
}
