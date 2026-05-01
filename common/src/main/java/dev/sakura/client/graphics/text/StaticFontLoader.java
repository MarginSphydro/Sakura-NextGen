package dev.sakura.client.graphics.text;

import dev.sakura.client.assets.resources.ResourceLocationUtils;
import dev.sakura.client.graphics.text.ttf.TtfFontLoader;

public class StaticFontLoader {

    public static final TtfFontLoader DEFAULT = new TtfFontLoader(ResourceLocationUtils.getIdentifier("fonts/font.ttf"));

    public static final TtfFontLoader DUCKSANS = new TtfFontLoader(ResourceLocationUtils.getIdentifier("fonts/ducksans.ttf"));

    public static final TtfFontLoader ICONS = new TtfFontLoader(ResourceLocationUtils.getIdentifier("fonts/icon.ttf"));

}
