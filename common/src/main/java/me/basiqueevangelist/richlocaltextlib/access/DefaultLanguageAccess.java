package me.basiqueevangelist.richlocaltextlib.access;

import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

public interface DefaultLanguageAccess {
    @ApiStatus.Internal
    void setTextMap(Map<String, Text> textMap);
}
