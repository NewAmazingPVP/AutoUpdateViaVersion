# Note
- I have officially stopped working on this plugin and instead am working on this new feature-rich plugin, [AutoViaUpdater](https://github.com/NewAmazingPVP/AutoViaUpdater). This new plugin automates the updating of all the Vias, such as ViaVersion, ViaRewind, ViaBackwards, and ViaRewind-Legacy, all-together.

# AutoUpdateViaVersion
AutoUpdateViaVersion is a plugin for Minecraft servers that automatically updates the ViaVersion plugin to the most recent successful build. 

Spigot link: https://www.spigotmc.org/resources/autoupdateviaversion.109224/

# Current Features
- Compatible with Minecraft versions 1.8 and higher, including the latest versions.

- Automatically downloads successful builds of ViaVersion from Jenkins.

# Planned Features
- Allow users to choose between if they want to install the latest ViaVersion or ViaVersion-dev builds (for stability)

- Allow plugins dependent on ViaVersion such as ViaBackwards, ViaRewind, etc. to properly enable on the first restart (it works currently but needs two restarts)

- This is not all, if you have a suggestion or feature request, please create a new issue in the project's GitHub repository.

# Installation

Download the latest release of the plugin from the releases page.
Copy the downloaded .jar file to the plugins directory of your Minecraft server.
Restart the server.
Make sure ViaVersion is not already installed because it will automatically do that.
AutoUpdateViaVersion does not require any configuration or setup. Once installed, it will automatically update ViaVersion to the most recent successful build whenever the server starts.

# Note
If you have plugins that are dependent on ViaVersion, such as ViaBackwards or ViaRewind, you need to restart the server twice to get them properly initialized when a new version of ViaVersion is released. I am working on a fix for this, so stay updated.

# License
AutoUpdateViaVersion is released under the MIT License. See the LICENSE file for more information.
