import java.sql.*;
//import java.util.*;
public class Billitem 
{
	private int billid;
	private int itemid;
	private String itemname;
	private int itemprice;
	private int quantity;
	private int sid;
	private int discount;
	Billitem(int billid,int itemid,String itemname,int itemprice,int quantity,int sid,int discount)
	{
		this.billid=billid;
		this.itemid=itemid;
		this.itemname=itemname;
		this.itemprice=itemprice;
		this.quantity=quantity;
		this.sid=sid;
		this.discount=discount;
	}
	int AddtoBillItemstoDB(Connection con) throws Exception
	{
		ResultSet rs;
		String query="insert into billings.billitems  (billid,itemid,itemname,itemprice,quantity,discountpercentage) values (?,?,?,?,?,?)";
		PreparedStatement st=con.prepareStatement(query);
		st.setInt(1, billid);
		st.setInt(2, itemid);
		st.setString(3,itemname);
		st.setInt(4,itemprice);
		st.setInt(5, quantity);
		st.setInt(6, discount);
		st.executeUpdate();
		st.close();
		Statement stm=con.createStatement();
		query=String.format("update billings.storeitems set stock=stock-%d where itemid=%d and storeid=%d",quantity,itemid,sid);
		stm.executeUpdate(query);
		stm.close();
		query="select price from  billitems  order by billitemid desc limit 1";
		st=con.prepareStatement(query);
	    rs=st.executeQuery();
	    rs.next();
		return rs.getInt("price");
	}
	
}
