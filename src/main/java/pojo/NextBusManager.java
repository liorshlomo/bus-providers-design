package pojo;

import java.util.List;

public class NextBusManager implements INextBusManager{

    @Override
    public List<StopEta> getLineEta(int agencyId, String lineNumber) {
        NextBusProvider nextBuss = busProviderFactory(agencyId);
        List<StopEta> stopEtas = nextBuss.getLineEtaProvider(lineNumber);
        return stopEtas;
    }

    public static NextBusProvider busProviderFactory(int agencyId)
    {
        NextBusProvider nextBusProvider = null;
        try {
            switch (agencyId) {
                case 1:
                    nextBusProvider = new BussProvider1();
                    break;
                case 2:
                    nextBusProvider = new BussProvider2();
                    break;
                default:
                    throw new Exception("Agency's id does not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return nextBusProvider;

    }
}
