package com.selesse.compare.workers;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class Md5WorkerTest {
    @Test
    public void canCalculateEmptyFile() throws Exception {
        File file = File.createTempFile("aaa", "aaa");
        file.deleteOnExit();
        Md5Worker md5Worker = new Md5Worker(file.toPath());

        assertEquals(Md5Worker.EMPTY_FILE_SHA, md5Worker.call().md5);
    }

    @Test
    public void canCalculateTextFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("text-file.txt").getFile());
        Md5Worker md5Worker = new Md5Worker(file.toPath());

        assertEquals("7f0ce67cf144aa2fa82f4f0547e9aa90", md5Worker.call().md5);
    }

    @Test
    public void canCalculateBinaryFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("gradle-wrapper.jar").getFile());
        Md5Worker md5Worker = new Md5Worker(file.toPath());

        assertEquals("253c42313985bbfef395f266696b0f71", md5Worker.call().md5);
    }
}