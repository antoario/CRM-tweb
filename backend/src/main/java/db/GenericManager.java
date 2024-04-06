package db;

import java.util.List;
import java.util.Map;

public interface GenericManager<T> {
    List<T> loadAll();
    T loadDetails(int id);

    int addFromParams(Map<String, String[]> params);

}
