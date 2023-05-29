package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class NextBusProvider implements INextBusProvider {
    protected final String API_BASE_URL ;
    protected final int[] supportedLines ;
    protected final int agencyId;
    protected String parserType;

    NextBusProvider(String i_url, int[] i_supportedLines, int i_agencyId, String i_parserType)
    {
        this.API_BASE_URL = i_url;
        this.supportedLines =i_supportedLines;
        this.agencyId = i_agencyId;
        this.parserType= i_parserType;
    }
    @Override
    public List<StopEta> getLineEta(String lineNumber)
    {
        String apiUrl =setting(lineNumber);

        Callable<String> apiRequestCallable = () -> ApiRequester.makeApiRequest(apiUrl);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> apiResponseFuture = executorService.submit(apiRequestCallable);

        List<StopEta> stopEtas = new ArrayList<>();
        try {

            String response = apiResponseFuture.get(); // Wait for the API response
            IArrivalsParser parser = parserFactory();
            stopEtas = parser.parseResponse(response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // Shutdown the executor service
        }

        return stopEtas;
    }

    private IArrivalsParser parserFactory()
    {
        IArrivalsParser parser = null;
        try {
            switch (parserType) {
                case "jason":
                    parser = new ArrivalsJsonParser();
                    break;
                case "xml":
                    parser = new ArrivalsXmlParser();
                break;
                default:
                    throw new Exception("parser type doesnt fit");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return parser;
    }
    protected void checkLineNumber(String lineNumber)
    {
        try {
            int desiredLine = Integer.parseInt(lineNumber);
            for (int line : supportedLines)
            {
                if (desiredLine == line) return;
            }
            throw new IllegalArgumentException("Sorry, The line " + desiredLine + " is not supported.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    protected String setting(String lineNumber)
    {
        checkLineNumber(lineNumber);
        return API_BASE_URL + lineNumber;
    }

    public abstract List<StopEta> getLineEtaProvider(String lineNumber);

}
