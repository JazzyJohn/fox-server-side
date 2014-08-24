package nstuff.juggerfall.extension.other;

/**
 * Created by 804129 on 02.08.14.
 */

public enum PREFABTYPE { SIMPLE, EGG;
    public static PREFABTYPE fromInteger(int x) {
        switch(x) {
            case 0:
                return SIMPLE;
            case 1:
                return EGG;
        }
        return null;
    }


}
