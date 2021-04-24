package main.java.com.hit.gamecalendar.common;

public class Triplet<FIRST, SECOND, THIRD> implements Comparable<Triplet<FIRST, SECOND, THIRD>> {

    public final FIRST first;
    public final SECOND second;
    public final THIRD third;

    public Triplet(FIRST first, SECOND second, THIRD third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <FIRST, SECOND, THIRD> Triplet<FIRST, SECOND, THIRD> of(FIRST first,
                                                         SECOND second,
                                                         THIRD third) {
        return new Triplet<>(first, second, third);
    }

    @Override
    public int compareTo(Triplet<FIRST, SECOND, THIRD> o) {
        int firstCmp = compare(first, o.first);
        if (firstCmp == 0) {
            int secondCmp = compare(second, o.second);
            if (secondCmp == 0)
                return compare(third, o.third);
        }
        return firstCmp;
    }

    // todo move this to a helper class.
    private static int compare(Object o1, Object o2) {
        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1
                : ((Comparable) o1).compareTo(o2);
    }

    @Override
    public int hashCode() {
        return 31 * hashcode(first) + hashcode(second) + hashcode(third);
    }

    // todo move this to a helper class.
    private static int hashcode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Triplet))
            return false;
        if (this == obj)
            return true;
        return equal(first, ((Triplet) obj).first)
                && equal(second, ((Triplet) obj).second);
    }

    // todo move this to a helper class.
    private boolean equal(Object o1, Object o2) {
        return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ')';
    }
}

