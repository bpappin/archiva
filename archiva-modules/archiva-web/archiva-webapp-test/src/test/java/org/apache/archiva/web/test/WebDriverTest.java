package org.apache.archiva.web.test;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Olivier Lamy
 */
public class WebDriverTest
    extends FluentTest
{

    @Test
    public void simpletest()
        throws Exception
    {

        Properties tomcatPortProperties = new Properties();
        tomcatPortProperties.load(
            new FileInputStream( new File( System.getProperty( "tomcat.propertiesPortFilePath" ) ) ) );

        int tomcatPort = Integer.parseInt( tomcatPortProperties.getProperty( "tomcat.maven.http.port" ) );

        goTo( "http://localhost:" + tomcatPort + "/archiva/index.html?request_lang=en" );

        FluentList<FluentWebElement> elements = find( "#create-admin-link-a" );

        if ( !elements.isEmpty() && elements.get( 0 ).isDisplayed() )
        {
            WebElement webElement = elements.get( 0 ).getElement();
            Assert.assertEquals( "Create Admin User", webElement.getText() );
        }
        else
        {
            elements = find( "#login-link-a" );
            WebElement webElement = elements.get( 0 ).getElement();
            Assert.assertEquals( "LOGIN", webElement.getText() );
        }

    }

    @Override
    public WebDriver getDefaultDriver()
    {
        String seleniumBrowser = System.getProperty( "selenium.browser" );

        if ( StringUtils.contains( seleniumBrowser, "chrome" ) )
        {
            return new ChromeDriver();
        }

        if ( StringUtils.contains( seleniumBrowser, "safari" ) )
        {
            return new SafariDriver();
        }

        if ( StringUtils.contains( seleniumBrowser, "iexplore" ) )
        {
            return new InternetExplorerDriver();
        }

        return new FirefoxDriver();

    }
}