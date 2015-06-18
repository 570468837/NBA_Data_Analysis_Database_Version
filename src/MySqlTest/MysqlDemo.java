package MySqlTest;

/**
 * @author ：陶伟基 ，微博：http://weibo.com/taoandtao
 * @date ：2012/12/11
 * @place：广州大学华软软件学院
 */
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
 
 
public class MysqlDemo {
    public static void main(String[] args) {
    	MysqlDemo sql = new MysqlDemo() ;
    	sql.read();
    }
    
    public void read(){

        Connection conn = null;
        String createPlayerTable;
        String createTeamTable ;
        String createMatchTable ;
        String createScores ; //每场比赛的每节比分
        String createPlayerDataOfOneMatchTable ;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/NBA_DATA?"
                + "user=root&password=941104&useUnicode=true&characterEncoding=UTF8";
 
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
 
            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            System.out.println("成功建表");
            Statement stmt = conn.createStatement();
            createPlayerTable = "create table players(playerID int,name varchar(30) not null,number int(3),position varchar(10),height varchar(20),weight int(3),birth char(12),"
            		+ "age int(3),exp int(2),school varchar(50),primary key(playerID))";
       
            createTeamTable = "create table teams(teamID int(3),fullName varchar(20) not null,shortName char(3) not null,city varchar(20) not null,zone char(1) not null,district varchar(20) not null,homeCourt varchar(30) not null,timeOfEstablishment year(4) not null,primary key(teamID))";
       
            createMatchTable = "create table matches(matchID int(11),time date,firstTeam char(3),secondTeam char(3),firstScore int(3),"
            		+ "secondScore int(3),primary key(matchID))";
         
            createScores = "create table scoresOfOneMatch(matchID int not null,firstScore int(3) not null,secondScore int(3) not null)" ;
           
            createPlayerDataOfOneMatchTable = "create table playerdataofonematch(matchID int ,team char(3),name varchar(30),position char(2),ifStarting int(1) not null,persentTime varchar(10) not null,numberOfShooting int(3) not null,numberOfShotAttempt int(3) not null,"
            		+ "numberOf3_point int(3) not null, numberOf3_pointAttempt int(3) not null,numberOfFreeThrow int(3) not null, numberOfFreeThrowAttempt int(3) not null ,numberOfAttackRebound int(3) not null,numberOfDefenseRebound int (3)not null ,numberOfRebound int (3) not null,numberOfAssist int (3)not null ,numberOfSteal int(3) not null ,numberOfBlock int(3) not null ,numberOfFault int (3)not null,"
            		+ "numberOfFoul int(3)  not null,score int(3) not null, primary key(matchID,team ,name))" ;
            		
            int result1 = stmt.executeUpdate(createPlayerTable);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            int result2= stmt.executeUpdate(createTeamTable) ;
            int  result3 =stmt.executeUpdate(createScores) ;
            int result4 = stmt.executeUpdate(createPlayerDataOfOneMatchTable) ;
            int result5 = stmt.executeUpdate(createMatchTable) ;
            int result =result3*result4*result5*result1*result1;
            if (result != -1) {
                System.out.println("创建数据表成功");
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
 
    
    }
    
}