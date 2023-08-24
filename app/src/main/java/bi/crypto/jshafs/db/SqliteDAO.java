package bi.crypto.jshafs.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class SqliteDAO implements DAO {

    private final static String[] SCHEMA_FILENAMES = {
            "/schema.sql",
            "/index1.sql",
            "/index2.sql",
    };

    Connection conn;

    public static void runSqlFile(Connection conn, String filename) {
        try (var schemaIS = SqliteDAO.class.getResourceAsStream(filename)) {
            if (schemaIS == null) {
                throw new RuntimeException("Cannot open schema file " + filename);
            }
            String sql = new String(schemaIS.readAllBytes(), StandardCharsets.UTF_8);
            try {
                Statement st = conn.createStatement();
                st.execute(sql);
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SqliteDAO(String dbFile) {
        String url = "jdbc:sqlite:" + dbFile;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Create schema from resources/*.sql
        for (String fname : SCHEMA_FILENAMES) {
            runSqlFile(conn, fname);
        }

    }

    @Override
    public void upsert(Path p, String hash) {

        String sql = "INSERT INTO shafs (filepath, filehash, filesize) VALUES(?, ?, ?) ON CONFLICT(filepath) DO UPDATE SET filehash = ?, filesize = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.toAbsolutePath().toString());
            pstmt.setString(2, hash);
            pstmt.setLong(3, Files.size(p));
            pstmt.setString(4, hash);
            pstmt.setLong(5, Files.size(p));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
