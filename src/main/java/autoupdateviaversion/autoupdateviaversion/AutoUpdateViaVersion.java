package autoupdateviaversion.autoupdateviaversion;

import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

        getLogger().info("Downloading latest version of ViaVersion...");

        try (InputStream in = new URL(latestVersionUrl).openStream();
             FileOutputStream out = new FileOutputStream(outputFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            getLogger().severe("Failed to download ViaVersion: " + e.getMessage());
            return;
        }

        getLogger().info("Successfully downloaded latest version of ViaVersion to " + outputFilePath);
    }

    private String getLatestVersion() throws IOException {
        String url = "https://ci.viaversion.com/job/ViaVersion/lastSuccessfulBuild/";
        Document doc = Jsoup.connect(url).get();
        Element artifactLink = doc.selectFirst("a[href$=.jar]");
        String href = artifactLink.attr("href");
        String version = href.substring(href.indexOf("ViaVersion-") + "ViaVersion-".length(), href.lastIndexOf(".jar"));
        return version;
    }

}
