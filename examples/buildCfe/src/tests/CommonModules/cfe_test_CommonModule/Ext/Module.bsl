#Region Public

Procedure ЗаполнитьНаборТестов(НаборТестов) Export
	
	НаборТестов.Добавить("Test_MyText");
	
	
EndProcedure

Procedure Test_MyText(Vanessa) Export
	
	// Given
	Text = "My text";
	
	// When
	MyText = cfe_CommonModule.MyText();
	
	// Then
	If (MyText <> Text) Then
		Raise "MyText is changed";
	EndIf;
	
EndProcedure

#EndRegion
