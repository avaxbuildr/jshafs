/*
 * jshafs - Hash every file on a filesystem and save the hashes to a SQLite DB. Based on shafs
 *
 * @author avaxbuildr
 *
 * Documentation at https://crypto.bi
 *
 */
package bi.crypto.jshafs;

import bi.crypto.jshafs.files.FileCountVisitor;
import bi.crypto.jshafs.files.Sha256FileVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class countfiles {

    public static void usage() {
        System.err.println("Usage: javac countfiles <initial_path>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {

        if(args.length != 1) {
            usage();
        }

        FileCountVisitor fcv = new FileCountVisitor();
        Files.walkFileTree(Path.of(args[0]), fcv);
        System.out.println(fcv.getFileCount());
    }
}
