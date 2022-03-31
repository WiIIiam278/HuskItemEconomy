package net.william278.huskitemeconomy;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.TreeMap;

public class Settings {

    public final TreeMap<Integer, Material> currencyItemValues = new TreeMap<>();
    public final Material singularDenomination;
    public final String singularCurrencyName;
    public final String pluralCurrencyName;
    public final String currencySymbol;
    public final String balanceMessage;

    public Settings(FileConfiguration config) {
        this.singularCurrencyName = config.getString("currency_options.name_singular", "Gold ingot");
        this.pluralCurrencyName = config.getString("currency_options.name_plural", "Gold ingots");
        this.currencySymbol = config.getString("currency_options.symbol", "xⒼ");
        this.singularDenomination = Material.matchMaterial(config.getString("currency_options.singular_denomination", "gold_ingot"));
        for (String key : Objects.requireNonNull(config.getConfigurationSection("currency_options.extra_item_values")).getKeys(false)) {
            this.currencyItemValues.put(Integer.parseInt(key), Material.matchMaterial(config.getString("currency_options.extra_item_values." + key, "gold_ingot")));
        }
        this.balanceMessage = config.getString("message_options.balance_message", "[Your current balance:](#00fb9a) [%1%](white)");
    }
}
