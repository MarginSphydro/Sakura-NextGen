package dev.sakura.client.modules.impl.hud;

import com.google.common.base.Suppliers;
import dev.sakura.client.SakuraClient;
import dev.sakura.client.graphics.renderers.RectRenderer;
import dev.sakura.client.graphics.renderers.TextRenderer;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.HudModule;
import dev.sakura.client.settings.impl.ColorSetting;
import dev.sakura.client.settings.impl.DoubleSetting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.awt.*;
import java.util.function.Supplier;

public class WatermarkHud extends HudModule {

    public static final WatermarkHud INSTANCE = new WatermarkHud();

    private WatermarkHud() {
        super("Watermark Hud", Category.HUD, 0f, 0f, 200f, 28f);
    }

    private final DoubleSetting scale = doubleSetting("Scale", 1.0, 0.5, 2.0, 0.1);
    private final DoubleSetting animSpeed = doubleSetting("Anim Speed", 8.0, 1.0, 20.0, 0.5);

    private final ColorSetting backgroundColor = colorSetting("Background Color", new Color(15, 15, 15, 200));
    private final ColorSetting brandColor = colorSetting("Brand Color", new Color(255, 105, 180, 255));
    private final ColorSetting separatorColor = colorSetting("Separator Color", new Color(255, 255, 255, 100));
    private final ColorSetting accentColor = colorSetting("Accent Color", new Color(255, 105, 180, 255));

    private final Supplier<TextRenderer> textRendererSupplier = Suppliers.memoize(TextRenderer::new);
    private final Supplier<RectRenderer> rectRendererSupplier = Suppliers.memoize(RectRenderer::new);

    private static final float PAD_X = 8.0f;
    private static final float PAD_Y = 5.0f;
    private static final float SEP_GAP = 6.0f;
    private static final float TOP_LINE_H = 1.5f;
    private static final float BOTTOM_BAR_H = 2.5f;

    private float animTimer = 0f;

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (nullCheck()) return;

        TextRenderer textRenderer = textRendererSupplier.get();
        RectRenderer rectRenderer = rectRendererSupplier.get();

        float s = scale.getValue().floatValue();
        float padX = PAD_X * s;
        float padY = PAD_Y * s;
        float sepGap = SEP_GAP * s;
        float topLineH = TOP_LINE_H * s;
        float bottomBarH = BOTTOM_BAR_H * s;

        String fullBrand = "Sakura";
        float frameTime = deltaTracker == null ? 0.05f : deltaTracker.getGameTimeDeltaTicks() / 20.0f;
        animTimer += frameTime;
        String brandText = computeAnimatedBrand(fullBrand, animSpeed.getValue().floatValue());

        // TODO: 改！！！
        String userText = "跨圈皇帝萌萌刻";
        if (userText == null || userText.isEmpty()) {

        }

        int fps = Minecraft.getInstance().getFps();
        String fpsText = "FPS:" + fps;
        String versionText = SakuraClient.VERSION;
        String sep = "|";

        float brandW = textRenderer.getWidth(fullBrand, s);
        float userW = textRenderer.getWidth(userText, s);
        float fpsW = textRenderer.getWidth(fpsText, s);
        float verW = textRenderer.getWidth(versionText, s);
        float sepW = textRenderer.getWidth(sep, s);

        float contentWidth = brandW + sepGap + sepW + sepGap + userW + sepGap + sepW + sepGap + fpsW + sepGap + sepW + sepGap + verW;

        float totalWidth = padX + contentWidth + padX;
        float textH = textRenderer.getHeight(s);
        float barH = padY * 2f + textH;
        float totalHeight = barH + bottomBarH;

        // Background
        rectRenderer.addRect(this.x, this.y, totalWidth, barH, backgroundColor.getValue());

        // Header
        rectRenderer.addRect(this.x, this.y, totalWidth, topLineH, accentColor.getValue());

        float textY = this.y + padY;
        float cursX = this.x + padX;

        textRenderer.addText(brandText, cursX, textY, s, brandColor.getValue());
        cursX += brandW + sepGap;

        textRenderer.addText(sep, cursX, textY, s, separatorColor.getValue());
        cursX += sepW + sepGap;

        textRenderer.addText(userText, cursX, textY, s, new Color(255, 255, 255, 235));
        cursX += userW + sepGap;

        textRenderer.addText(sep, cursX, textY, s, separatorColor.getValue());
        cursX += sepW + sepGap;

        textRenderer.addText(fpsText, cursX, textY, s, new Color(255, 255, 255, 235));
        cursX += fpsW + sepGap;

        textRenderer.addText(sep, cursX, textY, s, separatorColor.getValue());
        cursX += sepW + sepGap;

        textRenderer.addText(versionText, cursX, textY, s, new Color(255, 255, 255, 235));

        rectRenderer.drawAndClear();
        textRenderer.drawAndClear();

        setBounds(totalWidth, totalHeight);
    }

    private String computeAnimatedBrand(String full, float speed) {
        int maxLen = full.length();
        if (maxLen <= 1) return full;

        float stepDuration = 1.0f / speed;
        int totalSteps = maxLen * 2 - 2;
        int step = (int) (animTimer / stepDuration) % totalSteps;

        int visibleChars;
        if (step < maxLen) {
            visibleChars = step + 1;
        } else {
            visibleChars = totalSteps - step + 1;
        }

        return full.substring(0, Math.min(visibleChars, maxLen));
    }

}
