package nstuff.juggerfall.extension;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

import nstuff.juggerfall.extension.gamerule.PVPGameRule;
import nstuff.juggerfall.extension.handlers.*;
import nstuff.juggerfall.extension.models.GameSettingModel;
import nstuff.juggerfall.extension.tournament.TournamentModel;
/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class ZoneExtension extends AbstractExtension {

    public static final String CONTER_IDLE_REQUEST ="conterIdle";

    

    private ScheduledFuture<?> tcFuture;
    private ScheduledFuture<?> tuFuture;
    
    
    
    private Map<String, TournamentModel> tournaments = new ConcurrentHashMap<String, TournamentModel>();
        
    
    @Override
    public void init() {

        trace(ExtensionLogLevel.INFO,"ZoneExtension is Up");


        addEventHandler(SFSEventType.ROOM_ADDED, BaseAddRoomHandler.class);
        addEventHandler(SFSEventType.USER_LOGIN, LoginHandler.class);
        addRequestHandler(CONTER_IDLE_REQUEST,ConterIdleHandler.class);
        addEventHandler(SFSEventType.ROOM_REMOVED, RoomRemovedHandler.class);
       // addEventHandler(SFSEventType.USER_DISCONNECT, UserDisconnectZoneHandler.class);
        
        SmartFoxServer sfs = SmartFoxServer.getInstance();
        
               
        tcFuture = sfs.getTaskScheduler().scheduleAtFixedRate(new TournamentChecker(), 0, 30, TimeUnit.SECONDS);
        tuFuture = sfs.getTaskScheduler().scheduleAtFixedRate(new TournamentUpdater(), 0, 5, TimeUnit.SECONDS);
    }
    
    @Override
    public void destroy() {        
        tcFuture.cancel(true);        
        tuFuture.cancel(true);
        super.destroy();
    }
    
    private class TournamentUpdater implements Runnable{
    	
    	public void run()
        {
    		try{
    			synchronized (tournaments) {
    				for(TournamentModel tm : tournaments.values()){
    					tm.Update();
    				}
				}				
    		}catch(Exception e){
    			trace(ExtensionLogLevel.ERROR, "Tournament Update Exception  ", e, e.getStackTrace().toString());
    		}
    		
        }
    }
    
    
    private class TournamentChecker implements Runnable
    {    	
    	public void run()
        {
            try {
            	final String app_id = "9c543f25063cb80ff1241154";
            	final String api_key = "tuuy8h_dfVheRO80T0AR";
            	
            	String jsonResponce = GetTournamentsWithStatus(app_id, api_key, 6);            	
            	trace(ExtensionLogLevel.INFO, "Started Tournaments JSON Responce :  " + jsonResponce);
            	String jsonResponce1 = GetTournamentsByDefault(app_id, api_key);            	
            	trace(ExtensionLogLevel.INFO, "Tournaments JSON Responce :  " + jsonResponce1);
            	
            	
            	List<TournamentModel> tournList = ParseTournamentsResponce(jsonResponce);
            	tournList.addAll( ParseTournamentsResponce(jsonResponce1) );    			
    			
    			
    			synchronized (tournaments) {			
    			
	    			//Check created tournamemts, and create rooms
	    			for(TournamentModel tm : tournList){
	    				TournamentModel contains = tournaments.get(tm.id);
	    				if(contains != null){
	    					contains.UpdateMatchTime(tm);
	    					if(contains.date_games_start != null)trace(ExtensionLogLevel.INFO,"Tournament "+contains.id+" Started At "+tm.date_games_start);
	    				}else {
	    					Room room = CreateTournamentRoom(tm.title+"_"+tm.id,tm.id, (int)tm.max_players);
	        				tm.setRoom(room);
	        				tournaments.put(tm.id, tm);
						}
	    				    				
	    			}	    			
	    			
	    			//найти id которых нету в полученном списке иу далить комнаты которые не существуют    			
	    			List<String> idsToRemove = new ArrayList<String>();
	    			for (String key : tournaments.keySet()) {
						if(ContainsId(key, tournList)) continue;
						
						idsToRemove.add(key);					
					}
	    			
	    			for (String key : idsToRemove) {
						TournamentModel tm = tournaments.get(key);
						getApi().removeRoom(tm.getRoom());					
					}    			    			
    			
    			}
    			    			
            }catch(Exception e){
            	trace(ExtensionLogLevel.ERROR, "Tournament Request Exception  ", e, e.getStackTrace().toString());
            	//e.printStackTrace();
            }
        }
    
    }
    
    private static boolean ContainsId(String id,List<TournamentModel> models){
    	for(TournamentModel tm : models){
    		if(id.equals(tm.id)) return true;
    	}
    	return false;
    }
    
    public Room CreateTournamentRoom(String roomName, String tournamentId, int maxPlayers) throws SFSCreateRoomException{
    	CreateRoomSettings settings = new CreateRoomSettings();
	    settings.setName(roomName);	    
	    settings.setGroupId("games");
	    settings.setGame(true);	    
	    settings.setMaxSpectators(0);    		    
	    settings.setMaxUsers(maxPlayers);    		    
	    settings.setExtension(new RoomExtensionSettings("SFJugger", MainExtension.class.getName()));	    
	    settings.setAutoRemoveMode(SFSRoomRemoveMode.NEVER_REMOVE);
	    settings.setHidden(false);
	    settings.setMaxVariablesAllowed(15);
	    
	    List<RoomVariable> roomVars = new ArrayList<RoomVariable>();
	    	   
	    roomVars.add(new SFSRoomVariable("map", "urban_map_RR3_fix",false,false,true));	    
	    roomVars.add(new SFSRoomVariable("ruleClass", PVPGameRule.class.getName(),false,false,true));
	    roomVars.add(new SFSRoomVariable("visible", true,false,false,true));
	    roomVars.add(new SFSRoomVariable("isTournament", true,false,false,true));
	    roomVars.add(new SFSRoomVariable("tournamentId", tournamentId,false,false,true));
	    	    
	    
	    GameSettingModel gSettings = new GameSettingModel();
	    gSettings.isWithPractice = false;
	    gSettings.teamCount = 2;
	    gSettings.maxTime =0;
	    gSettings.maxScore= 35;
	    
	    SFSObject data = new SFSObject();	    
		data.putClass("gameSetting",gSettings);
	    roomVars.add(new SFSRoomVariable("gameVar", data, false,false,true));
	    settings.setRoomVariables(roomVars);
	    
	    return getApi().createRoom(getParentZone(), settings, null,false,null,false,false);
	    //return getApi().createRoom(getParentZone(), settings, null);
    }
    
    
    public List<TournamentModel> ParseTournamentsResponce(String jsonResponce) throws ParseException{
    	//Parse JSON String
    	JSONParser parser = new JSONParser();    			    						
		Object jsonData = parser.parse(jsonResponce);    			
		JSONArray jsonArray = (JSONArray)jsonData;
		
		List<TournamentModel> tournList = new ArrayList<TournamentModel>();
		
		for(Object obj : jsonArray){
			TournamentModel tm = new TournamentModel((JSONObject)obj, this);    				
			tournList.add( tm );    				
		}
		
		return tournList;
    }
    
    public static String GetTournamentsWithStatus(String app_id, String api_key, Integer status) throws Exception{
    	final String sign = SHA256(app_id+status.toString()+api_key);
    	final String url = "https://arena4games.com/game_api/v1/app/"+app_id+"/tournaments?status="+status.toString()+"&sign="+sign;
    	
    	return SendGetHTTPS(url); 
    	
    }
    
    public static String GetTournamentsByDefault(String app_id, String api_key) throws Exception{    	
    	final String sign = SHA256(app_id+api_key);
    	final String url = "https://arena4games.com/game_api/v1/app/"+app_id+"/tournaments?sign="+sign;
    	
    	return SendGetHTTPS(url); 
    }
    
    public static String SendGetHTTPS(final String url) throws Exception {				
 		 				
    	HttpsURLConnection con = null;
    	BufferedReader in = null;
    	
    	try{
	 		URL obj = new URL(url);
	 		con = (HttpsURLConnection) obj.openConnection(); 
	 		 		
	 		
	 		// optional default is GET 		
	 		con.setRequestMethod("GET");
	 		con.addRequestProperty("Content-Type", "application/json"); 		
	
	 		//int responseCode = con.getResponseCode();
	 		
	 		in = new BufferedReader( new InputStreamReader(con.getInputStream()));
	 		
	 		String inputLine;
	 		StringBuffer response = new StringBuffer();
	
	 		while ((inputLine = in.readLine()) != null) {
	 			response.append(inputLine);
	 		}
	 			
	 		//print result
	 		return response.toString();
	 		
    	}catch (Exception e){
    		throw e;    		
    	}finally{
    		if (in != null) {
    	        try {
    	            in.close();
    	        } catch (Exception e) {
    	        }
    	    }
    	    if (con != null) {
    	        con.disconnect();
    	    }
    	}
 	}
    
    public static String SHA256(String str) throws Exception  
    {
    	MessageDigest md = MessageDigest.getInstance("SHA-256");		
		byte[] bytes = md.digest(str.getBytes("UTF-8"));		
				
		return new BigInteger(1,bytes).toString(16);
    }

}


