package bi.crypto.jshafs.db;

import java.nio.file.Path;

public interface DAO {
    public void upsert(Path p, String hash);
}
