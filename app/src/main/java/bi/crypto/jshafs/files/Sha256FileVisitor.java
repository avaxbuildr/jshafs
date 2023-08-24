/*
 * jshafs - Hash every file on a filesystem and save the hashes to a SQLite DB. Based on shafs
 *
 * @author avaxbuildr
 *
 * Documentation at https://crypto.bi
 *
 */

package bi.crypto.jshafs.files;

import bi.crypto.jshafs.crypto.Hashing;
import bi.crypto.jshafs.db.DAO;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class Sha256FileVisitor implements FileVisitor<Path> {

    DAO db;

    public Sha256FileVisitor(DAO d) {
        super();
        this.db = d;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String hash = Hashing.sha256Path(file);
        db.upsert(file, hash);
        System.out.println(hash + " " + file.toAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
