package de.dhbw.binaeratops.groups;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private static StringBuilder builder = new StringBuilder();
    @AfterClass
    public static void afterClass() throws IOException {
        PrintWriter logFile = new PrintWriter("target/JUnit/ " + java.time.LocalDate.now() + "-log.txt", "UTF-8");
        logFile.write(builder.toString());
        logFile.close();
    }

    @Rule
    public TestWatcher watchman = new TestWatcher() {

        @Override
        protected void failed(Throwable e, Description description) {
            builder.append("FAIL ");
            if (description != null) {
                builder.append(description);
            }
            if (e != null) {
                builder.append(' ');
                builder.append(e);
            }
            builder.append("\n");
        }

        @Override
        protected void succeeded(Description description) {
            builder.append("OK   ");
            if (description != null) {
                builder.append(description);
            }
            builder.append("\n");
        }
    };
}
