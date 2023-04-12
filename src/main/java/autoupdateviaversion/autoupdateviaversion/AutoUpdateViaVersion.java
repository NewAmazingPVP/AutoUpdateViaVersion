package autoupdateviaversion.autoupdateviaversion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class AutoUpdateViaVersion extends JavaPlugin {

    @Override
    public void onEnable() {
        String latestVersionUrl;
        try {
            latestVersionUrl = "https://ci.viaversion.com/job/ViaVersion/lastSuccessfulBuild/artifact/build/libs/ViaVersion-" + getLatestVersion() + ".jar";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String outputFilePath = "plugins/ViaVersion.jar";

        Plugin viaVersionPlugin = Bukkit.getPluginManager().getPlugin("ViaVersion");
        if (viaVersionPlugin != null) {
            String currentVersion = viaVersionPlugin.getDescription().getVersion();
            try {
                if (currentVersion.equals(getLatestVersion())) {
                    getLogger().info(ChatColor.GREEN + "ViaVersion is already up to date. Nothing has been installed");
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        getLogger().info("Downloading latest version of ViaVersion...");

        try (InputStream in = new URL(latestVersionUrl).openStream();
             FileOutputStream out = new FileOutputStream(outputFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            getLogger().severe(ChatColor.RED + "Failed to download ViaVersion: " + e.getMessage());
            return;
        }

        getLogger().info(ChatColor.BLUE + "Successfully downloaded latest version of ViaVersion to " + outputFilePath + ChatColor.YELLOW + ". Note: The old ViaVersion plugin will be disabled and the new one will be loaded, causing a small error.");

        // Disable and enable ViaVersion to load the new version
        PluginManager pluginManager = Bukkit.getPluginManager();
        try {
            if (viaVersionPlugin != null) {
                pluginManager.disablePlugin(viaVersionPlugin);
            }
            pluginManager.loadPlugin(new File(outputFilePath));
        } catch (InvalidPluginException | InvalidDescriptionException e) {
            throw new RuntimeException(e);
        }
    }

    private String getLatestVersion() throws IOException {
        String url = "https://ci.viaversion.com/job/ViaVersion/lastSuccessfulBuild/";
        Document doc = Jsoup.connect(url).get();
        Element artifactLink = doc.selectFirst("a[href$=.jar]");
        assert artifactLink != null;
        String href = artifactLink.attr("href");
        return href.substring(href.indexOf("ViaVersion-") + "ViaVersion-".length(), href.lastIndexOf(".jar"));
    }

}
