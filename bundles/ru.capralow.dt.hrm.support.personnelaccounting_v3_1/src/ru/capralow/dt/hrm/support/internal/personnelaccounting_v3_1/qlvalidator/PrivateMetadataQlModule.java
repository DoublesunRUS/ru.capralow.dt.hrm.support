/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.qlvalidator;

import org.eclipse.xtext.service.AbstractGenericModule;

import com._1c.g5.v8.dt.ql.validation.QlValidator;

public class PrivateMetadataQlModule
    extends AbstractGenericModule
{
    @org.eclipse.xtext.service.SingletonBinding(eager = true)
    public Class<? extends QlValidator> bindQlValidator()
    {
        return PrivateMetadataQlValidator.class;
    }
}
