#Если Сервер Или ТолстыйКлиентОбычноеПриложение Или ВнешнееСоединение Тогда

Процедура ТестКадроваяИсторияСотрудников() Экспорт

	Запрос = Новый Запрос;

	Запрос.МенеджерВременныхТаблиц = Новый МенеджерВременныхТаблиц;

	Запрос.Текст =
	"ВЫБРАТЬ
	|	КадроваяИсторияСотрудников.Сотрудник
	|ИЗ
	|	РегистрСведений.КадроваяИсторияСотрудников КАК КадроваяИсторияСотрудников";

КонецПроцедуры

#КонецЕсли