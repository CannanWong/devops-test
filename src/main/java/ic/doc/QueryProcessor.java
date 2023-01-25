package ic.doc;

public class QueryProcessor {

    public String process(String query) {
        StringBuilder results = new StringBuilder();
        if (query.toLowerCase().contains("shakespeare")) {
            results.append("William Shakespeare (26 April 1564 - 23 April 1616) was an\n" +
                           "English poet, playwright, and actor, widely regarded as the greatest\n" +
                           "writer in the English language and the world's pre-eminent dramatist. \n");
            results.append(System.lineSeparator());
        }

        if (query.toLowerCase().contains("asimov")) {
            results.append("Isaac Asimov (2 January 1920 - 6 April 1992) was an\n" +
                           "American writer and professor of Biochemistry, famous for\n" +
                           "his works of hard science fiction and popular science. \n");
            results.append(System.lineSeparator());
        }

        if (query.toLowerCase().contains("darwin")) {
            results.append("Charles Robert Darwin (12 February 1809 – 19 April 1882) was an\n" +
                "biologist, geologist and English naturalist widely known for\n" +
                "his contributions to evolutionary biology. \n");
            results.append(System.lineSeparator());
        }
        if (query.toLowerCase().contains("cook")) {
            results.append("James Cook (7 November 1728 – 14 February 1779) was a British\n" +
                "cartographer, explorer and navigator, famous for his three\n" +
                "voyages between 1768 and 1779. \n");
            results.append(System.lineSeparator());
        }
        return results.toString();
    }
}

