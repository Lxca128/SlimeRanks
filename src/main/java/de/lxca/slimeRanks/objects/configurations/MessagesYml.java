package de.lxca.slimeRanks.objects.configurations;

import java.util.List;

public class MessagesYml extends Yml {

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "messages.yml";

    public MessagesYml() {
        super(filePath, fileName);
        setDefaultYmlKeys();
    }

    @Override
    protected void setDefaultYmlKeys() {
        createConfigKey("Chat.Brand", "<color:#39ff14><b>SlimeRanks</b></color> <dark_gray>»</dark_gray> ");

        createConfigKey("Chat.Command.Reload", "<gray>The configurations have been reloaded</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.About", "<newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline><newline><dark_gray>|</dark_gray> <gray>Description</gray><dark_gray>:</dark_gray> <color:#39ff14>{description}</color><newline><dark_gray>|</dark_gray> <gray>Author</gray><dark_gray>:</dark_gray> <color:#39ff14>{author}</color><newline><dark_gray>|</dark_gray> <gray>Version</gray><dark_gray>:</dark_gray> <color:#39ff14>{version}</color><newline><newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline>");
        createConfigKey("Chat.Command.Help", "<newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks about</color> <dark_gray>|</dark_gray> <gray>Displays information about SlimeRanks</gray><dark_gray>.</dark_gray><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks gui</color> <dark_gray>|</dark_gray> <gray>Opens the GUI to manage all ranks</gray><dark_gray>.</dark_gray><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks help</color> <dark_gray>|</dark_gray> <gray>Shows this help message</gray><dark_gray>.</dark_gray><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks import <dark_gray><</dark_gray>LuckPerms<dark_gray>></dark_gray></color> <dark_gray>|</dark_gray> <gray>Imports LuckPerms groups into SlimeRanks</gray><dark_gray>.</dark_gray><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks reload</color> <dark_gray>|</dark_gray> <gray>Reloads the configurations</gray><dark_gray>.</dark_gray><newline><newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline>");
        createConfigKey("Chat.Command.Unknown", "<gray>The command <color:#39ff14>{command}</color> is unknown<dark_gray>.</dark_gray> Use <color:#39ff14>/slimeranks help</color> to see all available commands</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.OnlyPlayers", "<red>Only players can use this command</red><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.Import.LuckPermsNotFound", "<red>To import ranks from <color:#ff1439>LuckPerms</color><dark_gray>,</dark_gray> it must be installed and active<dark_gray>.</dark_gray> Please check if <color:#ff1439>LuckPerms</color> has been loaded correctly</red><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.Import.LuckPermsSuccess", "<gray>All ranks were successfully imported from <color:#39ff14>LuckPerms</color> and assigned<dark_gray>.</dark_gray><newline>Use <color:#39ff14>/slimeranks gui</color> to customize the design of your ranks</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.Import.LuckPermsPartiallySuccess", "<red>Some ranks couldn<dark_gray>'</dark_gray>t be imported from <color:#ff1439>LuckPerms</color><dark_gray>.</dark_gray> Check the server console for more details</red><dark_gray>.</dark_gray><newline><gray>Use <color:#39ff14>/slimeranks gui</color> to customize the design of the imported ranks</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.Import.UnknownService", "<red>The selected service <color:#ff1439>{import_service}</color> you<dark_gray>'</dark_gray>re trying to import ranks from is currently not supported</red><dark_gray>.</dark_gray><newline><gray>Use <color:#39ff14>/slimeranks help</color> to see a list of supported services</gray><dark_gray>.</dark_gray>");

        createConfigKey("Chat.Action.UpdateAvailable", "<gray>A new version of <color:#39ff14>SlimeRanks</color> has been released on <color:#39ff14>Modrinth</color></gray><dark_gray>!</dark_gray><newline><color:#39ff14><b><click:open_url:'https://modrinth.com/plugin/slimeranks'>Click here</click></b></color> <gray>to update your version</gray> <dark_gray>(</dark_gray><color:#39ff14><color:#ff1439>{current_version}</color></color> <dark_gray>-></dark_gray> <color:#39ff14>{newest_version}</color><dark_gray>).</dark_gray>");
        createConfigKey("Chat.Action.VersionUpdateAvailable", "<gray>A new version of <color:#39ff14>SlimeRanks</color> has been released on <color:#39ff14>Modrinth</color></gray><dark_gray>!</dark_gray><newline><color:#39ff14><b><click:open_url:'https://modrinth.com/plugin/slimeranks'>Click here</click></b></color> <gray>to update your version</gray> <dark_gray>(</dark_gray><color:#39ff14><color:#ff1439>{current_version}</color></color> <dark_gray>-></dark_gray> <color:#39ff14>{newest_version}</color><dark_gray>).</dark_gray><newline><color:#ff3914>The latest version of SlimeRanks no longer supports this Minecraft version<dark_gray>,</dark_gray> so support is only limitedly available</color><dark_gray>.</dark_gray>");

        createConfigKey("Chat.Input.Started", "<gray>Chat input started<dark_gray>.</dark_gray> To cancel the process<dark_gray>,</dark_gray> write</gray> <color:#39ff24>cancel</color><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.Ended", "<gray>Chat input session ended</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.RankIdentifier.OnlyLetters", "<red>The rank identifier can only contain letters</red><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.RankIdentifier.AlreadyUsed", "<red>The rank identifier is already in use</red><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.OnlyNumbers", "<red>The input must only contain numbers</red><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.HigherThanZero", "<red>The input must be greater than or equal to</red> <color:#ff1439>0</color><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.CreateRank", "<gray>Please enter the desired identifier for the rank</gray><dark_gray>.</dark_gray><newline><gray>Only letters are allowed</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.TabFormat", "<gray>Please enter the desired <color:#39ff14>Tablist Format</color> in <color:#39ff14>MiniMessage format</color></gray><dark_gray>.</dark_gray><newline><gray>The following placeholders are supported</gray><dark_gray>:</dark_gray> <color:#39ff14>{player}</color><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.ChatFormat", "<gray>Please enter the desired <color:#39ff14>Chat Format</color> in <color:#39ff14>MiniMessage format</color></gray><dark_gray>.</dark_gray><newline><gray>The following placeholders are supported</gray><dark_gray>:</dark_gray> <color:#39ff14>{player}<dark_gray>,</dark_gray> {message}</color><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.NameTagFormat", "<gray>Please enter the desired <color:#39ff14>NameTag Format</color> in <color:#39ff14>MiniMessage format</color></gray><dark_gray>.</dark_gray><newline><gray>The following placeholders are supported</gray><dark_gray>:</dark_gray> <color:#39ff14>{player}</color><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.TabPriority", "<gray>Please enter the desired <color:#39ff14>tab priority</color> as a <color:#39ff14>number</color><dark_gray>.</dark_gray></gray><newline><gray>The higher the number<dark_gray>,</dark_gray> the higher the rank will be displayed<dark_gray>.</dark_gray> A value of <color:#39ff14>0</color> disables sorting</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.Permission", "<gray>Please enter the required <color:#39ff14>permission</color> the player needs to obtain the rank</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.RankPriority", "<gray>Please enter the desired <color:#39ff14>rank priority</color> as a <color:#39ff14>number</color><dark_gray>.</dark_gray></gray><newline><gray>If a player has multiple ranks<dark_gray>,</dark_gray> they will use the rank with the highest priority<dark_gray>.</dark_gray>");
        createConfigKey("Chat.Input.RankIdentifier.CreationFailed", "<red>An error occurred while creating the rank. Please try again</red><dark_gray>.</dark_gray>");

        createConfigKey("Gui.Global.ItemName.Close", "<color:#ff1439><u>Close</u></color>");
        createConfigKey("Gui.Global.ItemName.Back", "<color:#39ff14><u>Back</u></color>");
        createConfigKey("Gui.Global.ItemName.PreviousPage", "<color:#39ff14><u>Previous page</u></color>");
        createConfigKey("Gui.Global.ItemName.NextPage", "<color:#39ff14><u>Next page</u></color>");
        createConfigKey("Gui.Global.ItemName.Rank", "<color:#39ff14>{identifier}</color>");
        createConfigKey("Gui.Global.ItemLore.Rank", List.of("", "<gray>Identifier</gray><dark_gray>:</dark_gray> <color:#39ff14>{identifier}</color>", "<gray>Tablist format</gray><dark_gray>:</dark_gray> <white>{tablist_format}</white>", "<gray>Chat format</gray><dark_gray>:</dark_gray> <white>{chat_format}</white>", "<gray>Name tag format</gray><dark_gray>:</dark_gray> <white>{name_tag_format}</white>", "", "<gray>Rank priority</gray><dark_gray>:</dark_gray> <color:#39ff14>{rank_priority}</color>", "<gray>Permission</gray><dark_gray>:</dark_gray> <color:#39ff14>{permission}</color>", "<gray>Tab priority</gray><dark_gray>:</dark_gray> <color:#39ff14>{tab_priority}</color>", "<gray>Hide name tag on sneak</gray><dark_gray>:</dark_gray> <color:#39ff14>{hide_name_tag_on_sneak}</color>", "<gray>Colored chat messages</gray><dark_gray>:</dark_gray> <color:#39ff14>{colored_messages}</color>"));

        createConfigKey("Gui.Overview.Title", "Rank overview");
        createConfigKey("Gui.Overview.ItemName.Title", "<color:#39ff14><b>Rank overview</b></color>");
        createConfigKey("Gui.Overview.ItemName.Reload", "<color:#39ff14><u>Reload configurations</u></color>");
        createConfigKey("Gui.Overview.ItemName.CreateRank", "<color:#39ff14><u>Create rank</u></color>");
        createConfigKey("Gui.Overview.ItemLore.Title", List.of("", "<gray>In this overview you can manage</gray>", "<gray>and customize all your ranks</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Overview.ItemLore.Reload", List.of("", "<color:#39ff14>Click</color> <gray>to reload all plugin configurations</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Overview.ItemLore.CreateRank", List.of("", "<color:#39ff14>Click</color> <gray>to create a new rank</gray><dark_gray>.</dark_gray>"));

        createConfigKey("Gui.Edit.Title", "Edit rank ({identifier})");
        createConfigKey("Gui.Edit.ItemName.Tablist", "<color:#39ff14><u>Tablist format</u></color>");
        createConfigKey("Gui.Edit.ItemName.Chat", "<color:#39ff14><u>Chat format</u></color>");
        createConfigKey("Gui.Edit.ItemName.NameTag", "<color:#39ff14><u>Name tag format</u></color>");
        createConfigKey("Gui.Edit.ItemName.TabPriority", "<color:#39ff14><u>Tab priority</u></color>");
        createConfigKey("Gui.Edit.ItemName.Permission", "<color:#39ff14><u>Permission</u></color>");
        createConfigKey("Gui.Edit.ItemName.HideOnSneak", "<color:#39ff14><u>Hide name tag on sneak</u></color>");
        createConfigKey("Gui.Edit.ItemName.Delete", "<color:#ff1439><u>Delete rank</u></color>");
        createConfigKey("Gui.Edit.ItemName.Status.Activated", "<color:#39ff14><u>Activated</u></color>");
        createConfigKey("Gui.Edit.ItemName.Status.Deactivated", "<color:#ff1439><u>Deactivated</u></color>");
        createConfigKey("Gui.Edit.ItemName.PriorityStatus", "<gray>Current priority</gray><dark_gray>:</dark_gray> <color:#39ff14>{priority}</color>");
        createConfigKey("Gui.Edit.ItemName.PermissionStatus", "<gray>Current permission</gray><dark_gray>:</dark_gray> <color:#39ff14>{permission}</color>");
        createConfigKey("Gui.Edit.ItemName.ColoredMessages", "<color:#39ff14><u>Colored Chat Messages</u></color>");
        createConfigKey("Gui.Edit.ItemName.RankPriority", "<color:#39ff14><u>Rank priority</u></color>");
        createConfigKey("Gui.Edit.ItemLore.Tablist", List.of("", "<gray>The Tablist format defines how the rank</gray>", "<gray>should be displayed in the tablist</gray><dark_gray>.</dark_gray>", "", "<gray>Current format</gray><dark_gray>:</dark_gray> <white>{format}</white>", "", "<color:#39ff14>Click</color> <gray>to customize the format</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.Chat", List.of("", "<gray>The chat format determines how</gray>", "<gray>the rank is displayed in the chat</gray><dark_gray>.</dark_gray>", "", "<gray>Current format</gray><dark_gray>:</dark_gray> <white>{format}</white>", "", "<color:#39ff14>Click</color> <gray>to customize the format</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.NameTag", List.of("", "<gray>The name tag format determines how the</gray>", "<gray>rank should be displayed above the player</gray><dark_gray>.</dark_gray>", "", "<gray>Current format</gray><dark_gray>:</dark_gray> <white>{format}</white>", "", "<color:#39ff14>Click</color> <gray>to customize the format</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.TabPriority", List.of("", "<gray>The tab priority determines how the</gray>", "<gray>rank should be sorted in the tab list</gray><dark_gray>.</dark_gray>", "<gray>The higher the number<dark_gray>,</dark_gray> the higher up</gray>", "<gray>the rank is displayed</gray><dark_gray>.</dark_gray>", "<gray>If the value is <color:#39ff14>0</color><dark_gray>,</dark_gray> no sorting is performed</gray><dark_gray>.</dark_gray>", "", "<gray>Current priority</gray><dark_gray>:</dark_gray> <color:#39ff14>{priority}</color>", "", "<color:#39ff14>Click</color> <gray>to to set the tab priority</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.Permission", List.of("", "<gray>The permission determines which</gray>", "<gray>permission a player must have</gray>", "<gray>in order to obtain the rank</gray><dark_gray>.</dark_gray>", "", "<gray>Current permission</gray><dark_gray>:</dark_gray> <color:#39ff14>{permission}</color>", "", "<color:#39ff14>Click</color> <gray>to set the permission</gray><dark_gray>.</dark_gray>", "<color:#39ff14>Shift-Click</color> <gray>to remove the permission</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.HideOnSneak", List.of("", "<gray>Hiding the name tag when sneaking</gray>", "<gray>determines whether the player<dark_gray>'</dark_gray>s name tag</gray>", "<gray>should be hidden when the player sneaks</gray><dark_gray>.</dark_gray>", "", "<gray>Current status</gray><dark_gray>:</dark_gray> <color:#39ff14>{status}</color>"));
        createConfigKey("Gui.Edit.ItemLore.Delete", List.of("", "<gray>You can use this button to delete the rank</gray><dark_gray>.</dark_gray>", "<gray>Use this button with caution as there</gray>", "<gray>is no second confirmation</gray><dark_gray>.</dark_gray>", "", "<color:#ff1439>Shift-click</color> <gray>to delete the rank</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.Status.Activated", List.of("", "<color:#39ff14>Click</color> <gray>to deactivate this feature</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.Status.Deactivated", List.of("", "<color:#39ff14>Click</color> <gray>to activate this feature</gray><dark_gray>.</dark_gray>"));
        createConfigKey("Gui.Edit.ItemLore.ColoredMessages", List.of("", "<gray>Determines whether a player</gray>", "<gray>with this rank is allowed to use</gray>", "<gray>color codes to customize their chat messages</gray><dark_gray>.</dark_gray>", "", "<gray>Current status</gray><dark_gray>:</dark_gray> <color:#39ff14>{status}</color>"));
        createConfigKey("Gui.Edit.ItemLore.RankPriority", List.of("", "<gray>The rank priority is used to manage</gray>", "<gray>which rank a player should have</gray>", "<gray>when there are multiple options</gray><dark_gray>.</dark_gray>", "<gray>A player will use the rank with the highest</gray>", "<gray>for him available rank priority</gray><dark_gray>.</dark_gray>", "", "<gray>Current priority</gray><dark_gray>:</dark_gray> <color:#39ff14>{priority}</color>", "", "<color:#39ff14>Click</color> <gray>to change the rank priority</gray><dark_gray>.</dark_gray>"));

        createConfigKey("Title.ChatInput.Main", "<color:#39ff14><b>Chat input</b></color>");
        createConfigKey("Title.ChatInput.Sub", "<color:#39ff14>{seconds}</color> <gray>seconds left</gray><dark_gray>.</dark_gray>");

        createConfigKey("Placeholder.None", "None");
        createConfigKey("Placeholder.Yes", "Yes");
        createConfigKey("Placeholder.No", "No");

        saveYmlConfig();
    }
}
