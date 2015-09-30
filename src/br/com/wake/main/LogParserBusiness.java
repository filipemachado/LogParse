package br.com.wake.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import br.com.wake.entity.Game;
import br.com.wake.entity.MeansDeath;
import br.com.wake.entity.MeansDeathEnum;
import br.com.wake.entity.Player;


/**
 * Classe utilizada para gerar os reports e processar o arquivo de log.
 */

 public class LogParserBusiness {
	
	 private String TAG_GAME = "InitGame:";
	 private String TAG_PLAYER = "ClientUserinfoChanged:";
	 private String TAG_KILL = "Kill:";
	 
    
	 
	 /**
   	 * Task1
   	 * Gera o relatorio de Log     
   	 * 
     * @param File inputGameLog - caminho onde estará o arquivo de log.
     * 
     * @throws SecurityException
     * @throws IOException
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
       
   	 @SuppressWarnings("unchecked")
	public void reportLogParse(File inputGameLog) throws SecurityException, IOException {
   		 	
   		 
   		 List<Game> games = new ArrayList<Game>();
            games = LogParse(inputGameLog);
             
            JSONObject jsonGame = new JSONObject(); 
            JSONObject jsonReport = null; 
            JSONObject jsonKills = null; 
            
            
          for(Game g: games){  
       	   JSONArray players = new JSONArray();
   	       jsonReport = new JSONObject(); 
   	       jsonKills = new JSONObject(); 
   	       
   	       for(Player p: g.getPlayers()){
   	    	   players.add(p.getName());
   	    	   jsonKills.put(p.getName(), p.getKills());	
   	       }
   	       
   	       jsonReport.put(g.getName(),jsonGame);
   	       jsonGame.put("total_kills", g.getTotalKills());
   	       
   	       
   	       jsonGame.put("kills",jsonKills);
   	       jsonGame.put("player", players);	       
   	       
   	       System.out.println(jsonReport);
          }
          
            
   		 
   	 }
    
	/**
	 * Task2
	 * Gera o relatorio Means of Death     
	 * 
     * @param  File inputGameLog - caminho onde estará o arquivo de log. 
     * 
     * @throws SecurityException
     * @throws IOException
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */		
    
    @SuppressWarnings("unchecked")
	public void reportMeansDeath(File inputGameLog) throws SecurityException, IOException {
    	
    	List<Game> games = new ArrayList<Game>();
    	
    	games = LogParse(inputGameLog);
    	
    	JSONObject jsonGame = new JSONObject();
    	JSONObject jsonReport = null; 
        JSONObject jsonKills = null; 
         
        
      for(Game g: games){  
   	 
	       
	       jsonReport = new JSONObject(); 
	       jsonKills = new JSONObject(); 
	       for(MeansDeath md: g.getListMeansDeath()){
	    	   jsonKills.put(md.getMeansDeath(), md.getTotalMeansDeath());	
	       }
	            
	       jsonGame.put("kill_by_means",jsonKills);
	       jsonReport.put(g.getName(),jsonGame);
	       
	              
	       
	       System.out.println(jsonReport);
      }
    	
    }
    
    /**
   	 * Cria uma Lista de Objetos games de acordo com o LOG
   	 * 
     * @param File inputGameLog - caminho onde estará o arquivo de log.
     * @return List<Game> games- Lista de Objetos de games
     * 
     * @throws SecurityException
     * @throws IOException
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
    	 
	 public List<Game> LogParse(File inputGameLog) throws SecurityException, IOException {
		 int indexGame = -1;
		 int indexPlayer = -1;
		 int indexMeansDeath = -1;
		 Game game = null; 
		 
		 List<Game> games = new ArrayList<Game>();
		 BufferedReader readLog = genericValidPathFile(inputGameLog);
		 String line = readLog.readLine();
         
		 while (line != null) {
        	  
        	 if(isTag(line, TAG_GAME)){//NOVO OBJETO GAME
        		 indexGame++;
        		 game = new Game();
        		 game.setName("game_"+ indexGame);
        		 game.setPlayers(new ArrayList<Player>());
        		 game.setTotalKills(0);
        		 games.add(indexGame, game);
        		 indexPlayer = -1;
        		 indexMeansDeath = -1;
        		 
        		 game.setListMeansDeath(new ArrayList<MeansDeath>());
        	 }
        	 
        	 if(isTag(line, TAG_PLAYER)){//NOVA LISTA DE PLAYERS REFERENTE AO GAME
        		  boolean existPlayer = false;
        		 for(Player playerEquals: games.get(indexGame).getPlayers()){
        			 if(playerEquals.getName().equals(getPlayer(line))){
        				 existPlayer = true;
        				 break;
        			 }	 
        		 }
        		 if(!existPlayer){
        			 indexPlayer++;
            		 Player player = new Player();
            		 player.setName(getPlayer(line));
            		 player.setKills(0);
            		 player.setIndexPlayer(indexPlayer);
        			 games.get(indexGame).getPlayers().add(indexPlayer, player);
        		 }
        		 	
        	 }
        	 
        	 if(isTag(line, TAG_KILL)){
        		 
        		 /*********** LISTA DE KILLS REFERENTE AO GAME***********/
        		 int refreshKills = games.get(indexGame).getTotalKills() + 1;
        		 games.get(indexGame).setTotalKills(refreshKills);
        		         		 
        		 for(Player player: games.get(indexGame).getPlayers()){
        			 if(player.getName().equals(getKilled(line))){
        				 int refreshKillPlayer = player.getKills() +1;
        				 player.setKills(refreshKillPlayer);
        				 games.get(indexGame).getPlayers().set(player.getIndexPlayer(), player);
        				 break;
        			 }
        		 }
        		 
        		 
        		 /*********** LISTA DE MEANSDEATH REFERENTE AO GAME******/
        		 for(MeansDeathEnum mdEnum: MeansDeathEnum.values()){
        			 if(mdEnum.toString().equals(getMeansDeath(line))){
        				 int totalMeansDeath = 0;
        				 for(MeansDeath md:games.get(indexGame).getListMeansDeath()){
        					 if(md.getMeansDeath().equals(getMeansDeath(line))){
        						 totalMeansDeath = md.getTotalMeansDeath()+1;
        						 indexMeansDeath = md.getIndexMeansDeath();
        						 break;
        					 }	 
        				 }
    				 
        				 MeansDeath gameMeansDeath = new MeansDeath();
        				 
        				 if(totalMeansDeath == 0){
        					 indexMeansDeath++;
        					 gameMeansDeath.setMeansDeath(getMeansDeath(line));
        					 gameMeansDeath.setTotalMeansDeath(1);
        					 games.get(indexGame).getListMeansDeath().add(indexMeansDeath, gameMeansDeath);
        				 }else{
        					 gameMeansDeath.setIndexMeansDeath(indexMeansDeath);
        					 gameMeansDeath.setTotalMeansDeath(totalMeansDeath);
        					 gameMeansDeath.setMeansDeath(getMeansDeath(line));
        					 games.get(indexGame).getListMeansDeath().set(indexMeansDeath, gameMeansDeath);
        				 }	 
        				 	 
        				 
        			 }
        		 }
        	 }
        	 
        	 
        	 line = readLog.readLine();//PROXIMA LINHA
         }
		 
		 return games;
	 }
	 
	 /**
   	 * 
   	 * Valida se o caminho e o arquivo estão validos    
   	 * 
     * @param File inputGameLog - caminho onde estará o arquivo de log.
     * @return BufferReader bufferReader- Linhas do 
     * 
     * @throws SecurityException
     * @throws IOException
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
	 
	private static BufferedReader genericValidPathFile(File file) throws IOException, FileNotFoundException {
		File newFile = null;
	    
		if (file.exists()) {
     		newFile = new File(file.getPath());
     	} else {
     		throw new FileNotFoundException("file " + file + " not found");
     	}
		BufferedReader linesFile = new BufferedReader(new FileReader(newFile));
		
		return linesFile;
	}
	
	/**
   	 * 
   	 * Verifica se na linha passada existe alguma TAG    
   	 * 
     * @param String line - linha do arquivo de log
     * @param String tag - tag a ser verificada na linha
     * @return boolean - Se a tag existir na linha retorna verdadeiro
     * 
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
	
	public boolean isTag(String line, String tag){
		if(line.contains(tag))
			return true;
		else
			return false;
	}
	
	/**
   	 * 
   	 * procura o player na linha   
   	 * 
     * @param String line - linha do arquivo de log
     * @return String - retorna o player
     * 
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
	
	public String getPlayer(String line){
		String strPlayer = line.substring(line.indexOf("\\") + "\\".length());
		strPlayer = strPlayer.substring(0, strPlayer.indexOf("\\"));
		
		return strPlayer;
	}
	/**
   	 * 
   	 * procura o killed na linha   
   	 * 
     * @param String line - linha do arquivo de log
     * @return String - retorna o killed
     * 
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
	
	public String getKilled(String line){
		String killed = line.substring(line.indexOf("killed ") + "killed ".length());
		killed = killed.substring(0, killed.indexOf(" by"));
		
		return killed;
	}
	
	/**
   	 * 
   	 * procura o MeansDeath na linha   
   	 * 
     * @param String line - linha do arquivo de log
     * @return String - retorna o MeansDeath
     * 
     * 
     * @version 1.0
     * @author filipemachado
     * @since 17/09/2015
     */	
	
	public String getMeansDeath(String line){
		String meansDeath = line.substring(line.indexOf("by ") + "by ".length());
		
		return meansDeath;
	}
	
	 
	
}