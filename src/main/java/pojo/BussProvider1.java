package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BussProvider1 extends NextBusProvider {

    BussProvider1()
    {
        super("http://d2fo80vv1pnzuu.cloudfront.net/test/",new int[]{5,6,11},1,"jason");
    }


    @Override
    public List<StopEta> getLineEtaProvider(String lineNumber) {
        return getLineEta(lineNumber);
    }


}
