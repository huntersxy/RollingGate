package dev.anvilcraft.rg.api.server;

import dev.anvilcraft.rg.RollingGate;

public @interface RGServerRules {
    String value() default RollingGate.MODID;

    String[] languages() default {"en_us"};
}
