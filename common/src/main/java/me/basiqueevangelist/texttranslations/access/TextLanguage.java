package me.basiqueevangelist.texttranslations.access;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface TextLanguage {
    @Nullable Text getText(String key);
}
