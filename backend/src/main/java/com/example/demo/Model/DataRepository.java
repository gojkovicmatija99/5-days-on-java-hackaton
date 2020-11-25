package example.demo.Model;

import org.json.JSONException;

public interface DataRepository {
    public String getQuery1() throws JSONException;
    public String getQuery2(Game game) throws JSONException;
    public String getQuery3(Player player) throws JSONException;
    public String getQuery4() throws JSONException;
    public String getQuery5() throws JSONException;
    public String getQuery6() throws JSONException;
}
