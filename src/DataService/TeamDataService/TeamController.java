package DataService.TeamDataService;

import java.io.File;
import java.util.ArrayList;

import DataService.FileHelper.FileHelper;
import Enum.ResultMessage;
import Enum.Zone;
import MySqlTest.TeamDataToPO;
import PO.TeamListPO;
import PO.TeamPO;

public class TeamController implements TeamDataService{
	
	public static TeamListPO teams ;
	TeamDataToPO ttp  ;
	
	public TeamController(String fileAddress){
		teams = new TeamListPO();
//		read(fileAddress+"/teams/teams") ;
		ttp = new TeamDataToPO() ;
		ttp.init();
		ttp.read();
		teams = ttp.getAllTeams() ;
	}
	
	public void read(String fileName){
		File file = new File(fileName) ;
		ArrayList<String> tempString = FileHelper.readByLine(file) ;
		for(int i = 1;i<tempString.size()-1 ;i++){
			TeamPO newTeam = new TeamPO() ;
			ArrayList<String> data = FileHelper.analysisOfOneLine(tempString.get(i)) ;
			newTeam.setFullName(data.get(0));
			newTeam.setShortName(data.get(1));
			newTeam.setCity(data.get(2));
			if(data.get(3).equals("E")){
				newTeam.setZone(Zone.E);
			}
			if(data.get(3).equals("W")){
				newTeam.setZone(Zone.W);
			}
			newTeam.setDistrict(data.get(4));
			newTeam.setHomeCourt(data.get(5));
			newTeam.setTimeOfEstablishment(Integer.parseInt(data.get(6)));
			newTeam.setTeamLogoURL("Data/teams/"+newTeam.getShortName()+".png");
			
			addTeam(newTeam);
		}
	}
	@Override
	public ResultMessage addTeam(TeamPO oneTeam) {
		// TODO Auto-generated method stub
		return teams.addTeam(oneTeam) ;
	}

	@Override
	public TeamPO findTeamByName(String name) {
		// TODO Auto-generated method stub
		return teams.findTeamByShortName(name);
	}

	@Override
	public ResultMessage updateTeam(TeamPO oneTeam) {
		// TODO Auto-generated method stub
		return teams.updateTeam(oneTeam);
	}

	@Override
	public void calculateFinalData() {
		// TODO Auto-generated method stub
		for(TeamPO oneTeam:teams.getAllTeams()){
			oneTeam.calculateTeamDataInOneSeason();
		}
	}

	@Override
	public ArrayList<TeamPO> getAllTeams() {
		// TODO Auto-generated method stub
		return teams.getAllTeams() ;
	}
	
}
