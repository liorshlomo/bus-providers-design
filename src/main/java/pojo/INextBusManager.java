package pojo;

import java.util.List;

interface INextBusManager
{
    List<StopEta> getLineEta(int agencyId, String lineNumber);
}
