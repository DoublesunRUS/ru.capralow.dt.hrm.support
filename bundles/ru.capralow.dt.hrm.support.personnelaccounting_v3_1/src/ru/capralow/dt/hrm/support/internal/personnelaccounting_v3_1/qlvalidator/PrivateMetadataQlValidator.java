/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.qlvalidator;

import org.eclipse.xtext.validation.Check;

import com._1c.g5.v8.dt.ql.model.QuerySchemaExpression;
import com._1c.g5.v8.dt.ql.validation.QlValidator;

public class PrivateMetadataQlValidator
    extends QlValidator
{
    @Check
    public void checkPersonnelHistory(QuerySchemaExpression qsExpr)
    {
        return;
    }
}
