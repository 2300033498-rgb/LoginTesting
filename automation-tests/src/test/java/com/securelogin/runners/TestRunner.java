package com.securelogin.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber Test Runner
 * Configures and executes all Cucumber feature files
 * 
 * Run Configuration:
 * - All feature files in features/ directory
 * - Step definitions in steps/ package
 * - HTML and JSON reports generated
 * - Console output with pretty format
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    // Path to feature files
    features = "src/test/resources/features",
    
    // Package containing step definitions
    glue = {"com.securelogin.steps"},
    
    // Output format for console
    plugin = {
        "pretty",                                           // Readable console output
        "html:target/cucumber-reports/cucumber.html",      // HTML report
        "json:target/cucumber-reports/cucumber.json",      // JSON report
        "junit:target/cucumber-reports/cucumber.xml"       // XML report
    },
    
    // Only run scenarios with these tags (comment out to run all)
    // tags = "@functional or @boundary or @security or @ui",
    
    // Strict mode - fail if there are undefined or pending steps
    // strict = true,
    
    // Dry run - checks if all steps have definitions without executing them
    dryRun = false,
    
    // Display more detailed output
    monochrome = true,
    
    // Generate snippets for undefined steps
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class TestRunner {
    // This class is empty - JUnit uses annotations to run tests
    
    /**
     * To run specific test scenarios, use tags:
     * 
     * Run only functional tests:
     * tags = "@functional"
     * 
     * Run only boundary tests:
     * tags = "@boundary"
     * 
     * Run only security tests:
     * tags = "@security"
     * 
     * Run only UI tests:
     * tags = "@ui"
     * 
     * Run positive tests:
     * tags = "@positive"
     * 
     * Run negative tests:
     * tags = "@negative"
     * 
     * Combine tags with AND:
     * tags = "@functional and @positive"
     * 
     * Combine tags with OR:
     * tags = "@functional or @boundary"
     * 
     * Exclude tags:
     * tags = "not @security"
     */
}
