package dev.sakura.client.utils.player;

import dev.sakura.client.modules.impl.ClientSetting;
import dev.sakura.client.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class ChatUtils {

    private static final Minecraft mc = Minecraft.getInstance();

    public static final String PREFIX = "[Sakura] ";

    private static final double GRADIENT_CHAR_STEP = 0.55D;

    public static void addChatMessage(String message) {
        addChatMessage(true, message);
    }

    public static void addChatMessage(boolean prefix, String message) {
        mc.gui.getChat().addClientSystemMessage(buildClientMessage(prefix, message));
    }

    public static Component buildClientMessage(boolean prefix, String message) {
        MutableComponent component = Component.empty();
        if (prefix) {
            component.append(Component.literal(PREFIX));
        }
        return component.append(Component.literal(message));
    }

    public static FormattedCharSequence applyAnimatedPrefix(FormattedCharSequence original) {
        if (!ClientSetting.INSTANCE.shouldAnimateChatPrefix()) {
            return original;
        }

        String rawLine = toPlainString(original);
        if (!rawLine.startsWith(PREFIX)) {
            return original;
        }

        MutableComponent gradientLine = Component.empty();
        double animationTime = System.currentTimeMillis() / 180.0 * ClientSetting.INSTANCE.getChatPrefixGradientSpeed();

        int visualIndex = 0;
        for (int offset = 0; offset < PREFIX.length(); ) {
            int codePoint = PREFIX.codePointAt(offset);
            String character = new String(Character.toChars(codePoint));
            float blend = (float) ((Math.sin(animationTime - visualIndex * GRADIENT_CHAR_STEP) + 1.0D) * 0.5D);
            int color = ColorUtils.interpolateColor(ClientSetting.INSTANCE.getChatPrefixColorStart(), ClientSetting.INSTANCE.getChatPrefixColorEnd(), blend).getRGB() & 0xFFFFFF;

            gradientLine.append(Component.literal(character).withStyle(Style.EMPTY.withColor(color)));
            offset += Character.charCount(codePoint);
            visualIndex++;
        }

        gradientLine.append(Component.literal(rawLine.substring(PREFIX.length())));
        return gradientLine.getVisualOrderText();
    }

    private static String toPlainString(FormattedCharSequence sequence) {
        StringBuilder builder = new StringBuilder();
        sequence.accept((index, style, codePoint) -> {
            builder.appendCodePoint(codePoint);
            return true;
        });
        return builder.toString();
    }

}
