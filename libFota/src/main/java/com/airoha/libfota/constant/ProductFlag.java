package com.airoha.libfota.constant;

/**
 * Created by MTK60279 on 2018/1/11.
 */

public class ProductFlag {
    public static boolean isBuildFor161X() {
        return BuildFor161X;
    }

    public static void setBuildFor161X(boolean buildFor161X) {
        BuildFor161X = buildFor161X;
    }

    public static boolean BuildFor161X = false;
}
