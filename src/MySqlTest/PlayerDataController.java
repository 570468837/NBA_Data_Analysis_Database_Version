package MySqlTest;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import DataService.FileHelper.FileHelper;
import PO.PlayerListPO;
import PO.PlayerPO;

public class PlayerDataController {

	java.sql.Connection con = null ;
	String url = "jdbc:mysql://localhost:3306/NBA_DATA?"
            + "user=root&password=941104&useUnicode=true&characterEncoding=UTF8";
	java.sql.PreparedStatement stmt = null ;
	int sum;
	 String string = "insert into players(playerID,name,number,position,height,weight,birth,age,exp,school) values(?,?,?,?,?,?,?,?,?,?)";
	public void init(){
		 
		 try {
			Class.forName("com.mysql.jdbc.Driver") ;
			System.out.println("加载MySQL驱动");
			
	    	con = DriverManager.getConnection(url);
	    	System.out.println("链接数据库");
	    	con.setAutoCommit(false);
	    	
	    	stmt = con.prepareStatement(string,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
	    	
	    	java.sql.Statement stmt = con.createStatement() ;
	    	ResultSet rs = stmt.executeQuery("select count(*) from players") ;
	    	if(rs.next())
	    		sum = rs.getInt(1) ;
	    	
	    }
	    catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		long begin = System.currentTimeMillis() ;
		PlayerDataController pl = new PlayerDataController() ;
		pl.init();
		pl.read("E:\\two\\software engineering\\迭代一\\迭代一数据");
		long end = System.currentTimeMillis() ;
		System.out.println("运行时间："+(end-begin));
	}
   
	public void read(String fileName){
		fileName = fileName+"/players/info" ;
		File file=new File(fileName);
		if(file.isDirectory()){
			File[] allFiles=file.listFiles();
			if(allFiles.length == sum)
				return ;
			
			try {
		    	for(int i=0;i<allFiles.length;i++){   
		     		ArrayList<String> tempString=FileHelper.readByLine(allFiles[i]);
			
				    stmt.setInt(1,i);
					stmt.setString(2, checkString(getDataOfOneLine(tempString.get(1))));
					stmt.setInt(3, checkInt(getDataOfOneLine(tempString.get(3))));
					stmt.setString(4, getDataOfOneLine(tempString.get(5)));
					stmt.setString(5, getDataOfOneLine(tempString.get(7)));
					stmt.setInt(6, checkInt(getDataOfOneLine(tempString.get(9))));
					stmt.setString(7, getDataOfOneLine(tempString.get(11)));
					stmt.setInt(8, checkInt(getDataOfOneLine(tempString.get(13))));
					stmt.setInt(9, checkInt(getDataOfOneLine(tempString.get(15))));
					stmt.setString(10, checkString(getDataOfOneLine(tempString.get(17))));
					stmt.addBatch();
		    	}
		    	
		    	stmt.executeBatch() ;
		    	con.commit();
		    	con.close();
		    	
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
				
//				String name = "" ;
//				ArrayList<String> dataOfString = new ArrayList<>() ;
//				ArrayList<Integer> dataOfInteger = new ArrayList<>() ;
//				for(int j = 0;j<tempString.size();j++){
//					if(j==1){
//						name = getDataOfOneLine(tempString.get(j)) ;
//					}
//					if(j%2 ==0)
//						continue ;
//					if(j==3||j==9||j==13||j==15){
//						try{
//							dataOfInteger.add(Integer.parseInt(getDataOfOneLine(tempString.get(j)))) ;
//						}catch(NumberFormatException e){
//							dataOfInteger.add(0) ;
//						}
//					}else
//				    	dataOfString.add("'"+checkString(getDataOfOneLine(tempString.get(j)))+"'") ;
//				}
//				dataOfString.add("'Data/players/portrait/"+checkString(name)+".png'") ;
//				dataOfString.add("'Data/players/action/"+checkString(name)+".png'") ;
//				String insert = string+"("+i+","+dataOfString.get(0)+","+dataOfInteger.get(0)+","+dataOfString.get(1)+","+dataOfString.get(2)+","
//				         +dataOfInteger.get(1)+","+dataOfString.get(3)+","+dataOfInteger.get(2)
//						+","+dataOfInteger.get(3)+","+dataOfString.get(4)+","+dataOfString.get(5)+","+dataOfString.get(6)+")";
////				System.out.println(insert);
//				
//				
//                try {
//					int result = stmt.executeUpdate(insert) ;
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					System.out.println("wrong");
//					e.printStackTrace();
//				}	
		
		}
	}
	private String getDataOfOneLine(String oneLine){
		oneLine = oneLine.substring(1,oneLine.length()-1) ;
		String[] strs = oneLine.split("│") ;
		return strs[1].trim() ;
	}
	
	private String checkString(String string){//检查插入的字符串中是否有单引号，从而添加转义符
		if(string.contains("'")){
			int i = string.indexOf("'") ;
			string = string.substring(0,i)+"\\"+string.substring(i);
		}
		return string ;
	}
	private int checkInt(String string){
		try{
			return Integer.parseInt(string) ;
		}catch(Exception e){
			return 0 ;
		}
	}

}
