/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui;

import org.eclipse.core.runtime.Plugin;

import com._1c.g5.v8.dt.bm.index.emf.IBmEmfIndexManager;
import com._1c.g5.v8.dt.core.platform.IV8ProjectManager;
import com._1c.g5.v8.dt.platform.version.IRuntimeVersionSupport;
import com._1c.g5.v8.dt.ql.scoping.IQlCachedScopeProvider;
import com._1c.g5.wiring.AbstractServiceAwareModule;

public class ExternalDependenciesModule
    extends AbstractServiceAwareModule
{

    public ExternalDependenciesModule(Plugin bundle)
    {
        super(bundle);
    }

    @Override
    protected void doConfigure()
    {
        bind(IBmEmfIndexManager.class).toService();
        bind(IQlCachedScopeProvider.class).toService();
        bind(IRuntimeVersionSupport.class).toService();
        bind(IV8ProjectManager.class).toService();
    }

}
