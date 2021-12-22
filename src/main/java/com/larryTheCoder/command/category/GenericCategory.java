/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2020 larryTheCoder and contributors
 *
 * Permission is hereby granted to any persons and/or organizations
 * using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or
 * any derivatives of the work for commercial use or any other means to generate
 * income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing
 * and/or trademarking this software without explicit permission from larryTheCoder.
 *
 * Any persons and/or organizations using this software must disclose their
 * source code and have it publicly available, include this license,
 * provide sufficient credit to the original authors of the project (IE: larryTheCoder),
 * as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,FITNESS FOR A PARTICULAR
 * PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.larryTheCoder.command.category;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import com.larryTheCoder.ASkyBlock;
import com.larryTheCoder.island.TopTen;
import com.larryTheCoder.locales.LocaleInstance;
import com.larryTheCoder.utils.Settings;
import com.larryTheCoder.utils.Utils;

import java.util.*;

public class GenericCategory extends SubCategory {

    public GenericCategory(ASkyBlock plugin) {
        super(plugin);
    }

    @Override
    public List<String> getCommands() {
        return Arrays.asList("expel", "kick", "lobby", "leave", "spawn", "locale", "protection", "settings", "top", "about", "test", "clear");
    }

    @Override
    public boolean canUse(CommandSender sender, String command) {
        switch (command.toLowerCase()) {
            case "lobby":
            case "spawn":
            case "leave":
                return hasPermission(sender, "is.command.leave") && sender.isPlayer();
            case "settings":
                return hasPermission(sender, "is.panel.setting") && sender.isPlayer();
            default:
                return false;
        }
    }

    @Override
    public String getDescription(String commandName) {
        switch (commandName.toLowerCase()) {
            case "lobby":
                return "Rời khỏi hòn đảo của bạn và dịch chuyển đến sảnh máy chủ";
            case "settings":
                return "Thay đổi cài đặt cho đảo của bạn";
           
            default:
                return null;
        }
    }

    @Override
    public String getParameters(String commandName) {
        switch (commandName.toLowerCase()) {
            case "expel":
            case "kick":
                return "[Tên Người Chơi]";
            case "locale":
                return "[Ngôn Ngữ]";
            default:
                return "";
        }
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, String[] args) {
        Player p = Server.getInstance().getPlayer(sender.getName());

        switch (args[0].toLowerCase()) {
            case "clear":
                getPlugin().getFastCache().clearSavedCaches();

                Utils.send("DONE");
                break;
            case "test":
                getPlugin().getFastCache().dumpAllCaches();
                break;
            case "expel":
            case "kick":
                if (getPlugin().getIslandManager().checkIsland(p)) {
                    sender.sendMessage(getPrefix() + getLocale(p).errorNoIsland);
                    break;
                } else if (args.length != 2) {
                    break;
                }
                if (getPlugin().getServer().getPlayer(args[1]) == null) {
                    sender.sendMessage(getPrefix() + getLocale(p).errorOfflinePlayer);
                    break;
                }

                getPlugin().getIslandManager().kickPlayerByName(p, args[1]);
                break;
            case "lobby":
            case "spawn":
            case "leave":
                if (getPlugin().getLevel().stream()
                        .noneMatch(i -> i.getLevel().getName().equalsIgnoreCase(p.getLevel().getName()))) {
                    sender.sendMessage(getPrefix() + getLocale(p).errorWrongWorld);
                    break;
                }

                if (!p.isOp() && p.getGamemode() != 0) p.setGamemode(0);
                if (Settings.saveInventory) getPlugin().getInventory().loadPlayerInventory(p);

                p.teleport(Location.fromObject(getPlugin().getServer().getDefaultLevel().getSafeSpawn()));
                break;
            case "locale":
                if (args.length < 2) {
                    displayLocales(p);
                    break;
                }

                if (!Utils.isNumeric(args[1])) {
                    displayLocales(p);
                    break;
                } else {
                    final int index = Integer.parseInt(args[1]);
                    if (index < 1 || index > getPlugin().getAvailableLocales().size()) {
                        displayLocales(p);
                        break;
                    }

                    LocaleInstance locale = getPlugin().getAvailableLocales()
                            .values().stream()
                            .filter(i -> i.getIndex() == index)
                            .findAny().orElse(null);

                    // Recheck again if there the index is not in the list.
                    if (locale == null) {
                        displayLocales(p);
                        return;
                    }

                    // Now we update them into the list.
                    getPlugin().getFastCache().getPlayerData(p.getName(), pd -> {
                        if (pd == null) {
                            p.sendMessage("An error occured while attempting to save your data.");
                            return;
                        }

                        pd.setLocale(locale.getLocaleName());
                        pd.saveData();

                        p.sendMessage(String.format("Successfully marked %s as your default locale", locale.getLocaleName()));
                    });
                }
                break;
            case "protection":
                getPlugin().getPanel().addProtectionOverlay(p);
                break;
            case "settings":
                getPlugin().getPanel().addSettingFormOverlay(p);
                break;
            case "top":
                TopTen.topTenShow(sender);
                break;
        }
    }

    private void displayLocales(Player player) {
        player.sendMessage(TextFormat.GREEN + "Your default locale: " + TextFormat.YELLOW + getPlugin().getLocale(player).getLocaleName());
        player.sendMessage(TextFormat.RED + "/is lang <#>");

        TreeMap<Integer, String> locales = new TreeMap<>();
        for (LocaleInstance locale : getPlugin().getAvailableLocales().values()) {
            if (!locale.getLocaleName().equalsIgnoreCase("locale")) {
                locales.put(locale.getIndex(), locale.getLanguageName() + " (" + locale.getCountryName() + ")");
            }
        }

        for (Map.Entry<Integer, String> entry : locales.entrySet()) {
            player.sendMessage(entry.getKey() + ": " + entry.getValue());
        }
    }
}
