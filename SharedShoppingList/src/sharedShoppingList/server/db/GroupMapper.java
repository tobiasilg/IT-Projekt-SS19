
package sharedShoppingList.server.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.util.Vector;

/**
* Author dieser Klasse:
* @author Tobias Ilg
*/

public class GroupMapper {

private static GroupMapper groupmapper = null;

/**
* Falls noch kein GroupMapper existiert wird ein neuen GroupMapper erstellt und zurückgegeben
*
* @return erstmalig erstellter GroupMapper
*
*/

public static GroupMapper groupMapper() {
if (groupmapper == null){
groupmapper = new GroupMapper();
}
return groupmapper;
}

/**
* Findet Groups durch eine G_ID und speichert die dazugehörigen Werte (G_ID, name, createDate, modDate) in einem Group Objekt ab und gibt dieses wieder
*
* @param sid übergebener Integer der G_ID
* @return Ein vollständiges Group Objekt
*
* @author Tobias Ilg
*/

public Group findByID(Group group){
Connection con = DBConnection.connection();
Group g = new Group();

try{
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("SELECT G_ID, name, createDate, modDate FROM T_Group WHERE G_ID ="+ group.getId() + " ORDER BY modDate");

if (rs.next()){

g.setId(rs.getInt("G_ID"));
g.setOwnerId(rs.getInt("name"));
g.setCreateDate(rs.getTimestamp("createDate"));
g.setModDate(rs.getTimestamp("modDate"));

return g;
}
}

catch (SQLException e){
e.printStackTrace();
return g;
}

return g;
}


public Vector<Group> findAll(){
Connection con = DBConnection.connection();
Vector<Group> result = new Vector<Group>();

try{
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("SELECT G_ID, name, createDate, modDate FROM T_Group ORDER BY modDate");

while (rs.next()){
Group g = new Group();
g.setId(rs.getInt("G_ID"));
g.setOwnerId(rs.getInt("name"));
g.setCreateDate(rs.getTimestamp("createDate"));
g.setModDate(rs.getTimestamp("modDate"));
result.addElement(p);
}
}catch(SQLException e2){
e2.printStackTrace();
}
return result;
}


/**
* Gibt alle Group Objekte eine spez. Users zurück welche mit G_ID, name, createDate und modDate befüllt sind
*
* @return Ein Vector voller Group Objekte welche befüllt sind
*
*/
public Vector<Group> findAllByUID(User u){
Connection con = DBConnection.connection();
Vector<Group> result = new Vector<Group>();

try{
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("SELECT G_ID, name, createDate, modDate FROM T_Group WHERE creator =" +u.getId()+" ORDER BY modDate");

while (rs.next()){
Group g = new Group();
g.setId(rs.getInt("G_ID"));
g.setOwnerId(rs.getInt("name"));
g.setCreateDate(rs.getTimestamp("createDate"));
g.setModDate(rs.getTimestamp("modDate"));
result.addElement(p);
}
}catch(SQLException e2){
e2.printStackTrace();
}
return result;
}
/**
* Gibt alle Group Objekte zurück welche mit G_ID, name, createDate und modDate befüllt sind
* Hierfür holen wir G_ID, creator, cintent, createDate und modDate aus der T_Group Tabelle und speichern diese in einem Group Objekt ab und fügen diese dem Vector hinzu
* Diesen Vector befüllt mit Group geben wir zurück
*
* @return Ein Vector voller Group Objekte welche befüllt sind
*
*/



public Group insert(Group group){
Connection con = DBConnection.connection();
Timestamp ts = new Timestamp(System.currentTimeMillis());

String g = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);

try{
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("SELECT MAX(G_ID) AS maxsid FROM T_Group");
if (rs.next()){

group.setId(rs.getInt("maxsid")+1);
Statement stmt2 = con.createStatement();
stmt2.executeUpdate("INSERT INTO T_Group (G_ID, name, createDate, modDate)"
+ " VALUES ("
+ group.getId()
+ ", "
+ group.getName()
+ ", '"
+ g
+ "', '"
+ g
+ "')") ;

return group;

}
}
catch (SQLException e2){
e2.printStackTrace();
return group;
}
return group;}

/**
* Befüllt T_Group mit G_ID, name, createDate und modDate, falls sich was geändert hat
* Ein Group Objekt wird zurückgegeben
*
* @param group übergebenes Group Objekt mit Attributen G_ID und type
* @return Ein vollständiges Group Objekt
*
*/
public Group update(Group group){
Connection con = DBConnection.connection();
Timestamp ts = new Timestamp(System.currentTimeMillis());

String g = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);

try{
Statement stmt = con.createStatement();
stmt.executeUpdate("UPDATE T_Group SET G_ID ="
+group.getId()
+", creator ="
+ group.getName()
// +"', createDate="
// + group.getCreateDate()
+"', modDate='"
+ g
+ "' WHERE G_ID="
+ group.getId());
}
catch (SQLException e2){
e2.printStackTrace();
return group;
}
return group;}

/**
* Entfernt alles aus T_Group wo die G_ID der ID des übergebenen Objekts entspricht
*
* @param group übergebenes Group Objekt mit Attribut G_ID
*
*/
public void delete (Group group){
Connection con = DBConnection.connection();

try{

Statement stmt = con.createStatement();
stmt.executeUpdate("DELETE FROM T_Group WHERE G_ID =" + group.getId());
}

catch (SQLException e2){
e2.printStackTrace();
}
}
} 

