package slava0135.fuzz3.mutation;

public class TriangleCalculator {

    public static class TriangleProperties {
        public double area;
        public double perimeter;
        public double inradius;
        public double circumradius;

        public TriangleProperties(double area, double perimeter, double inradius, double circumradius) {
            this.area = area;
            this.perimeter = perimeter;
            this.inradius = inradius;
            this.circumradius = circumradius;
        }
    }

    public static TriangleProperties calculateProperties(double a, double b, double c) {
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Such triangle does not exist");
        }

        double s = (a + b + c) / 2;
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        double perimeter = a + b + c;
        double inradius = area / s;
        double circumradius = (a * b * c) / (4 * area);

        return new TriangleProperties(area, perimeter, inradius, circumradius);
    }
}
