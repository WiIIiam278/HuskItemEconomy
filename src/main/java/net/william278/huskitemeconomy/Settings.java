package net.william278.huskitemeconomy;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.TreeMap;

public class Settings {

    private final TreeMap<Integer, Material> currencyItemValues = new TreeMap<>();
    private final Material singularDenomination;
    private final String singularCurrencyName;
    private final String pluralCurrencyName;
    private final String currencySymbol;
    private final String balanceMessage;

    public Settings(FileConfiguration config) {
        this.singularCurrencyName = config.getString("currency_options.name_singular");
        this.pluralCurrencyName = config.getString("currency_options.name_plural");
        this.currencySymbol = config.getString("currency_options.symbol");
        this.singularDenomination = Material.matchMaterial(config.getString("currency_options.singular_denomination", "gold_ingot"));
        for (String key : Objects.requireNonNull(config.getConfigurationSection("currency_options.extra_item_values")).getKeys(false)) {
            this.currencyItemValues.put(Integer.parseInt(key), Material.matchMaterial(config.getString("currency_options.extra_item_values." + key, "gold_ingot")));
        }
        this.balanceMessage = config.getString("message_options.balance_message");
    }

    public TreeMap<Integer, Material> getCurrencyItemValues() {
        return currencyItemValues;
    }

    public String getSingularCurrencyName() {
        return singularCurrencyName;
    }

    public String getPluralCurrencyName() {
        return pluralCurrencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public Material getSingularDenomination() { return singularDenomination; }

    public String getBalanceMessage() { return balanceMessage; }
}
