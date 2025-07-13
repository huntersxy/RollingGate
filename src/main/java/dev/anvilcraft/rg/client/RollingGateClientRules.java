package dev.anvilcraft.rg.client;

import dev.anvilcraft.rg.RollingGateCategories;
import dev.anvilcraft.rg.api.RGEnvironment;
import dev.anvilcraft.rg.api.Rule;
import dev.anvilcraft.rg.api.client.RGClientRules;

@RGClientRules
public class RollingGateClientRules {
    @Rule(
        env = RGEnvironment.CLIENT,
        categories = RollingGateCategories.BASE
    )
    public static boolean windowResizable = true;
}
