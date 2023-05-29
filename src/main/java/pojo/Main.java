package pojo;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        NextBusManager nextBuss = new NextBusManager();
        List<StopEta> stopEtas = nextBuss.getLineEta(1,"11");

        for (StopEta stop : stopEtas)
        {
            System.out.println("stopId : " + stop.getStopId());
            System.out.println("eta : " + stop.getEta());
        }
    }
}
