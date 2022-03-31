package net.william278.huskitemeconomy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HuskItemEconomy extends JavaPlugin {

    private static HuskItemEconomy instance;
    public static HuskItemEconomy getInstance() {
        return instance;
    }

    private static Settings settings;
    public static Settings getSettings() {
        return settings;
    }
    private void setSettings() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        settings = new Settings(getConfig());
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Load settings
        setSettings();

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault not found! Plugin will not function!");
            return;
        }
        Objects.requireNonNull(getCommand("balance")).setExecutor(new BalanceCommand());

        getServer().getServicesManager().register(Economy.class, new EconomyProvider(), this, ServicePriority.Normal);
    }
}
