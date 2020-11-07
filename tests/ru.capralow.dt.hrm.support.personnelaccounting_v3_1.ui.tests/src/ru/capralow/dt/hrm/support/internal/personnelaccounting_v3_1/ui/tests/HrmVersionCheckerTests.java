/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com._1c.g5.v8.dt.core.platform.IV8Project;
import com._1c.g5.v8.dt.core.platform.IV8ProjectManager;
import com._1c.g5.v8.dt.testing.TestingWorkspace;

import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.HrmVersionChecker;
import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.PersonnelAccountingUiPlugin;

public class HrmVersionCheckerTests {

	@ClassRule
	public static TestingWorkspace testingWorkspace = new TestingWorkspace();

	@BeforeClass
	public static void setUp() throws Exception {
		testingWorkspace.setUpProject("v2_4_1", HrmVersionCheckerTests.class); //$NON-NLS-1$
	}

	private static void testFalseVersion(String startVersion) {
		IV8ProjectManager v8ProjectManager = PersonnelAccountingUiPlugin.getInstance().getInjector()
				.getInstance(IV8ProjectManager.class);

		IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v2_4_1")); //$NON-NLS-1$

		boolean result = HrmVersionChecker.checkSslVersion(v8Project, startVersion);

		Assert.assertFalse("Проверяемая версия должна быть меньше текущей", result);
	}

	private static void testFalseVersion(String startVersion, String endVersion) {
		IV8ProjectManager v8ProjectManager = PersonnelAccountingUiPlugin.getInstance().getInjector()
				.getInstance(IV8ProjectManager.class);

		IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v2_4_1")); //$NON-NLS-1$

		boolean result = HrmVersionChecker.checkSslVersion(v8Project, startVersion, endVersion);

		Assert.assertFalse("Проверяемая версия не должна входить в диапазон", result);
	}

	private static void testTrueVersion(String startVersion) {
		IV8ProjectManager v8ProjectManager = PersonnelAccountingUiPlugin.getInstance().getInjector()
				.getInstance(IV8ProjectManager.class);

		IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v2_4_1")); //$NON-NLS-1$

		boolean result = HrmVersionChecker.checkSslVersion(v8Project, startVersion);

		Assert.assertTrue("Проверяемая версия должна быть больше или равна текущей", result);
	}

	private static void testTrueVersion(String startVersion, String endVersion) {
		IV8ProjectManager v8ProjectManager = PersonnelAccountingUiPlugin.getInstance().getInjector()
				.getInstance(IV8ProjectManager.class);

		IV8Project v8Project = v8ProjectManager.getProject(testingWorkspace.getProject("v2_4_1")); //$NON-NLS-1$

		boolean result = HrmVersionChecker.checkSslVersion(v8Project, startVersion, endVersion);

		Assert.assertTrue("Проверяемая версия должна входить в диапазон", result);
	}

	@Test
	public void testVersion_2_4() throws Exception {
		testTrueVersion("2.4"); //$NON-NLS-1$
	}

	@Test
	public void testVersion_2_4_1() throws Exception {
		testTrueVersion("2.4.1"); //$NON-NLS-1$
	}

	@Test
	public void testVersion_2_4_1__3_0_3() throws Exception {
		testTrueVersion("2.4.1", "3.0.3"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testVersion_2_4_2() throws Exception {
		testFalseVersion("2.4.2"); //$NON-NLS-1$
	}

	@Test
	public void testVersion_3_1_1__3_1_1() throws Exception {
		testFalseVersion("3.1.1", "3.1.1"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testVersion_3_1_2() throws Exception {
		testFalseVersion("3.1.2"); //$NON-NLS-1$
	}
}