package dev.anvilcraft.rg.api.client;

import dev.anvilcraft.rg.RollingGate;

public @interface RGClientRules {
    String value() default RollingGate.MODID;
}
