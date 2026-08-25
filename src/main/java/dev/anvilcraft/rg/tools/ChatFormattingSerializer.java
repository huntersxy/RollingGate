package dev.anvilcraft.rg.tools;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
//? if >=26.2
/*import java.util.Locale;*/

public class ChatFormattingSerializer implements JsonSerializer<ChatFormatting>, JsonDeserializer<ChatFormatting> {
    @Override
    public ChatFormatting deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //? if <26.2
        return ChatFormatting.getByName(json.getAsString());
        //? if >=26.2 {
        /*try {
            return ChatFormatting.valueOf(json.getAsString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return null;
        }
         *///?}
    }

    @Override
    public JsonElement serialize(@NotNull ChatFormatting src, Type typeOfSrc, JsonSerializationContext context) {
        //? if <26.2
        return new JsonPrimitive(src.getName());
        //? if >=26.2
        /*return new JsonPrimitive(src.name().toLowerCase(Locale.ROOT));*/
    }
}
