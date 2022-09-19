import java.lang.Math;


public class lab1
{
    public static int[] h = new int[8];
    public static double[]  x = new double[20];
    public static float[][] y = new float[8][20];

    public static void main(String[] args)
    {

        for (byte i=0; i<h.length; i++)   h[i]=16-2*i;
        for (byte i=0; i<x.length; i++)   x[i]=randomInRange(-5, 11);
        for (byte i=0; i<y.length; i++)
        {
            for(byte j=0; j<y[i].length; j++) {
                switch (h[i]) {
                    case 16:
                        y[i][j] = (float) Math.log((Math.pow(2 * (Math.abs(x[j]) + 1) + 1, 2)));
                        break;

                    case 2:
                    case 4:
                    case 12:
                    case 14:
                        y[i][j] = (float) Math.pow(Math.sin(Math.pow(x[j] * Math.pow(x[j] + 1, 2), 2)), 1 / 3);
                        break;
                    default:
                        y[i][j] = (float) (Math.cos(Math.asin(1 / Math.pow(Math.E, x[j]))) + 3);
                        break;
                }
            }
        }
        Printer();
    }

    private static double randomInRange(double min, double max)
    {
        return min + Math.random()*(max-min);
    }


    private static void Printer()
    {
        for (byte i=0; i<h.length; i++) System.out.print(h[i] + " ");
        System.out.println("\n");

        for (byte i=0; i<x.length; i++) System.out.print(String.format("%.3f  ",x[i]));
        System.out.println("\n");

        for (byte i=0; i<y.length; i++)
        {
            for(byte j=0; j<y[i].length; j++)
                System.out.print(String.format("%.3f  ",y[i][j]));
            System.out.println();
        }
    }
}
