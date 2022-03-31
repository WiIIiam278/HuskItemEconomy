package net.william278.huskitemeconomy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.TreeMap;

public class DataManager {

    public static int getPlayerBalance(String username) {
        Player player = Bukkit.getPlayer(username);
        if (player == null) {
            return 0;
        } else {
            return getPlayerBalance(player);
        }
    }

    public static int getPlayerBalance(Player player) {
        int balance = 0;
        final TreeMap<Integer, Material> currencyItemValues = HuskItemEconomy.getSettings().currencyItemValues;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) {
                continue;
            }
            if (currencyItemValues.containsValue(itemStack.getType())) {
                for (Integer value : currencyItemValues.keySet()) {
                    if (currencyItemValues.get(value) == itemStack.getType()) {
                        balance = balance + (value * itemStack.getAmount());
                        break;
                    }
                }
                continue;
            }
            if (itemStack.getType() == HuskItemEconomy.getSettings().singularDenomination) {
                balance = balance + itemStack.getAmount();
            }
        }
        return balance;
    }

    public static void giveBalance(Player player, int amount) {
        for (int x = 0; x < amount; x++) {
            final ItemStack itemStack = new ItemStack(HuskItemEconomy.getSettings().singularDenomination, 1);
            if (player.getInventory().firstEmpty() == -1) {
                Bukkit.getScheduler().runTask(HuskItemEconomy.getInstance(), () -> player.getWorld().dropItem(player.getLocation(), itemStack));
                continue;
            }
            player.getInventory().addItem(itemStack);
        }
    }

    public static void deductBalance(Player player, int amount) {
        final TreeMap<Integer, Material> currencyItemValues = HuskItemEconomy.getSettings().currencyItemValues;
        final HashSet<ItemStack> storedItemStacks = new HashSet<>();
        final ItemStack itemStack = new ItemStack(HuskItemEconomy.getSettings().singularDenomination, 1);
        for (int x = 0; x < amount; x++) {
            if (!player.getInventory().contains(itemStack)) {
                for (Integer value : currencyItemValues.keySet()) {
                    if (player.getInventory().contains(currencyItemValues.get(value))) {
                        for (ItemStack largerDenomination : player.getInventory().getContents()) {
                            if (largerDenomination == null) {
                                continue;
                            }
                            if (largerDenomination.getType() == currencyItemValues.get(value)) {
                                if (player.getInventory().firstEmpty() == -1) {
                                    player.getInventory().removeItem(largerDenomination);
                                    storedItemStacks.add(new ItemStack(largerDenomination.getType(), largerDenomination.getAmount() - 1));
                                } else {
                                    player.getInventory().removeItem(new ItemStack(largerDenomination.getType(), 1));
                                }
                                player.getInventory().addItem(new ItemStack(HuskItemEconomy.getSettings().singularDenomination, value));
                                break;
                            }
                        }
                    } else {
                        for (ItemStack storedItemStack : storedItemStacks) {
                            if (storedItemStack.getType() == currencyItemValues.get(value)) {
                                if (storedItemStack.getAmount() > 1) {
                                    storedItemStack.setAmount(storedItemStack.getAmount() - 1);
                                } else {
                                    storedItemStacks.remove(storedItemStack);
                                }
                                player.getInventory().addItem(new ItemStack(HuskItemEconomy.getSettings().singularDenomination, value));
                            }
                        }
                    }
                }
            }
            player.getInventory().removeItem(itemStack);
        }
        for (ItemStack stack : storedItemStacks) {
            Bukkit.getScheduler().runTask(HuskItemEconomy.getInstance(), () -> player.getWorld().dropItem(player.getLocation(), stack));
        }
    }

}
