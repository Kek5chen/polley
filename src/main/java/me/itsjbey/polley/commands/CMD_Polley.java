package me.itsjbey.polley.commands;

import me.itsjbey.polley.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CMD_Polley implements CommandExecutor {

    Main main = Main.getInstance();

    YamlConfiguration yml = main.yml;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            sendHelp(sender);

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("end")) {

                if (sender.hasPermission("polley.admin")) {

                    if (!main.runningPoll.isRunning()) {

                        sender.sendMessage(Main.prefix + getString("No-Polley-Running"));

                        return true;

                    }

                    main.runningPoll.setRunning(false);

                    Bukkit.broadcastMessage(Main.prefix + "§7----------------- " + Main.prefix + "§7-----------------");
                    Bukkit.broadcastMessage(Main.prefix);
                    Bukkit.broadcastMessage(Main.prefix + getString("Polley-Over"));

                    for (String s : main.runningPoll.getVotes()) {

                        Bukkit.broadcastMessage(Main.prefix + "§a§l" + s + "§7: " + main.runningPoll.getVotesFor(s));

                    }

                    Bukkit.broadcastMessage(Main.prefix);
                    Bukkit.broadcastMessage(Main.prefix + "§7--------------------------------------------");


                }

            } else if (args[0].equalsIgnoreCase("vote") || args[0].equalsIgnoreCase("show")) {

                sender.sendMessage(Main.prefix + "§7----------------- " + Main.prefix + "§7-----------------");
                sender.sendMessage(Main.prefix);

                if (main.runningPoll.getVotes().size() == 0) {

                    sender.sendMessage(Main.prefix + getString("No-Running"));

                } else {

                    sender.sendMessage(Main.prefix +
                            (main.runningPoll.isRunning() ? getString("Votes-For")
                                    : getString("Polley-Over")));

                    int i = 1;

                    for (String s : main.runningPoll.getVotes()) {

                        sender.sendMessage(Main.prefix + "§7§l" + i + ". §a§l" + s + "§7: " + main.runningPoll.getVotesFor(s));

                        i++;

                    }

                }

                sender.sendMessage(Main.prefix);
                sender.sendMessage(Main.prefix + "§7--------------------------------------------");

            } else if (args[0].equalsIgnoreCase("results")) {

                sender.sendMessage(Main.prefix + "§7----------------- " + Main.prefix + "§7-----------------");
                sender.sendMessage(Main.prefix);

                if (main.runningPoll.getVotes().size() == 0) {

                    sender.sendMessage(Main.prefix + getString("No-Running"));

                } else {

                    sender.sendMessage(Main.prefix +
                            (main.runningPoll.isRunning() ? getString("Votes-For")
                                    : getString("Polley-Over")));

                    for (String s : main.runningPoll.getVotes()) {

                        sender.sendMessage(Main.prefix + "§a§l" + s + "§7: " + main.runningPoll.getVotesFor(s));

                    }

                }

                sender.sendMessage(Main.prefix);
                sender.sendMessage(Main.prefix + "§7--------------------------------------------");

            } else {

                sendHelp(sender);

            }

        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("vote")) {

                if (main.runningPoll.hasVoted((HumanEntity) sender)) {

                    sender.sendMessage(Main.prefix + getString("Already-Voted"));

                    return true;

                }

                if (!main.runningPoll.isRunning()) {

                    sender.sendMessage(Main.prefix + getString("No-Running"));

                    return true;

                }

                int num;

                try {

                    num = Integer.parseInt(args[1]);

                } catch (NumberFormatException e) {

                    sender.sendMessage(Main.prefix + getString("Input-Number"));

                    return true;

                }

                if (num <= 0) {

                    sender.sendMessage(Main.prefix + getString("Above-Null"));

                    return true;

                }

                if(num > main.runningPoll.getVotes().size()) {

                    sender.sendMessage(Main.prefix + getString("Not-Exist"));

                    return true;

                }

                main.runningPoll.addVote(num, (HumanEntity) sender);

                sender.sendMessage(Main.prefix + getString("Vote-Added"));

            }

        } else {

            if (args[0].equalsIgnoreCase("create")) {

                if (sender.hasPermission("polley.admin")) {

                    if (main.runningPoll.isRunning()) {

                        sender.sendMessage(Main.prefix + getString("Polley-Running"));

                        return true;

                    }

                    main.runningPoll.setName(args[1]);

                    ArrayList<String> votes = new ArrayList<>();
                    StringBuilder description = new StringBuilder();

                    int i = 0;

                    boolean isDesc = false;

                    for (String s : args) {

                        if (i > 1) {

                            if (isDesc) {

                                description.append(s).append(" ");

                            } else {

                                if (s.startsWith("Desc:")) {

                                    isDesc = true;

                                    description.append(s.substring(5, s.length())).append(" ");

                                } else {

                                    votes.add(s);

                                }

                            }

                        }

                        i++;

                    }

                    main.runningPoll.resetVoted();
                    main.runningPoll.setDescription(description.toString());
                    main.runningPoll.setVotes(votes);
                    main.runningPoll.setRunning(true);

                    Bukkit.broadcastMessage(Main.prefix + "§7----------------- " + Main.prefix + "§7-----------------");
                    Bukkit.broadcastMessage(Main.prefix);
                    Bukkit.broadcastMessage(Main.prefix + getString("Polley-Started"));
                    Bukkit.broadcastMessage(Main.prefix);
                    Bukkit.broadcastMessage(Main.prefix + "§a§lName: §7§l" + main.runningPoll.getName());

                    if(!main.runningPoll.getDescription().equals("")) {

                        Bukkit.broadcastMessage(Main.prefix + "§a§lDescription: §7§l" + main.runningPoll.getDescription());

                    }

                    Bukkit.broadcastMessage(Main.prefix);
                    Bukkit.broadcastMessage(Main.prefix + "§7--------------------------------------------");

                }

            }

        }

        return true;

    }

    public void sendHelp(CommandSender sender) {

        if (!sender.hasPermission("polley.admin")) {

            sender.sendMessage(Main.prefix + "§7----------------- " + Main.prefix + "§7-----------------");
            for(String s : yml.getStringList("Help-Player"))
                sender.sendMessage(Main.prefix + turnString(s));
            sender.sendMessage(Main.prefix + "§7--------------------------------------------");

        } else {

            sender.sendMessage(Main.prefix + "§7----------------- " + Main.prefix + "§7-----------------");
            for(String s : yml.getStringList("Help-Team"))
                sender.sendMessage(Main.prefix + turnString(s));
            sender.sendMessage(Main.prefix + "§7--------------------------------------------");

        }

    }

    public String getString(String p) {

        return ChatColor.translateAlternateColorCodes('&', yml.getString(p));

    }

    public static String turnString(String p) {

        return ChatColor.translateAlternateColorCodes('&', p);

    }

}
