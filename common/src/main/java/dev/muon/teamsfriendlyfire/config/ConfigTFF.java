package dev.muon.teamsfriendlyfire.config;

import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.ConfigUtil;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.muon.teamsfriendlyfire.TeamsFriendlyFire;

import java.nio.file.Path;

public final class ConfigTFF {
    private static final SNBTConfig CONFIG = SNBTConfig.create(TeamsFriendlyFire.MOD_ID);
    private static final String FILENAME = TeamsFriendlyFire.MOD_ID + ".snbt";

    public static final BooleanValue REQUIRE_MUTUAL_ALLIES = CONFIG
            .addBoolean("require_mutual_allies", false)
            .comment("When true, ally PvP protection only applies if each team has added the other player as an ally.",
                    "Prevents a team from unilaterally blocking PvP by allying an enemy.");

    public static void load() {
        Path configPath = ConfigUtil.CONFIG_DIR.resolve(FILENAME).toAbsolutePath();
        Path defaultPath = ConfigUtil.DEFAULT_CONFIG_DIR.resolve(TeamsFriendlyFire.MOD_ID).resolve(FILENAME);
        CONFIG.load(configPath, defaultPath, () -> new String[]{
                "Default config file that will be copied to " + configPath + " if it doesn't exist!",
                "Just copy any values you wish to override in here!"});
    }

    private ConfigTFF() {}
}
