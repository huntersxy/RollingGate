package dev.anvilcraft.rg.api.event;

import dev.anvilcraft.rg.api.RGRule;
import lombok.Getter;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

@Getter
public class RGValidatorNotPassedEvent<T> extends Event implements IModBusEvent {
    /**
     * 发生变更的规则。
     */
    private final RGRule<T> rule;
    /**
     * 规则的旧值。
     */
    private final T oldValue;

    public RGValidatorNotPassedEvent(RGRule<T> rule, T oldValue) {
        this.rule = rule;
        this.oldValue = oldValue;
    }
}
