package com.playtomic.tests.wallet;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = {"pretty"}, glue = "com.playtomic.tests.wallet.stepDefs")
public class CucumberTestRunner {

}