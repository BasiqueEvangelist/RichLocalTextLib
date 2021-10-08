package me.basiqueevangelist.richlocaltextlib;

import net.minecraft.text.BaseText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class InsertingText extends BaseText {
    private static final ThreadLocal<Deque<TranslatableText>> translationStack = ThreadLocal.withInitial(ArrayDeque::new);

    private final int index;

    public InsertingText(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @ApiStatus.Internal
    public static boolean pushTranslatableText(TranslatableText text) {
        for (TranslatableText other : translationStack.get()) {
            if (other == text) {
                return false;
            }
        }

        translationStack.get().push(text);

        return true;
    }

    @ApiStatus.Internal
    public static void popTranslatableText() {
        translationStack.get().pop();
    }

    @Override
    public String asString() {
        if (translationStack.get().peek() == null)
            return "%" + (index + 1) + "$s";

        Object arg = translationStack.get().peek().getArgs()[index];
        if (arg instanceof Text) {
            return ((Text) arg).asString();
        } else {
            return arg.toString();
        }
    }

    @Override
    public <T> Optional<T> visitSelf(Visitor<T> visitor) {
        if (translationStack.get().peek() == null)
            return visitor.accept("%" + (index + 1) + "$s");

        Object arg = translationStack.get().peek().getArgs()[index];
        if (arg instanceof Text) {
            return ((Text) arg).visit(visitor);
        } else {
            return visitor.accept(arg.toString());
        }
    }

    @Override
    public <T> Optional<T> visitSelf(StyledVisitor<T> visitor, Style style) {
        if (translationStack.get().peek() == null)
            return visitor.accept(style, "%" + (index + 1) + "$s");

        Object arg = translationStack.get().peek().getArgs()[index];
        if (arg instanceof Text) {
            return ((Text) arg).visit(visitor, style);
        } else {
            return visitor.accept(style, arg.toString());
        }
    }

    @Override
    public BaseText copy() {
        return new InsertingText(index);
    }
}
