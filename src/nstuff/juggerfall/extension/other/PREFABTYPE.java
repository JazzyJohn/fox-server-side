package nstuff.juggerfall.extension.other;

/**
 * Created by 804129 on 02.08.14.
 */

public enum PREFABTYPE { SIMPLE(0), EGG(1), BUILDING(2);
    public static PREFABTYPE fromInteger(int x) {
        switch(x) {
            case 0:
                return SIMPLE;
            case 1:
                return EGG;
            case 2:
                return BUILDING;
        }
        return null;
    }

    private final int value;

    private PREFABTYPE(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
