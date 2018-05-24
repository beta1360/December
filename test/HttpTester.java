package test;
import December.Server;

import java.io.IOException;

public class HttpTester {
    public static void main(String[] args) throws IOException {
        new Server(8000).run();
    }
}
