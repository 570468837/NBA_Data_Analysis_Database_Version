package MySqlTest;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import Enum.Zone;
import PO.TeamDataPO;
import PO.TeamListPO;
import PO.TeamPO;

public class TeamDataToPO {

	String url = "jdbc:mysql://localhost:3306/NBA_DATA?user=root&password=941104&useUnicode=true&characterEncoding=UTF8" ;
	java.sql.Connection con = null ;
	java.sql.PreparedStatement stmt = null ;
	TeamListPO teams  ;
	String query = "select * from teams " ;
	
	public TeamDataToPO(){
		teams = new TeamListPO() ;
	}
	
	public TeamListPO getAllTeams(){
		return teams ;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long beginTime = System.currentTimeMillis() ;
		TeamDataToPO tp = new TeamDataToPO() ;
		tp.init();
		tp.read();
		System.out.println(TeamListPO.allTeams.size());
		long endTime = System.currentTimeMillis() ;
		System.out.println("运行时间："+(endTime-beginTime));
	}

	public void init(){
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			System.out.println("加载MySQL驱动");
			
			con = DriverManager.getConnection(url) ;
			System.out.println("链接数据库");
			
			stmt = con.prepareStatement(query) ;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("加载驱动失败");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("链接数据库失败");
			e.printStackTrace();
		}
		
	}
	
	public void read(){
//		int id = 0 ;
		
		try {
//			stmt.setString(1, String.valueOf(id));
			ResultSet rs = stmt.executeQuery() ;
			
			while(rs.next()){
				TeamPO oneTeam = new TeamPO() ;
				oneTeam.setFullName(rs.getString(2));
				oneTeam.setShortName(rs.getString(3));
				oneTeam.setCity(rs.getString(4));
				oneTeam.setZone(getZone(rs.getString(5)));
				oneTeam.setDistrict(rs.getString(6));
				oneTeam.setHomeCourt(rs.getString(7));
				oneTeam.setTimeOfEstablishment(getETime(rs.getString(8)));
				oneTeam.setTeamLogoURL("Data/teams/"+oneTeam.getShortName()+".png");
				teams.addTeam(oneTeam) ;
//				id++ ;
//				stmt.setString(1, String.valueOf(id));
//				rs = stmt.executeQuery() ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Zone getZone(String zone){
		if(zone.equals("E"))
			return Zone.E ;
		else
			return Zone.W ;
	}
	private int getETime(String time){
		return Integer.parseInt(time.substring(0,4)) ;
	}
	public void calculateFinalData() {
		// TODO Auto-generated method stub
		for(TeamPO oneTeam:teams.getAllTeams()){
			oneTeam.calculateTeamDataInOneSeason();
		}
	}
}
