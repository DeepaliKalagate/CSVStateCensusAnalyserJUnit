import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateCensusAnalyser
{
    List<CSVStatesCensus> csvStateCensuses = new ArrayList<>();

    public int giveStateCensusData(String STATE_CENSUS_DATA_CSV_FILE_PATH)
    {
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(STATE_CENSUS_DATA_CSV_FILE_PATH));
            CsvToBean<CSVStatesCensus> cavToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStatesCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<CSVStatesCensus> csvUserIterator = cavToBean.iterator();
            while (csvUserIterator.hasNext())
            {
                CSVStatesCensus csvStatesCensus = csvUserIterator.next();
                csvStateCensuses.add(csvStatesCensus);
                count++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return count;
    }
}
