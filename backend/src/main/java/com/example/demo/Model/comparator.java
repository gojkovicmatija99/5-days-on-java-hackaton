package example.demo.Model;

import java.util.Comparator;

public class comparator implements Comparator {
    private String order;

    public comparator(String order) {
        this.order = order;
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            if(!(o1 instanceof Player)) throw new Exception();
            if(!(o2 instanceof Player)) throw new Exception();
            Player e1 = (Player) o1;
            Player e2 = (Player) o2;

            if(order.equals("ASC")) {
                if(e1.getId() < e2.getId())
                    return -1;
                else if (e1.getId() > e2.getId())
                    return 1;
                else
                    return 0;
            }
            else if (order.equals("DESC")) {
                if(e1.getId() < e2.getId())
                    return 1;
                else if (e1.getId() > e2.getId())
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