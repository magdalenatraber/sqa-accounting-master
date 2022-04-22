package at.campus02.input;

import at.campus02.exchange.ExchangeRates;
import at.campus02.exchange.ExchangeRatesAPI;
import at.campus02.storage.Database;
import org.junit.Test;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class TestItemInput {
    @Test
    public void testViewItem() throws IOException {
        InputHelper inputHelper =mock(InputHelper.class);
        ExchangeRatesAPI exchangeRatesAPI = mock((ExchangeRatesAPI.class));
        when(inputHelper.getItemId(InputHelper.ID_MUST_EXIST)).thenReturn(1);
        when(exchangeRatesAPI.fromAPI()).thenReturn(new ExchangeRates(
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(7)
                ));
        PrintStream outmock = mock(PrintStream.class);

        ItemInput itemInput = new ItemInput(inputHelper,outmock,exchangeRatesAPI);
        Database.setupSampleDatabase();

        itemInput.viewItem();

        verify(outmock,times(1)).println(
                "\tPrice [GBP]: 2.85" +
                "\n\tPrice [USD]: 0.95" +
                "\n\tPrice [CAD]: 4.75"  +
                "\n\tPrice [JPY]: 6.65"  +
                "\n");
        verify(outmock,times(1)).println(
                matches("GBP.+2\\.85")
        );

        verify(outmock,times(1)).println(
                matches("JPY.+6\\.65")
        );

        verify(exchangeRatesAPI, times(1)).fromAPI();
    }


}
