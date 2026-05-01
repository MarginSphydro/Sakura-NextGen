package dev.sakura.client.assets.holders;

import dev.sakura.client.graphics.SakuraTexture;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TextureCacheHolder {

    public static final TextureCacheHolder INSTANCE = new TextureCacheHolder();

    private TextureCacheHolder() {
    }

    public final Map<Identifier, SakuraTexture> textureCache = new HashMap<>();

    public void clearCache() {
        for (SakuraTexture texture : textureCache.values()) {
            texture.close();
        }
        textureCache.clear();
    }

}
