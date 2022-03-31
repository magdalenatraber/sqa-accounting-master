package at.campus02.input;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TestInputHelper {
    @Test
    public void testValidDate() throws EOFException {
        Scanner scanner = new Scanner("2022-03-31\n");
        PrintStream mockStream = mock(PrintStream.class);
        InputHelper inputHelper = new InputHelper(scanner,mockStream);
        Date result = inputHelper.getDate("test");
        Date expected = new Date(122, Calendar.MARCH,31);
        assertEquals(expected,result);
        verify(mockStream,Mockito.never()).print("x");
        verify(mockStream,Mockito.atLeastOnce()).println(anyString());
    }

    @Test
    public void testValidAfterInvalidDate() throws EOFException {
        Scanner scanner = new Scanner("31st Mar 2022\n2022-03-31\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream,true);
        InputHelper inputHelper = new InputHelper(scanner,out);
        Date result = inputHelper.getDate("test");
        Date expected = new Date(122, Calendar.MARCH,31);
        assertEquals(expected,result);
        String msg = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(msg.contains("Wrong date format"));
    }

    @Test
    public void testValidAfterInvalidDate2() throws EOFException {
        Scanner scanner = mock(Scanner.class);
        when(scanner.next(anyString())).thenReturn("2022-03-31");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = mock(PrintStream.class);
        InputHelper inputHelper = new InputHelper(scanner,out);
        Date result = inputHelper.getDate("test");
        Date expected = new Date(122, Calendar.MARCH,31);
        assertEquals(expected,result);
        verify(out,Mockito.times(1)).println("Wrong date format.");
        verify(out,Mockito.times(2)).println("test (empty string to abort) ");

    }
}
