package dev.sakura.client.graphics.text;

import dev.sakura.client.graphics.text.ttf.TtfGlyphAtlas;

public record GlyphDescriptor(
        TtfGlyphAtlas atlas,
        TtfGlyphAtlas.GlyphUV uv,
        int width,
        int height,
        int xOffset,
        int yOffset,
        int advance
) {
}