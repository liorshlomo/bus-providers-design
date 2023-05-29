package pojo;

import java.util.List;

interface IArrivalsParser
{
     List<StopEta> parseResponse(String response);
}
