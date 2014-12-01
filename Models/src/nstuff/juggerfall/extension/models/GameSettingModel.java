package nstuff.juggerfall.extension.models;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import java.util.Map;

/**
 * Created by Ivan.Ochincenko on 22.08.14.
 */
public class GameSettingModel implements SerializableSFSType {
    public int teamCount;

    public int maxTime;

    public int maxScore;

    public Map<String, Object> huntTable;

    public boolean isWithPractice;


}
