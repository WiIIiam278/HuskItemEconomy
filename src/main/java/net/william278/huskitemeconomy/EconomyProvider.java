package net.william278.huskitemeconomy;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class EconomyProvider extends AbstractEconomy {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "HuskItemEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return ((int) v) + HuskItemEconomy.getSettings().currencySymbol;
    }

    @Override
    public String currencyNamePlural() {
        return HuskItemEconomy.getSettings().pluralCurrencyName;
    }

    @Override
    public String currencyNameSingular() {
        return HuskItemEconomy.getSettings().singularCurrencyName;
    }

    /**
     * @param username The username of the account holder
     * @deprecated
     */
    @Override
    public boolean hasAccount(String username) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    /**
     * @deprecated
     */
    @Override
    public double getBalance(String s) {
        return DataManager.getPlayerBalance(s);
    }

    /**
     * @deprecated
     */
    @Override
    public double getBalance(String s, String s1) {
        return DataManager.getPlayerBalance(s);
    }

    /**
     * @deprecated
     */
    @Override
    public boolean has(String name, double amount) {
        return getBalance(name) >= amount;
    }

    /**
     * @deprecated
     */
    @Override
    public boolean has(String name, String s1, double amount) {
        return has(name, amount);
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String name, double amount) {
        if (!hasAccount(name)) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        }
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        }
        double bal = getBalance(name);

        if (bal < amount) {
            return new EconomyResponse(0.0D, bal, EconomyResponse.ResponseType.FAILURE, "Insufficient balance!");
        }
        DataManager.deductBalance(player, (int) amount);
        return new EconomyResponse((0 - amount), bal, EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String name, String s1, double amount) {
        return withdrawPlayer(name, amount);
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String name, double amount) {
        if (!hasAccount(name)) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        }
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        }
        double bal = getBalance(name);
        DataManager.giveBalance(player, (int) amount);
        return new EconomyResponse(amount, bal, EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String name, String s1, double amount) {
        return depositPlayer(name, amount);
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s) {
        return hasAccount(s);
    }

    /**
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }
}
