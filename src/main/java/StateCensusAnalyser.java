
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser
{
    private static final  String SOrtState_JSON_FILE_PATH="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByState.json";
    private static final String SortByPopulation="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByPopulation.json";
    private static final String SortByDensityPerSqKm ="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByDensityPerSqKm.json";
    private static final String SortByAreaInSqKm="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByAreaInSqKm.json";
    List<CSVStatesCensus> csvStateCensuses = new ArrayList<>();

    public int giveStateCensusData(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws StateException
    {
        int count = 0;
        try
        {
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
                if (csvStatesCensus.getState()==null || csvStatesCensus.getPopulation()==null || csvStatesCensus.getAreaInSqKm()==null || csvStatesCensus.getDensityPerSqKm()==null)
                {
                    throw new StateException(StateException.ExceptionType.NO_SUCH_HEADER, "No Such Header in File");
                }
                count++;
            }
        }
        catch (RuntimeException e)
        {
            throw new StateException(StateException.ExceptionType.INVALID_DELIMITER,"Exception is Occured because of incorrect Delimiter or Header",e);
        }
        catch (NoSuchFileException e)
        {
            throw new StateException(StateException.ExceptionType.NO_SUCH_FILE,"Please Check File Extention! Give Correct Extension",e);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return count;
    }
    public int sortCSVFile(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws IOException, StateException
    {

        int count = giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Comparator<CSVStatesCensus> c = (s1, s2) -> s1.getState().compareTo(s2.getState());
        csvStateCensuses.sort(c);
        writeInJSONFile(csvStateCensuses,SOrtState_JSON_FILE_PATH);
        return count;
    }

    public int sortByPopulation(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws IOException, StateException
    {

        int count = giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Comparator<CSVStatesCensus> c = (s1, s2) -> Integer.parseInt(s2.getPopulation()) - Integer.parseInt(s1.getPopulation());
        csvStateCensuses.sort(c);
        writeInJSONFile(csvStateCensuses,SortByPopulation);
        return count;
    }

    public int sortByDensity(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws IOException, StateException
    {
        int count = giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Comparator<CSVStatesCensus> c = (s1, s2) -> Integer.parseInt(s2.getDensityPerSqKm()) - Integer.parseInt(s1.getDensityPerSqKm());
        csvStateCensuses.sort(c);
        writeInJSONFile(csvStateCensuses,SortByDensityPerSqKm);
        return count;
    }

    public int sortByAreaInSqKm(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws IOException, StateException
    {
        int count = giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Comparator<CSVStatesCensus> c = (s1, s2) -> Integer.parseInt(s2.getAreaInSqKm()) - Integer.parseInt(s1.getAreaInSqKm());
        csvStateCensuses.sort(c);
        writeInJSONFile(csvStateCensuses,SortByAreaInSqKm);
        return count;
    }

    public void writeInJSONFile(List<CSVStatesCensus> list,String filePath) throws IOException
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        FileWriter writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();
    }
}
