/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.qlvalidator;

import static com._1c.g5.v8.dt.ql.model.QlPackage.Literals.ABSTRACT_QUERY_SCHEMA_TABLE__TABLE_DB_VIEW;

import org.eclipse.xtext.validation.Check;

import com._1c.g5.v8.dt.ql.model.AbstractQuerySchemaTable;
import com._1c.g5.v8.dt.ql.validation.QlValidator;

public class PrivateMetadataQlValidator
    extends QlValidator
{
    @Check
    public void checkPersonnelHistory(AbstractQuerySchemaTable qsTable)
    {
        if (!qsTable.getFullTableName().equalsIgnoreCase("РегистрСведений.КадроваяИсторияСотрудников")) //$NON-NLS-1$
            return;

        error("Используйте программный интерфейс СоздатьВТКадровыеДанныеСотрудников вместо обращения к регистру",
            qsTable, ABSTRACT_QUERY_SCHEMA_TABLE__TABLE_DB_VIEW);
    }
}
