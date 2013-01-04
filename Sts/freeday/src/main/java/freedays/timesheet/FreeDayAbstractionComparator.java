package freedays.timesheet;

import java.util.Comparator;

public class FreeDayAbstractionComparator implements Comparator<FreeDayAbstraction> {

    @Override
    public int compare(FreeDayAbstraction o1, FreeDayAbstraction o2) {
        return o1.getStart().compareTo(o2.getStart());
    }

}
