package dev.codedsakura.blossom.lib.teleport;

import dev.codedsakura.blossom.lib.BlossomLib;
import dev.codedsakura.blossom.lib.utils.CubicBezierCurve;

public class TeleportConfig {

    public BossBarConfig bossBar;


    public TitleMessageConfig titleMessage;


    public boolean actionBarMessageEnabled = false;


    public CubicBezierCurve fovEffectBefore = new CubicBezierCurve(new double[]{0, 0, 0, 1}, 1, .1, 10);

    public CubicBezierCurve fovEffectAfter = new CubicBezierCurve(new double[]{0, 1, 1, 1}, .1, 1, 10);


    public ParticleAnimation particleAnimation = ParticleAnimation.OFF;


    public boolean allowBack = true;


    public enum ParticleAnimation {
        OFF
    }


    public TeleportConfig(boolean defaults) {
        bossBar = defaults ? new BossBarConfig(true) : null;
        titleMessage = defaults ? new TitleMessageConfig(true) : null;
    }

    public TeleportConfig cloneMerge() {
        TeleportConfig defaultConf = BlossomLib.CONFIG.baseTeleportation;

        TeleportConfig newOne = new TeleportConfig(false);

        newOne.bossBar = this.bossBar == null ? defaultConf.bossBar : defaultConf.bossBar.cloneMerge(this.bossBar);
        newOne.titleMessage = this.titleMessage == null ? defaultConf.titleMessage : defaultConf.titleMessage.cloneMerge(this.titleMessage);

        newOne.allowBack = this.allowBack && defaultConf.allowBack;

        return newOne;
    }
}
