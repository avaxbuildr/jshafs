/*
 * jshafs - Hash every file on a filesystem and save the hashes to a SQLite DB. Based on shafs
 *
 * @author avaxbuildr
 *
 * Documentation at https://crypto.bi
 *
 */
package bi.crypto.jshafs;

import bi.crypto.jshafs.db.DAO;
import bi.crypto.jshafs.db.SqliteDAO;
import bi.crypto.jshafs.files.Sha256FileVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class jshafs {

    static DAO db;

    public static void usage() {
        System.err.println("Usage: java jshafs <initial_path> <sqlite_db_path>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {

        if(args.length != 2) {
            usage();
        }

        db = new SqliteDAO(args[1]);
        Files.walkFileTree(Path.of(args[0]), new Sha256FileVisitor(db));
    }

}
