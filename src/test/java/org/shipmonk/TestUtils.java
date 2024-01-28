package org.shipmonk;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TestUtils {

    public static LinkedList<Integer> getIntList(final File dataFile) throws Exception {
        Preconditions.checkNotNull(dataFile);
        Preconditions.checkArgument(dataFile.exists());

        try (final FileInputStream fileInputStream = new FileInputStream(dataFile)) {
            final String data = new String(fileInputStream.readAllBytes());
            return Splitter.on(";").splitToStream(data).map(Integer::parseInt).collect(Collectors.toCollection(LinkedList::new));
        }
    }

    public static LinkedList<String> getStrList(final File dataFile) throws Exception {
        Preconditions.checkNotNull(dataFile);
        Preconditions.checkArgument(dataFile.exists());

        try (final FileInputStream fileInputStream = new FileInputStream(dataFile)) {
            final String data = new String(fileInputStream.readAllBytes());
            return Splitter.on(";").splitToStream(data).collect(Collectors.toCollection(LinkedList::new));
        }
    }

    public static void fillTestDataFileWithRandomInts(final int count,
                                                      final int maxIntBound,
                                                      final File dataFile) throws IOException {
        Preconditions.checkNotNull(dataFile);
        Preconditions.checkArgument(dataFile.exists());

        final List<Integer> randomIntList = getRandomIntList(count, maxIntBound);
        writeIntListToFile(randomIntList, dataFile);
    }

    public static void fillTestDataFileWithRandomString(final int count,
                                                        final int minLengthBound,
                                                        final int maxLengthBound,
                                                        final File dataFile) throws IOException {
        Preconditions.checkNotNull(dataFile);
        Preconditions.checkArgument(dataFile.exists());

        final List<String> randomStringList = getRandomStringList(count, minLengthBound, maxLengthBound);
        writeListToFile(randomStringList, dataFile);
    }

    private static void writeIntListToFile(final List<Integer> list, final File file) throws IOException {
        final String joined = list.stream().map(Object::toString).collect(Collectors.joining(";"));
        writeStringToFile(joined, file);
    }

    private static void writeListToFile(final List<String> list, final File file) throws IOException {
        final String joined = list.stream().map(Object::toString).collect(Collectors.joining(";"));
        writeStringToFile(joined, file);
    }

    private static void writeStringToFile(final String string, final File file) throws IOException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (final FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile())) {
                baos.writeBytes(string.getBytes());

                baos.writeTo(fileOutputStream);
                baos.flush();
                fileOutputStream.flush();
            }
        }
    }

    private static List<Integer> getRandomIntList(final int count, final int maxBound) {
        final List<Integer> list = Lists.newArrayList();
        final Random random = new Random();

        for (int i = 0; i < count; i++) {
            int value = random.nextInt(0, maxBound);
            list.add(value);
        }
        return list;
    }

    private static List<String> getRandomStringList(final int count, final int minLengthBound, final int maxLengthBound) {
        final List<String> list = Lists.newArrayList();

        for (int i = 0; i < count; i++) {
            final String randomStr = RandomStringUtils.randomAlphabetic(minLengthBound, maxLengthBound);
            list.add(randomStr);
        }
        return list;
    }
}
