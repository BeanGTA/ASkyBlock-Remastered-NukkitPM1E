package com.larryTheCoder.command.category;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import com.larryTheCoder.ASkyBlock;
import com.larryTheCoder.utils.BlockUtil;

import java.util.Arrays;
import java.util.List;

public class IslandCategory extends SubCategory {

    public IslandCategory(ASkyBlock plugin) {
        super(plugin);
    }

    @Override
    public List<String> getCommands() {
        return Arrays.asList("taodao", "reset", "delete", "home", "sethome");
    }

    @Override
    public boolean canUse(CommandSender sender, String command) {
        switch (command) {
            case "taodao":
                return hasPermission(sender, "is.create") && sender.isPlayer();
            case "reset":
            case "delete":
                return hasPermission(sender, "is.command.reset") && sender.isPlayer();
            case "home":
            case "sethome":
                return hasPermission(sender, "is.command.home") && sender.isPlayer();
            
           
        }

        return false;
    }

    @Override
    public String getDescription(String commandName) {
        switch (commandName.toLowerCase()) {
            case "taodao":
                return "Tạo một đảo mới";
            case "reset":
                return "Reset lại đảo về nguyên thủy";
            case "delete":
                return "Xóa Đảo";
            case "home":
                return "Để về vị trí đã sethome";
            case "sethome":
                return "Để set vị trí home";
           
            default:
                return null;
        }
    }

    @Override
    public String getParameters(String commandName) {
        if (commandName.toLowerCase().equals("tp")) {
            return "[Tên]";
        }

        return "";
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, String[] args) {
        Player p = sender.getServer().getPlayer(sender.getName());

        switch (args[0].toLowerCase()) {
            case "taodao":
                getPlugin().getPanel().addIslandFormOverlay(p);
                break;
            case "delete":
            case "reset":
                getPlugin().getPanel().addDeleteFormOverlay(p);
                break;
            case "home":
                // Only one home? Don't worry. we wont open the form overlay
                getPlugin().getFastCache().getIslandsFrom(p.getName(), listHome -> {
                    if (listHome == null) {
                        p.sendMessage(getPlugin().getLocale(p).errorFailedCritical);
                        return;
                    }

                    if (listHome.size() == 1) {
                        getPlugin().getGrid().homeTeleport(p);
                        return;
                    }

                    getPlugin().getPanel().addHomeFormOverlay(p, listHome);
                });
                break;
            case "sethome":
                getPlugin().getFastCache().getIslandData(p.getLocation(), pd -> {
                    // Check if the ground is an air
                    if (!BlockUtil.isBreathable(p.clone().add(p.down()).getLevelBlock())) {
                        p.sendMessage(getLocale(p).groundNoAir);
                        return;
                    }
                    // Check if the player on their own island or not
                    if (pd != null && pd.getPlotOwner().equalsIgnoreCase(sender.getName())) {
                        pd.setHomeLocation(p.getLocation());
                        pd.saveIslandData();

                        p.sendMessage(getLocale(p).setHomeSuccess);
                    } else {
                        p.sendMessage(getLocale(p).errorNotOnIsland);
                    }
                });
                break;
            case "tp":
                if (args.length != 2) {
                    break;
                }

                getPlugin().getIslandManager().teleportPlayer(p, args[1]);
                break;
        }
    }
}
