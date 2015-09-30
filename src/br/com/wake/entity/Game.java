package br.com.wake.entity;

import java.util.List;



/**
 * @author filipemachado
 *
 */
public class Game {
	
	private String name;
	private List<Player> players;
	private int totalKills;
	private List<MeansDeath> listMeansDeath;
	
	
	
	public Game() {
		
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}



	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}



	/**
	 * @return the totalKills
	 */
	public int getTotalKills() {
		return totalKills;
	}



	/**
	 * @param totalKills the totalKills to set
	 */
	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
	}



	/**
	 * @return the listMeansDeath
	 */
	public List<MeansDeath> getListMeansDeath() {
		return listMeansDeath;
	}



	/**
	 * @param listMeansDeath the listMeansDeath to set
	 */
	public void setListMeansDeath(List<MeansDeath> listMeansDeath) {
		this.listMeansDeath = listMeansDeath;
	}
	
	
	
	
	
	
	

}
