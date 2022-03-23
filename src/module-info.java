module CPS240HW8 {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}
