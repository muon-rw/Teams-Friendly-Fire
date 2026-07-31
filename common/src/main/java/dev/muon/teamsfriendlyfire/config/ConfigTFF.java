package dev.muon.teamsfriendlyfire.config;

import dev.ftb.mods.ftblibrary.config.manager.ConfigManager;
import dev.ftb.mods.ftblibrary.config.value.BooleanValue;
import dev.ftb.mods.ftblibrary.config.value.Config;
import dev.muon.teamsfriendlyfire.TeamsFriendlyFire;

public interface ConfigTFF {
    String KEY = TeamsFriendlyFire.MOD_ID + "-server";

    Config CONFIG = Config.create(KEY).standardTopLevelComment("Teams Friendly Fire", KEY, false);

    BooleanValue REQUIRE_MUTUAL_ALLIES = CONFIG.addBoolean("require_mutual_allies", false)
            .comment("When true, ally PvP protection only applies if each team has added the other player as an ally.",
                    "Prevents a team from unilaterally blocking PvP by allying an enemy.");

    static void register() {
        ConfigManager.getInstance().registerServerConfig(CONFIG, TeamsFriendlyFire.MOD_ID + ".config.server", true);
    }
}
