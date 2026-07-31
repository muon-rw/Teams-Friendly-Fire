package dev.muon.teamsfriendlyfire.config;

import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.ConfigUtil;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.muon.teamsfriendlyfire.TeamsFriendlyFire;

public final class ConfigTFF {
    private static final SNBTConfig CONFIG = SNBTConfig.create(TeamsFriendlyFire.MOD_ID);

    public static final BooleanValue REQUIRE_MUTUAL_ALLIES = CONFIG
            .addBoolean("require_mutual_allies", false)
            .comment("When true, ally PvP protection only applies if each team has added the other player as an ally.",
                    "Prevents a team from unilaterally blocking PvP by allying an enemy.");

    public static void load() {
        ConfigUtil.loadDefaulted(CONFIG, ConfigUtil.CONFIG_DIR, TeamsFriendlyFire.MOD_ID);
    }

    private ConfigTFF() {}
}
