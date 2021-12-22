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
import cn.nukkit.command.CommandSender;
import com.larryTheCoder.ASkyBlock;
import com.larryTheCoder.listener.invitation.Invitation;
import com.larryTheCoder.listener.invitation.InvitationHandler;

import java.util.Arrays;
import java.util.List;

public class CoopCategory extends SubCategory {

    public CoopCategory(ASkyBlock plugin) {
        super(plugin);
    }

    @Override
    public List<String> baseCommands() {
        return Arrays.asList("coop", "co");
    }

    @Override
    public List<String> getCommands() {
        return Arrays.asList("invite", "accept", "decline", "kick", "promote", "demote", "transfer", "leave");
    }

    @Override
    public boolean canUse(CommandSender sender, String command) {
        switch (command.toLowerCase()) {
            case "accept":
                return hasPermission(sender, "is.command.accept") && sender.isPlayer();
            case "deny":
            case "reject":
                return hasPermission(sender, "is.command.reject") && sender.isPlayer();
            case "invite":
                return hasPermission(sender, "is.command.invite") && sender.isPlayer();
            case "kickmember":
                return hasPermission(sender, "is.command.kick") && sender.isPlayer();
            case "quit":
                return hasPermission(sender, "is.command.quit") && sender.isPlayer();
        }
        return false;
    }

    @Override
    public String getDescription(String command) {
        switch (command.toLowerCase()) {
            case "accept":
                return "Chấp nhận lời mời từ người chơi khác";
            case "reject":
                return "Từ chối lời mời";
            case "invite":
                return "Mời người chơi khác vào đảo của bạn";
            case "kickmember":
                return "Đuổi người chơi trên đảo của bạn";
            case "quit":
                return "Thoát, không muốn làm thành viên trên đảo này";
        }
        return null;
    }

    @Override
    public String getParameters(String commandName) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, String[] args) {
        Player p = sender instanceof Player ? (Player) sender : null;
        if (p == null) {
            return;
        }

        InvitationHandler handler = getPlugin().getInvitationHandler();

        switch (args[0].toLowerCase()) {
            case "accept":
                Invitation invite = handler.getInvitation(p);
                if (invite == null) {
                    sender.sendMessage(getPrefix() + getLocale(p).errorNotPending);
                    break;
                }

                if (args.length == 2) {
                    invite = handler.getInvitation(p, args[1]);

                    if (invite == null) {
                        sender.sendMessage(getPrefix() + getLocale(p).errorNotPending2.replace("[player]", args[1]));
                        break;
                    }
                }

                invite.acceptInvitation();
                break;
            case "deny":
            case "reject":
                invite = handler.getInvitation(p);
                if (invite == null) {
                    sender.sendMessage(getPrefix() + getLocale(p).errorNotPending);
                    break;
                }

                if (args.length == 2) {
                    invite = handler.getInvitation(p, args[1]);

                    if (invite == null) {
                        sender.sendMessage(getPrefix() + getLocale(p).errorNotPending2.replace("[player]", args[1]));
                        break;
                    }
                }

                invite.denyInvitation();
                break;
            case "invite":
                if (args.length != 2) {
                    break;
                }

                // Player cannot invite other players when he have no island
                getPlugin().getFastCache().getRelations(p.getPosition(), data -> {
                    if (data == null) {
                        p.sendMessage(getPrefix() + "Vị trí mà bạn đang đứng không phải là đảo của bạn.");
                        return;
                    }

                    if (!data.isAdmin(p)) {
                        p.sendMessage(getPrefix() + "Bạn không phải là quản trị viên hòn đảo này");
                        return;
                    }

                    getPlugin().getFastCache().getIslandData(data.getIslandUniqueId(), pd -> {
                        Player inviter = p.getServer().getPlayer(args[1]);
                        if (inviter == null) {
                            p.sendMessage(getPrefix() + getLocale(p).errorOfflinePlayer);
                            return;
                        }

                        getPlugin().getInvitationHandler().addInvitation(sender, inviter, pd);
                    });
                });
                break;
        }
    }
}
