/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PersonnelAccountingUiPlugin
    extends Plugin
{
    public static final String ID = "ru.capralow.dt.hrm.support.personnelaccounting_v3_1.ui"; //$NON-NLS-1$

    private static PersonnelAccountingUiPlugin instance;

    public static IStatus createErrorStatus(String message)
    {
        return new Status(IStatus.ERROR, ID, 0, message, (Throwable)null);
    }

    public static IStatus createErrorStatus(String message, int code)
    {
        return new Status(IStatus.ERROR, ID, code, message, (Throwable)null);
    }

    public static IStatus createErrorStatus(String message, int code, Throwable throwable)
    {
        return new Status(IStatus.ERROR, ID, code, message, throwable);
    }

    public static IStatus createErrorStatus(String message, Throwable throwable)
    {
        return new Status(IStatus.ERROR, ID, 0, message, throwable);
    }

    public static PersonnelAccountingUiPlugin getInstance()
    {
        return instance;
    }

    public static void log(IStatus status)
    {
        getInstance().getLog().log(status);
    }

    private Injector injector;

    private Injector createInjector()
    {
        try
        {
            return Guice.createInjector(new ExternalDependenciesModule(getInstance()));

        }
        catch (Exception e)
        {
            String msg = MessageFormat.format(Messages.Failed_to_create_injector_for_0,
                getInstance().getBundle().getSymbolicName());
            log(createErrorStatus(msg, e));
            return injector;

        }
    }

    public synchronized Injector getInjector()
    {
        if (injector == null)
            injector = createInjector();

        return injector;
    }

    @Override
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        instance = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception
    {
        instance = null;
        super.stop(context);
    }
}
