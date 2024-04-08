package utility;

public class SQLbuilder {

    String table;

    public SQLbuilder(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public String getAllData() {
        return "SELECT * from " + this.table;
    }

    public String getSingle() {
        return "SELECT * from " + this.table + "WHERE id = ?";
    }


}
