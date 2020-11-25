package main.java.com.sanjaNasOrganizovala.backend.Model;

import org.json.JSONException;

public interface DataRepository {
    public String getQuery1() throws JSONException;
    public String getQuery2(long gameID) throws JSONException;
    public String getQuery3(long playerID) throws JSONException;
    public String getQuery4() throws JSONException;
    public String getQuery5() throws JSONException;
    public String getQuery6() throws JSONException;
}
