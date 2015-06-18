package MySqlTest;

import PO.MatchListPO;
import PO.PlayerListPO;
import PO.SeasonListPO;
import PO.TeamListPO;

public class Controller {
	
	PlayerDataToPO ptp  ;
	TeamDataToPO ttp  ;
	MatchDataToPO mtp  ;
	SeasonListPO seasons ;
       
	public static void main(String[] args){
			long begin = System.currentTimeMillis() ;
			
			Controller c = new Controller() ;
			c.insertIntoDatabase("E:/two/software engineering/迭代一/迭代一数据");
			long end = System.currentTimeMillis() ;
			System.out.println("运行时间："+(end-begin));
	}

	/**
	 * 将数据从数据库转为PO
	 */
	public void initDataToPO(){
		long a = System.currentTimeMillis() ;
		seasons = new SeasonListPO() ;
		ptp = new PlayerDataToPO() ;
		ptp.init();
		ptp.read();
		long b = System.currentTimeMillis() ;
		System.out.println("获取player信息"+(b-a));
		
		ttp = new TeamDataToPO() ;
		ttp.init();
		ttp.read();
		a = System.currentTimeMillis() ;
		System.out.println("获取球队信息"+(a-b));
		
		mtp = new MatchDataToPO() ;
		mtp.init();
//		mtp.read();
		b = System.currentTimeMillis() ;
		System.out.println("获取比赛信息："+(b-a));
		
		ttp.calculateFinalData();
		ptp.calculateFinalData(); 
	}
	
	/**
	 *  将源数据插入数据库
	 * @param fileAddress 数据源地址 子目录下含players,teams,matches数据
	 */
	public void insertIntoDatabase(String fileAddress){
		long a = System.currentTimeMillis() ;
		MysqlDemo sql = new MysqlDemo() ;
		sql.read();
		
		
		
		
		PlayerDataController pc = new PlayerDataController() ;
		pc.init();
		pc.read(fileAddress);
		long b = System.currentTimeMillis() ;
		System.out.println("插入球员时间："+(b-a));
		
		TeamDataController tc = new TeamDataController() ;
		tc.init();
		tc.read(fileAddress);
		a = System.currentTimeMillis() ;
		System.out.println("插入球队时间："+(a-b));
		
		MatchDataController mc = new MatchDataController() ;
		mc.init();
		mc.read(fileAddress);
		b = System.currentTimeMillis() ;
		System.out.println("插入比赛时间："+(b-a));
//		
//		long end = System.currentTimeMillis() ;
//		System.out.println("运行时间："+(end-begin));
	}
}
