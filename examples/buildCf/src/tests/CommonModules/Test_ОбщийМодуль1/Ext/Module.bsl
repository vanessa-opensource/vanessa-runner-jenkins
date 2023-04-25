#Region Internal

Procedure ЗаполнитьНаборТестов(НаборТестов) Export
	
	НаборТестов.Добавить("Test_RunTest");
	
EndProcedure

Procedure Test_RunTest(Vanessa) Export
	
	// Given
	Item = Catalogs.MyCatalog.CreateItem();
	Item.Description = "Test item";
	
	// When
	Item.Fill(Undefined);
	
	// Then
	If Not Item.CheckFilling() Then
		
		Details = New Array;
		For each Message in GetUserMessages() Do
			Details.Add(Message.Text);
		EndDo;
		
		Raise "Check filling failed: " + StrConcat(Details, Chars.LF);
	EndIf;
	
EndProcedure

#EndRegion