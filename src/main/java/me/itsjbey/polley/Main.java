package me.itsjbey.polley;

import com.google.common.io.CharStreams;
import me.itsjbey.polley.commands.CMD_Polley;
import me.itsjbey.polley.types.Poll;
import org.apache.commons.io.IOUtils;
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

        messages.getParentFile().mkdirs();

        if(!messages.exists()) {

            try {
                messages.createNewFile();

                String mess = "";

                try (Reader reader = new InputStreamReader(getResource("messages.yml"))) {
                    mess = CharStreams.toString(reader);
                }

                PrintWriter pw = new PrintWriter(messages);
                pw.print(mess);
                pw.flush();
                pw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        yml = YamlConfiguration.loadConfiguration(messages);

        Bukkit.getPluginCommand("polley").setExecutor(new CMD_Polley());

    }

    public static Main getInstance() {

        return instance;

    }
}
