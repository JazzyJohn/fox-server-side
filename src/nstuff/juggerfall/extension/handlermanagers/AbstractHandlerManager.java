package nstuff.juggerfall.extension.handlermanagers;

import com.sun.org.apache.xml.internal.security.Init;
import nstuff.juggerfall.extension.MainExtension;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public abstract class AbstractHandlerManager {

    public MainExtension extension;

    public abstract void Init();
}
