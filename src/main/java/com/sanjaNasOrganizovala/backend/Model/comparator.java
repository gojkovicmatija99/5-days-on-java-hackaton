package main.java.com.sanjaNasOrganizovala.backend.Model;
import java.util.Comparator;

public class comparator implements Comparator {
    private String order;

    public comparator(String order) {
        this.order = order;
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            if(!(o1 instanceof Team)) throw new Exception();
            if(!(o2 instanceof Team)) throw new Exception();
            Team e1 = (Team) o1;
            Team e2 = (Team) o2;

            if(order.equals("ASC")) {
                if( Double.compare( e1.getWinPercent() , e2.getWinPercent() ) == -1)
                    return -1;
                else if( Double.compare( e1.getWinPercent() , e2.getWinPercent() ) == 1)
                    return 1;
                else if(e1.getScoreDiff() < e2.getScoreDiff())
                    return -1;
                else if(e1.getScoreDiff() > e2.getScoreDiff())
                    return 1;
                else
                    return 0;
            }
            else if (order.equals("DESC")) {
                if( Double.compare( e1.getWinPercent() , e2.getWinPercent() ) == -1)
                    return 1;
                else if( Double.compare( e1.getWinPercent() , e2.getWinPercent() ) == 1)
                    return -1;
                else if(e1.getScoreDiff() < e2.getScoreDiff())
                    return 1;
                else if(e1.getScoreDiff() > e2.getScoreDiff())
                    return -1;
                else
                    return 0;
            }
            else
                throw new Exception();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        // Ako je ovde, nastala je greska
        return 0;
    }
}