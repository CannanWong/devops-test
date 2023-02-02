package ic.doc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryProcessorTest {

    QueryProcessor queryProcessor = new QueryProcessor();

    @Test
    public void returnsEmptyStringIfCannotProcessQuery() throws Exception {
        assertThat(queryProcessor.process("test"), is(""));
    }

    @Test
    public void knowsAboutShakespeare() throws Exception {
        assertThat(queryProcessor.process("Shakespeare"), containsString("playwright1234"));
    }

    @Test
    public void knowsAboutAsimov() throws Exception {
        assertThat(queryProcessor.process("Asimov"), containsString("science fiction"));
    }

    @Test
    public void knowsAboutDarwin() throws Exception {
        assertThat(queryProcessor.process("Darwin"), containsString("evolutionary biology"));
    }

    @Test
    public void knowsAboutCook() throws Exception {
        assertThat(queryProcessor.process("Cook"), containsString("cartographer"));
    }

    @Test
    public void isNotCaseSensitive() throws Exception {
        assertThat(queryProcessor.process("shakespeare"), containsString("playwright"));
    }
}
