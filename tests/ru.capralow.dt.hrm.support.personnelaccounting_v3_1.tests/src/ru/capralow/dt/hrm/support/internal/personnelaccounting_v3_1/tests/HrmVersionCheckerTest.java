/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com._1c.g5.v8.dt.core.platform.IV8Project;
import com._1c.g5.v8.dt.core.platform.IV8ProjectManager;
import com._1c.g5.v8.dt.testing.TestingWorkspace;

import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.HrmVersionChecker;
import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.PersonnelAccountingPlugin;

public class HrmVersionCheckerTest
{

    @ClassRule
    public static TestingWorkspace testingWorkspace = new TestingWorkspace();

    @BeforeClass
    public static void setUp() throws Exception
    {
        testingWorkspace.setUpProject("v3_1_14", HrmVersionCheckerTest.class); //$NON-NLS-1$
    }

    private static void testFalseVersion(String startVersion)
    {
        IV8ProjectManager v8ProjectManager =
            PersonnelAccountingPlugin.getInstance().getInjector().getInstance(IV8ProjectManager.class);

        IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v3_1_14")); //$NON-NLS-1$

        boolean result = HrmVersionChecker.checkHrmVersion(v8Project, startVersion);

        Assert.assertFalse("Проверяемая версия должна быть меньше текущей", result);
    }

    private static void testFalseVersion(String startVersion, String endVersion)
    {
        IV8ProjectManager v8ProjectManager =
            PersonnelAccountingPlugin.getInstance().getInjector().getInstance(IV8ProjectManager.class);

        IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v3_1_14")); //$NON-NLS-1$

        boolean result = HrmVersionChecker.checkHrmVersion(v8Project, startVersion, endVersion);

        Assert.assertFalse("Проверяемая версия не должна входить в диапазон", result);
    }

    private static void testTrueVersion(String startVersion)
    {
        IV8ProjectManager v8ProjectManager =
            PersonnelAccountingPlugin.getInstance().getInjector().getInstance(IV8ProjectManager.class);

        IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v3_1_14")); //$NON-NLS-1$

        boolean result = HrmVersionChecker.checkHrmVersion(v8Project, startVersion);

        Assert.assertTrue("Проверяемая версия должна быть больше или равна текущей", result);
    }

    private static void testTrueVersion(String startVersion, String endVersion)
    {
        IV8ProjectManager v8ProjectManager =
            PersonnelAccountingPlugin.getInstance().getInjector().getInstance(IV8ProjectManager.class);

        IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v3_1_14")); //$NON-NLS-1$

        boolean result = HrmVersionChecker.checkHrmVersion(v8Project, startVersion, endVersion);

        Assert.assertTrue("Проверяемая версия должна входить в диапазон", result);
    }

    @Test
    public void testVersion_3_1() throws Exception
    {
        testTrueVersion("3.1"); //$NON-NLS-1$
    }

    @Test
    public void testVersion_3_1_10__3_1_16() throws Exception
    {
        testTrueVersion("3.1.10", "3.1.16"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Test
    public void testVersion_3_1_13() throws Exception
    {
        testFalseVersion("3.1.13"); //$NON-NLS-1$
    }

    @Test
    public void testVersion_3_1_14() throws Exception
    {
        testTrueVersion("3.1.14"); //$NON-NLS-1$
    }

    @Test
    public void testVersion_3_1_15() throws Exception
    {
        testFalseVersion("3.1.15"); //$NON-NLS-1$
    }

}
