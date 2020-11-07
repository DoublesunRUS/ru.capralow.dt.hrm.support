/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.pi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com._1c.g5.v8.dt.bsl.model.Module;
import com._1c.g5.v8.dt.bsl.model.StringLiteral;
import com._1c.g5.v8.dt.bsl.ui.menu.BslHandlerUtil;
import com._1c.g5.v8.dt.core.platform.IV8ProjectManager;
import com.google.inject.Inject;

import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.BslModelUtils;
import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.PersonnelAccountingUiPlugin;

public class EditAttributesHandler
    extends AbstractHandler
{

    @Inject
    private IV8ProjectManager projectManager;

    @Override
    public final Object execute(ExecutionEvent event) throws ExecutionException
    {
        IWorkbenchPart part = HandlerUtil.getActivePart(event);
        XtextEditor target = BslHandlerUtil.extractXtextEditor(part);
        if (target != null)
            this.execute(target);

        return null;
    }

    public void execute(XtextEditor target)
    {
        IFileEditorInput input = (IFileEditorInput)target.getEditorInput();
        IFile file = input.getFile();
        if (file == null)
            return;
        IProject project = file.getProject();
        if (project == null)
            return;

        IXtextDocument doc = target.getDocument();

        ITextViewer viewer = BslHandlerUtil.getTextViewer(target);

        int insertPosition = viewer.getSelectedRange().x;
        int insertLength = 0;

        StringLiteral stringLiteral = getStringLiteral(doc, viewer);
        if (stringLiteral != null)
        {
            ICompositeNode node = NodeModelUtils.findActualNodeFor(stringLiteral);
            insertPosition = node.getTotalOffset() + 2;
            insertLength = Integer.max(node.getLength() - 2, 0);
        }

        String currentAttributes = "";
        try
        {
            currentAttributes = doc.get(insertPosition, insertLength);
        }
        catch (BadLocationException e)
        {
            PersonnelAccountingUiPlugin.log(PersonnelAccountingUiPlugin.createErrorStatus(e.getMessage(), e));
        }

        List<String> attributesList = new ArrayList<>();
        // ЗапросВТПостоянныеКадровыеДанныеСотрудников
        attributesList.add("ТабельныйНомер");
        attributesList.add("ДатаПриема");
        attributesList.add("ДатаУвольнения");
        attributesList.add("ДатаЗавершенияРаботы");
        attributesList.add("ТекущаяОрганизация");
        attributesList.add("ТекущееПодразделение");
        attributesList.add("ТекущаяДолжность");
        attributesList.add("ТекущийВидЗанятости");
        attributesList.add("ОсновноеРабочееМестоВОрганизации");
        attributesList.add("ОформленПоТрудовомуДоговору");
        attributesList.add("ТекущаяДолжностьПоШтатномуРасписанию");
        attributesList.add("ТекущийВидДоговора");
        attributesList.add("ТекущаяТерритория");

        attributesList.add("ТекущаяТарифнаяСтавка");
        attributesList.add("ТекущийФОТ");
        attributesList.add("ТекущаяНадбавка");
        attributesList.add("ТекущийСпособРасчетаАванса");
        attributesList.add("ТекущийАванс");
        attributesList.add("ДатаНачалаУчета");

        attributesList.add("ПриказОПриеме");
        attributesList.add("ПриказОПриемеДата");
        attributesList.add("ПриказОПриемеНомер");
        attributesList.add("ПриказОПриемеДатаЗавершенияТрудовогоДоговора");
        attributesList.add("КраткосрочныйТрудовойДоговор");

        attributesList.add("ПриказОбУвольнении");
        attributesList.add("ПриказОбУвольненииДата");
        attributesList.add("ПриказОбУвольненииНомер");
        attributesList.add("ПриказОбУвольненииСтатьяТКРФ");

        attributesList.add("НазначениеПодработки");
        attributesList.add("ПрекращениеПодработки");

        attributesList.add("ВладелецМестаВыплаты");
        attributesList.add("ВидМестаВыплаты");
        attributesList.add("МестоВыплаты");

        // ЗапросВТКадровыеДанныеФизическихЛиц
        attributesList.add("ГражданствоПериодРегистрации");
        attributesList.add("Страна");
        attributesList.add("ИННВСтранеГражданства");

        attributesList.add("ДокументПериодРегистрации");
        attributesList.add("ДокументВид");
        attributesList.add("ДокументКодМВД");
        attributesList.add("ДокументСерия");
        attributesList.add("ДокументНомер");
        attributesList.add("ДокументДатаВыдачи");
        attributesList.add("ДокументСрокДействия");
        attributesList.add("ДокументКемВыдан");
        attributesList.add("ДокументКодПодразделения");
        attributesList.add("ДокументСтранаВыдачи");
        attributesList.add("ДокументПредставление");

        attributesList.add("ФИОПериодРегистрации");
        attributesList.add("Фамилия");
        attributesList.add("Имя");
        attributesList.add("Отчество");
        attributesList.add("ФИОПолные");
        attributesList.add("ФамилияИО");
        attributesList.add("ИОФамилия");
        attributesList.add("Инициалы");

        attributesList.add("ИнвалидностьПериодРегистрации");
        attributesList.add("Инвалидность");
        attributesList.add("ИнвалидностьДатаВыдачи");
        attributesList.add("ИнвалидностьСрокДействияСправки");

        attributesList.add("СтатусНалогоплательщикаПериодРегистрации");
        attributesList.add("СтатусНалогоплательщика");

        attributesList.add("ВидЗастрахованногоЛицаПериодРегистрации");
        attributesList.add("ВидЗастрахованногоЛица");

        attributesList.add("ОбщийСтажВид");
        attributesList.add("ОбщийСтажПериодРегистрации");
        attributesList.add("ОбщийСтажРазмерДней");
        attributesList.add("ОбщийСтажРазмерМесяцев");
        attributesList.add("ОбщийСтажДней");
        attributesList.add("ОбщийСтажМесяцев");
        attributesList.add("ОбщийСтажЛет");

        attributesList.add("СеверныйСтажВид");
        attributesList.add("СеверныйСтажПериодРегистрации");
        attributesList.add("СеверныйСтажРазмерДней");
        attributesList.add("СеверныйСтажРазмерМесяцев");
        attributesList.add("СеверныйСтажДней");
        attributesList.add("СеверныйСтажМесяцев");
        attributesList.add("СеверныйСтажЛет");

        attributesList.add("НепрерывныйСтажВид");
        attributesList.add("НепрерывныйСтажПериодРегистрации");
        attributesList.add("НепрерывныйСтажРазмерДней");
        attributesList.add("НепрерывныйСтажРазмерМесяцев");
        attributesList.add("НепрерывныйСтажДней");
        attributesList.add("НепрерывныйСтажМесяцев");
        attributesList.add("НепрерывныйСтажЛет");

        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетВид");
        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетПериодРегистрации");
        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетРазмерДней");
        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетРазмерМесяцев");
        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетДней");
        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетМесяцев");
        attributesList.add("СтажНаНадбавкуЗаВыслугуЛетЛет");

        attributesList.add("ОбщийНаучноПедагогическийСтажВид");
        attributesList.add("ОбщийНаучноПедагогическийСтажПериодРегистрации");
        attributesList.add("ОбщийНаучноПедагогическийСтажРазмерДней");
        attributesList.add("ОбщийНаучноПедагогическийСтажРазмерМесяцев");
        attributesList.add("ОбщийНаучноПедагогическийСтажДней");
        attributesList.add("ОбщийНаучноПедагогическийСтажМесяцев");
        attributesList.add("ОбщийНаучноПедагогическийСтажЛет");

        attributesList.add("ПедагогическийСтажВид");
        attributesList.add("ПедагогическийСтажПериодРегистрации");
        attributesList.add("ПедагогическийСтажРазмерДней");
        attributesList.add("ПедагогическийСтажРазмерМесяцев");
        attributesList.add("ПедагогическийСтажДней");
        attributesList.add("ПедагогическийСтажМесяцев");
        attributesList.add("ПедагогическийСтажЛет");

        attributesList.add("СтраховойСтажВид");
        attributesList.add("СтраховойСтажПериодРегистрации");
        attributesList.add("СтраховойСтажРазмерДней");
        attributesList.add("СтраховойСтажРазмерМесяцев");
        attributesList.add("СтраховойСтажДней");
        attributesList.add("СтраховойСтажМесяцев");
        attributesList.add("СтраховойСтажЛет");

        attributesList.add("РасширенныйСтраховойСтажВид");
        attributesList.add("РасширенныйСтраховойСтажПериодРегистрации");
        attributesList.add("РасширенныйСтраховойСтажРазмерДней");
        attributesList.add("РасширенныйСтраховойСтажРазмерМесяцев");
        attributesList.add("РасширенныйСтраховойСтажДней");
        attributesList.add("РасширенныйСтраховойСтажМесяцев");
        attributesList.add("РасширенныйСтраховойСтажЛет");

        attributesList.add("ПрочийСтажВид");
        attributesList.add("ПрочийСтажПериодРегистрации");
        attributesList.add("ПрочийСтажРазмерДней");
        attributesList.add("ПрочийСтажРазмерМесяцев");
        attributesList.add("ПрочийСтажДней");
        attributesList.add("ПрочийСтажМесяцев");
        attributesList.add("ПрочийСтажЛет");

        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеВид");
        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеПериодРегистрации");
        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеРазмерМесяцев");
        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеРазмерДней");
        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеДней");
        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеМесяцев");
        attributesList.add("ВыслугаЛетНаГосударственнойСлужбеЛет");

        attributesList.add("ВыслугаЛетНаВоеннойСлужбеВид");
        attributesList.add("ВыслугаЛетНаВоеннойСлужбеПериодРегистрации");
        attributesList.add("ВыслугаЛетНаВоеннойСлужбеРазмерМесяцев");
        attributesList.add("ВыслугаЛетНаВоеннойСлужбеРазмерДней");
        attributesList.add("ВыслугаЛетНаВоеннойСлужбеДней");
        attributesList.add("ВыслугаЛетНаВоеннойСлужбеМесяцев");
        attributesList.add("ВыслугаЛетНаВоеннойСлужбеЛет");

        attributesList.add("СостояниеВБракеПериодРегистрации");
        attributesList.add("СостояниеВБраке");

        attributesList.add("ВоинскийУчетПериодРегистрации");
        attributesList.add("ВоинскийУчетКатегорияЗапаса");
        attributesList.add("ВоинскийУчетЗвание");
        attributesList.add("ВоинскийУчетСостав");
        attributesList.add("ВоинскийУчетВУС");
        attributesList.add("ВоинскийУчетГодность");
        attributesList.add("ВоинскийУчетВоенкомат");
        attributesList.add("ВоинскийУчетНомерКомандыПартии");
        attributesList.add("ВоинскийУчетПунктПеречня");
        attributesList.add("ВоинскийУчетНаличиеМобпредписания");
        attributesList.add("ВоинскийУчетОтношениеКВоинскойОбязанности");
        attributesList.add("ВоинскийУчетОтношениеКВоинскомуУчету");

        attributesList.add("ВоинскийУчетЗабронированОрганизациейПериодРегистрации");
        attributesList.add("ВоинскийУчетЗабронированОрганизациейРегистратор");
        attributesList.add("ВоинскийУчетЗабронированОрганизацией");

        attributesList.add("КлассныйЧинРангПериодРегистрации");
        attributesList.add("КлассныйЧинРанг");

        attributesList.add("ВоинскоеСпециальноеЗваниеПериодРегистрации");
        attributesList.add("ВоинскоеСпециальноеЗвание");

        attributesList.add("КоличествоДетей");
        attributesList.add("КоличествоИждивенцев");
        attributesList.add("КоличествоРодственников");

        // ЗапросВТКадровыеДанныеСотрудников
        attributesList.add("РабочееМестоПериодРегистрации");
        attributesList.add("РабочееМестоРегистратор");
        attributesList.add("Организация");
        attributesList.add("Подразделение");
        attributesList.add("Должность");
        attributesList.add("ВидЗанятости");
        attributesList.add("ВидСобытия");
        attributesList.add("КоличествоСтавок");
        attributesList.add("ДолжностьПоШтатномуРасписанию");
        attributesList.add("ВидДоговора");
        attributesList.add("МестоВСтруктуреПредприятия");

        attributesList.add("РазрядКатегорияПериодРегистрации");
        attributesList.add("РазрядКатегорияРегистратор");
        attributesList.add("РазрядКатегория");

        attributesList.add("ПКУПериодРегистрации");
        attributesList.add("ПКУРегистратор");
        attributesList.add("ПКУ");

        attributesList.add("СостояниеПериодРегистрации");
        attributesList.add("Состояние");

        attributesList.add("ТерриторияПериодРегистрации");
        attributesList.add("ТерриторияРегистратор");
        attributesList.add("Территория");

        attributesList.add("ЯвляетсяЧленомЛетногоЭкипажа");
        attributesList.add("ЯвляетсяШахтером");
        attributesList.add("ЯвляетсяФармацевтом");
        attributesList.add("ЯвляетсяРаботникомСДосрочнойПенсией");
        attributesList.add("ЯвляетсяЧленомЭкипажаСуднаПодФлагомРФ");
        attributesList.add("ТарифнаяСетка");
        attributesList.add("ТарифнаяСеткаНадбавки");
        attributesList.add("КлассныйЧинРангДолжности");

        attributesList.add("ОплатаТрудаПериодЗаписи");
        attributesList.add("ОплатаТрудаРегистратор");
        attributesList.add("ТарифнаяСтавка");
        attributesList.add("ФОТ");
        attributesList.add("Надбавка");
        attributesList.add("ОсновноеНачисление");
        attributesList.add("ПоказательТарифнойСтавки");

        attributesList.add("СовокупнаяТарифнаяСтавкаПериодРегистрации");
        attributesList.add("СовокупнаяТарифнаяСтавкаРегистратор");
        attributesList.add("СовокупнаяТарифнаяСтавка");
        attributesList.add("ВидСовокупнойТарифнойСтавки");

        attributesList.add("ПорядокРасчетаСтоимостиЕдиницыВремениПериодРегистрации");
        attributesList.add("ПорядокРасчетаСтоимостиЕдиницыВремениРегистратор");
        attributesList.add("ПорядокРасчетаСтоимостиЕдиницыВремени");

        attributesList.add("ГрейдПериодРегистрации");
        attributesList.add("ГрейдРегистратор");
        attributesList.add("Грейд");

        attributesList.add("ГрафикРаботыПериодРегистрации");
        attributesList.add("ГрафикРаботыРегистратор");
        attributesList.add("ГрафикРаботы");
        attributesList.add("СуммированныйУчетРабочегоВремени");
        attributesList.add("СреднемесячноеЧислоЧасов");
        attributesList.add("СреднемесячноеЧислоДней");

        attributesList.add("ДоляНеполногоРабочегоВремени");

        attributesList.add("АвансПериодРегистрации");
        attributesList.add("АвансРегистратор");
        attributesList.add("СпособРасчетаАванса");
        attributesList.add("Аванс");

        attributesList.add("ЗарплатныйПроектДатаОткрытияЛицевогоСчета");
        attributesList.add("ЗарплатныйПроектРегистратор");
        attributesList.add("ЗарплатныйПроект");
        attributesList.add("НомерЛицевогоСчета");

        attributesList.add("УчетЗатратПериодРегистрации");
        attributesList.add("СтатьяФинансирования");
        attributesList.add("СпособОтраженияЗарплатыВБухучете");
        attributesList.add("ОтношениеКЕНВД");
        attributesList.add("УчетЗатратДокументОснование");

        attributesList.add("ДатаДоговораКонтракта");
        attributesList.add("НомерДоговораКонтракта");
        attributesList.add("ДатаНачала");
        attributesList.add("ПоступлениеНаСлужбуВпервые");

        attributesList.add("ПредставительНанимателя");
        attributesList.add("ДолжностьПредставителяНанимателя");
        attributesList.add("ОснованиеПредставителя");
        attributesList.add("ВидАктаГосоргана");
        attributesList.add("ДатаОкончания");
        attributesList.add("СрочныйДоговор");
        attributesList.add("СрокЗаключенияДоговора");
        attributesList.add("ОборудованиеРабочегоМеста");
        attributesList.add("ИныеУсловияДоговора");
        attributesList.add("ОснованиеСрочногоДоговора");

        attributesList.add("КлассУсловийТрудаПериодРегистрации");
        attributesList.add("КлассУсловийТруда");
        attributesList.add("КлассУсловийТрудаДатаРегистрацииИзменений");

        Shell shell = target.getShell();
        ListSelectionDialog dialog = new ListSelectionDialog(shell, attributesList, new ArrayContentProvider(),
            new LabelProvider(), "Dialog message");
        dialog.setInitialSelections(currentAttributes.split(",")); //$NON-NLS-1$
        dialog.setTitle("Dialog title");
        if (dialog.open() != Window.OK)
            return;

        List<String> selectedAttributesList = new ArrayList<>();
        for (Object resultElement : dialog.getResult())
            selectedAttributesList.add((String)resultElement);

        String newAttributes = String.join(",", selectedAttributesList); //$NON-NLS-1$

        try
        {
            doc.replace(insertPosition, insertLength, newAttributes);
        }
        catch (BadLocationException e)
        {
            PersonnelAccountingUiPlugin.log(PersonnelAccountingUiPlugin.createErrorStatus(e.getMessage(), e));
        }
    }

    private StringLiteral getStringLiteral(IXtextDocument doc, ITextViewer viewer)
    {
        return doc.readOnly(new IUnitOfWork<StringLiteral, XtextResource>()
        {
            @Override
            public StringLiteral exec(XtextResource res) throws Exception
            {
                if (res.getContents() != null && !res.getContents().isEmpty())
                {
                    EObject obj = res.getContents().get(0);
                    if (obj instanceof Module)
                    {
                        Module module = (Module)obj;

                        StringLiteral stringLiteral =
                            BslModelUtils.getNearestStringLiteral(module, viewer.getSelectedRange().x);
                        if (stringLiteral != null)
                            return stringLiteral;
                    }
                }
                return null;
            }
        });
    }
}
