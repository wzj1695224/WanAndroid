package com.zlx.widget.viewpager.animviewpager;


public interface LiquidSwipeLayout {
    public ClipPathProvider clipPathProvider();

    public Float currentRevealPercent();

    public void revealForPercentage(Float percent);
}
