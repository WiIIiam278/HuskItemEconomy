package net.william278.huskitemeconomy;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;

public class Settings {

    public final TreeMap<Integer, ItemStack> currencyItemValues = new TreeMap<>();
    public final ItemStack singularDenomination;
    public final String singularCurrencyName;
    public final String pluralCurrencyName;
    public final String currencySymbol;
    public final String balanceMessage;

    public Settings(FileConfiguration config) {
        this.singularCurrencyName = config.getString("currency_options.name_singular", "Gold ingot");
        this.pluralCurrencyName = config.getString("currency_options.name_plural", "Gold ingots");
        this.currencySymbol = config.getString("currency_options.symbol", "xⒼ");

        this.singularDenomination = getItemStackFromString(config.getString("currency_options.singular_denomination", "gold_ingot"));
        for (String key : Objects.requireNonNull(config.getConfigurationSection("currency_options.extra_item_values")).getKeys(false)) {
            this.currencyItemValues.put(Integer.parseInt(key), getItemStackFromString("currency_options.extra_item_values." + key));
        }
        this.balanceMessage = config.getString("message_options.balance_message", "[Your current balance:](#00fb9a) [%1%](white)");
    }

    private ItemStack getItemStackFromString(String string) {
        try {
            if (!string.contains(":")) {
                return new ItemStack(Material.valueOf(string.toUpperCase()), 1);
            } else {
                final Material material = Material.valueOf(string.split(":")[0].toUpperCase());
                final int itemData = Integer.parseInt(string.split(":")[1]);
                ItemStack itemStack = new ItemStack(material, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setCustomModelData(itemData);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            }
        } catch (Exception e) {
            HuskItemEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to load item stack data", e);
            return new ItemStack(Material.GOLD_INGOT, 1);
        }
    }
}
