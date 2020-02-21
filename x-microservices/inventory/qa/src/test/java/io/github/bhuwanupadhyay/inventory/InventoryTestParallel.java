package io.github.bhuwanupadhyay.inventory;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@KarateOptions(tags = {"~@ignore"})
public class InventoryTestParallel {

    private static final String ENV = "inventory";

    @BeforeClass
    public static void launchInfra() {

    }

    @AfterClass
    public static void killInfra() {

    }

    @Test
    public void testParallel() {
        System.setProperty("karate.env", ENV);
        Results results = Runner.parallel(getClass(), 5);
        generateReport(results.getReportDir());
        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
    }

    private void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("out"), ENV);
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

}