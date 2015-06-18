package PO;

import java.util.ArrayList;

import Enum.Season;

public class MatchPO {
	
	
	String name;
	
	Season season;
	String date;
	String firstTeam;
	String secondTeam;
	ScoreOfMatchPO finalScore;
	ArrayList<ScoreOfMatchPO> allScore ;
	
	ArrayList<PlayerDataOfOneMatchPO> firstTeam_PlayerData;
	ArrayList<PlayerDataOfOneMatchPO> secondTeam_PlayerData;
	
	TeamDataPO firstTeamData;
	TeamDataPO secondTeamData;
	
	int totalTime ;
	
	public MatchPO(){
		firstTeam_PlayerData = new ArrayList<>() ;
		secondTeam_PlayerData = new ArrayList<>() ;
		firstTeamData = new TeamDataPO() ;
		secondTeamData = new TeamDataPO() ;
	}
	
	public PlayerDataOfOneMatchPO getPlayerDataOfOneMatchByName(String name){
		for(PlayerDataOfOneMatchPO oneData : firstTeam_PlayerData)
			if(oneData.getName().equals(name))
				return oneData;
		
		for(PlayerDataOfOneMatchPO oneData : secondTeam_PlayerData)
			if(oneData.getName().equals(name))
				return oneData;
		
		return null;
	}
	
	public void addDataOfOnePlayerOfFirstTeam(PlayerDataOfOneMatchPO onePlayer){  
		firstTeam_PlayerData.add(onePlayer) ;
	}
	public void addDataOfOnePlayerOfSecondTeam(PlayerDataOfOneMatchPO onePlayer){  
		secondTeam_PlayerData.add(onePlayer) ;
	}
	
	public void calculateTeamData(){
		firstTeamData.calculateTeamDataOfOneMatch(firstTeam_PlayerData);
		secondTeamData.calculateTeamDataOfOneMatch(secondTeam_PlayerData);
		firstTeamData.calculateTeamDataOfOneMatchUsingTheOther(secondTeamData);
		secondTeamData.calculateTeamDataOfOneMatchUsingTheOther(firstTeamData);
		
	}
	public void calculatePlayersData(){
		for(PlayerDataOfOneMatchPO onePlayer:firstTeam_PlayerData){
			onePlayer.calculatePlayerData(totalTime, firstTeamData, secondTeamData,firstTeam_PlayerData);
		}
		for(PlayerDataOfOneMatchPO onePlayer:secondTeam_PlayerData){
			onePlayer.calculatePlayerData(totalTime, secondTeamData, firstTeamData,secondTeam_PlayerData);
		}
	}
	
	public TeamDataPO getOneTeamDataByName(String name){
		if(firstTeam.equals(name)){
			return firstTeamData ;
		}
		if(secondTeam.equals(name)){
			return secondTeamData ;
		}
		return null;
	}
	
	public void calculateTotalTime(){
		totalTime = (48+(allScore.size()-4)*5)*60 ;
	}
	
	public void updateOtherTeamDataForPlayers(){
		for(PlayerDataOfOneMatchPO onePlayer: firstTeam_PlayerData ){
			PlayerPO thePlayer = PlayerListPO.findPlayerAccurately(onePlayer.getName()) ;
			thePlayer.updateDataOfOtherData(this.totalTime, this.secondTeamData,this.season);
		}
		for(PlayerDataOfOneMatchPO onePlayer: secondTeam_PlayerData ){
			PlayerPO thePlayer = PlayerListPO.findPlayerAccurately(onePlayer.getName()) ;
			thePlayer.updateDataOfOtherData(this.totalTime, this.firstTeamData,this.season);
		}
	}
	public String getNameOfWinner(){
		if(finalScore.getFirstScore()>finalScore.getSecondScore())
			return firstTeam;
		else
			return secondTeam;
	}
	
	public TeamDataPO getTeamDataByName(String shortName){
		if(shortName.equals(firstTeam))
			return firstTeamData;
		else 
			return secondTeamData;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFirstTeam() {
		return firstTeam;
	}
	public void setFirstTeam(String firstTeam) {
		this.firstTeam = firstTeam;
	}
	public String getSecondTeam() {
		return secondTeam;
	}
	public void setSecondTeam(String secondTeam) {
		this.secondTeam = secondTeam;
	}
	public ScoreOfMatchPO getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(ScoreOfMatchPO finalScore) {
		this.finalScore = finalScore;
	}
	public ArrayList<ScoreOfMatchPO> getAllScore() {
		return allScore;
	}
	public void setAllScore(ArrayList<ScoreOfMatchPO> allScore) {
		this.allScore = allScore;
	}

	public ArrayList<PlayerDataOfOneMatchPO> getFirstTeam_PlayerData() {
		return firstTeam_PlayerData;
	}

	public void setFirstTeam_PlayerData(
			ArrayList<PlayerDataOfOneMatchPO> firstTeam_PlayerData) {
		this.firstTeam_PlayerData = firstTeam_PlayerData;
	}

	public ArrayList<PlayerDataOfOneMatchPO> getSecondTeam_PlayerData() {
		return secondTeam_PlayerData;
	}

	public void setSecondTeam_PlayerData(
			ArrayList<PlayerDataOfOneMatchPO> secondTeam_PlayerData) {
		this.secondTeam_PlayerData = secondTeam_PlayerData;
	}

	public TeamDataPO getFirstTeamData() {
		return firstTeamData;
	}

	public void setFirstTeamData(TeamDataPO firstTeamData) {
		this.firstTeamData = firstTeamData;
	}

	public TeamDataPO getSecondTeamData() {
		return secondTeamData;
	}

	public void setSecondTeamData(TeamDataPO secondTeamData) {
		this.secondTeamData = secondTeamData;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	

	
	
}
