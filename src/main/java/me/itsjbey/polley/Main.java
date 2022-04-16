package me.itsjbey.polley;

import me.itsjbey.polley.commands.CMD_Polley;
import me.itsjbey.polley.types.Poll;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Main extends JavaPlugin  {
    private static Main instance;
    public static final File messages = new File("plugins/Polley", "messages.yml");
    public YamlConfiguration yml;

    public static final String prefix = "§7[§5§lPolley§7] §r";
    public Poll runningPoll = new Poll();

    public void onEnable() {
        instance = this;
        saveResource("messages.yml", false);
        yml = YamlConfiguration.loadConfiguration(messages);
        Bukkit.getPluginCommand("polley").setExecutor(new CMD_Polley());
    }

    public static Main getInstance() {
        return instance;
    }
}
