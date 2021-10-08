package me.basiqueevangelist.richlocaltextlib.mixin;

import me.basiqueevangelist.richlocaltextlib.InsertingText;
import me.basiqueevangelist.richlocaltextlib.access.TextLanguage;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(TranslatableText.class)
public class TranslatableTextMixin {
    @Unique private static final Logger LOGGER = LogManager.getLogger("RichLocalTextLib");

    @Shadow @Final private String key;

    @Shadow @Final private List<StringVisitable> translations;

    @Inject(method = {"visitSelf(Lnet/minecraft/text/StringVisitable$Visitor;)Ljava/util/Optional;", "visitSelf(Lnet/minecraft/text/StringVisitable$StyledVisitor;Lnet/minecraft/text/Style;)Ljava/util/Optional;"}, at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"), cancellable = true)
    private <T> void enter(CallbackInfoReturnable<Optional<T>> cir) {
        if (!InsertingText.pushTranslatableText((TranslatableText)(Object) this)) {
            LOGGER.warn("Detected reference cycle, replacing with empty");
            cir.setReturnValue(Optional.empty());
        }
    }

    @Inject(method = {"visitSelf(Lnet/minecraft/text/StringVisitable$Visitor;)Ljava/util/Optional;", "visitSelf(Lnet/minecraft/text/StringVisitable$StyledVisitor;Lnet/minecraft/text/Style;)Ljava/util/Optional;"}, at = @At(value = "RETURN"))
    private <T> void exit(CallbackInfoReturnable<Optional<T>> cir) {
        InsertingText.popTranslatableText();
    }

    @Inject(method = "updateTranslations", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Language;get(Ljava/lang/String;)Ljava/lang/String;"), cancellable = true)
    private void pullTranslationText(CallbackInfo ci) {
        Language lang = Language.getInstance();
        if (lang instanceof TextLanguage) {
            Text text = ((TextLanguage) lang).getText(key);

            if (text != null) {
                translations.add(text);
                ci.cancel();
            }
        }
    }
}
