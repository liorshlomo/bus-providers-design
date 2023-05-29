package pojo;

import java.util.List;

interface INextBusProvider {
    List<StopEta> getLineEta(String lineNumber);
}
