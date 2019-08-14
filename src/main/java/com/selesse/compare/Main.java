package com.selesse.compare;

import com.selesse.compare.filesystem.comparison.Diff;
import com.selesse.compare.filesystem.comparison.DiffFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new RuntimeException("Expected arguments, got " + args.length);
        }
        Diff diff = DiffFactory.create(args);
        diff.printOutput();
    }
}
