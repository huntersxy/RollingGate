package dev.anvilcraft.rg;

import dev.anvilcraft.rg.api.Rule;
import dev.anvilcraft.rg.api.server.RGServerRules;

@RGServerRules(languages = {"zh_cn", "en_us"})
public class RollingGateServerRules {
    @Rule(allowed = {"zh_cn", "en_us"}, categories = RollingGateCategories.BASE)
    public static String language = "zh_cn";
}
