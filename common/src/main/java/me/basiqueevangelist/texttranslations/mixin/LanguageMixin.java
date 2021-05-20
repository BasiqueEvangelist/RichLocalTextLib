package me.basiqueevangelist.texttranslations.mixin;

import com.google.gson.JsonElement;
import me.basiqueevangelist.texttranslations.access.DefaultLanguageAccess;
import me.basiqueevangelist.texttranslations.access.LanguageAccess;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(Language.class)
public class LanguageMixin {
    @Unique private static boolean skipNext;
    @Unique private static Map<String, Text> texts;

    @Inject(method = "create", at = @At("HEAD"))
    private static void initTextBuilder(CallbackInfoReturnable<Language> cir) {
        texts = new HashMap<>();
        LanguageAccess.textConsumer = texts::put;
    }

    @Inject(method = "create", at = @At(value = "RETURN"))
    private static void addTextMap(CallbackInfoReturnable<Language> cir) {
        ((DefaultLanguageAccess) cir.getReturnValue()).setTextMap(texts);
        texts = null;
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;asString(Lcom/google/gson/JsonElement;Ljava/lang/String;)Ljava/lang/String;"))
    private static String skipIfObjectOrArray(JsonElement el, String str, InputStream inputStream, BiConsumer<String, String> entryConsumer) {
        if (!el.isJsonPrimitive()) {
            skipNext = true;

            MutableText text = Text.Serializer.fromJson(el);
            LanguageAccess.textConsumer.accept(str, text);

            return "";
        } else {
            skipNext = false;
            return JsonHelper.asString(el, str);
        }
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"))
    private static void doSkip(BiConsumer<Object, Object> biConsumer, Object t, Object u) {
        if (!skipNext)
            biConsumer.accept(t, u);
    }

}
