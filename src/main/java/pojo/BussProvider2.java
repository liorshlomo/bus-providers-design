package pojo;

import java.util.List;

public class BussProvider2 extends NextBusProvider
{
    BussProvider2()
    {
        super("https://abcdefg.org/arrivals?lineNumber=",new int[]{4},1,"xml");
    }


    @Override
    public List<StopEta> getLineEtaProvider(String lineNumber) {
        return getLineEta(lineNumber);
    }
}
