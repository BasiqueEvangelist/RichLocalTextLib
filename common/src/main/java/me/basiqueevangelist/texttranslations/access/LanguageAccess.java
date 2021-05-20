package me.basiqueevangelist.texttranslations.access;

import net.minecraft.text.Text;

import java.util.function.BiConsumer;

// This is an extremely janky class that is needed because i don't know how to make an accessor for added fields.
public final class LanguageAccess {
    private LanguageAccess() {

    }

    public static BiConsumer<String, Text> textConsumer;
}
